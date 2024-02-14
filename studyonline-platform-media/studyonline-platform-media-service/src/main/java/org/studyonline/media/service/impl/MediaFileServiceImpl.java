package org.studyonline.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.base.model.RestResponse;
import org.studyonline.media.mapper.MediaFilesMapper;
import org.studyonline.media.mapper.MediaProcessMapper;
import org.studyonline.media.model.dto.QueryMediaParamsDto;
import org.studyonline.media.model.dto.UploadFileParamsDto;
import org.studyonline.media.model.dto.UploadFileResultDto;
import org.studyonline.media.model.po.MediaFiles;
import org.studyonline.media.model.po.MediaProcess;
import org.studyonline.media.service.MediaFileService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class MediaFileServiceImpl implements MediaFileService {

    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Autowired
    MinioClient minioClient;

    @Autowired
    MediaFileService currentServiceProxy;

    @Autowired
    MediaProcessMapper mediaProcessMapper;

    //normal files(images,files)
    @Value("${minio.bucket.files}")
    private String bucket_mediaFiles;

    //video files
    @Value("${minio.bucket.videofiles}")
    private String bucket_videoFiles;

    @Override
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {
        //构建查询条件对象
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();

        //分页对象
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<MediaFiles> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        PageResult<MediaFiles> mediaListResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
        return mediaListResult;

    }

    @Override
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) {
        //upload File into MinIo
        File file = new File(localFilePath);
        if (!file.exists()) {
            StudyOnlineException.cast("File is not exist");
        }
        String fileName = uploadFileParamsDto.getFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String defaultFolderPath = getDefaultFolderPath();
        String fileMd5 = getFileMd5(file);
        String objectName = defaultFolderPath + fileMd5 + extension;

        String mimeType = getMimeType(extension);
        boolean isUploadSuccess = addMediaFilesToMinIo(localFilePath, mimeType, bucket_mediaFiles, objectName);
        if (!isUploadSuccess) {
            StudyOnlineException.cast("Upload File Failed !");
        }
        //save file info into database
        //check file exist in database or not
        MediaFiles mediaFiles = currentServiceProxy.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_mediaFiles, objectName);
        UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
        BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);

        return uploadFileResultDto;
    }

    public String getMimeType(String extension) {
        if (extension == null) {
            extension = "";
        }
        //Get mimeType based on extension
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        //Generic mimeType, byte stream
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }

    //Get the file default storage directory path year/month/day
    private String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String folder = sdf.format(new Date()).replace("-", "/") + "/";
        return folder;
    }

    public boolean addMediaFilesToMinIo(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().bucket(bucket)
                    .filename(localFilePath).object(objectName).contentType(mimeType).build();
            minioClient.uploadObject(uploadObjectArgs);
            log.debug("upload file into MinIo successfully,bucket:{}，objectName:{}", bucket, objectName);
            return true;
        } catch (Exception e) {
            log.error("upload File into MinIo failed, bucket:{}, objectName:{},failed reasons:{}", bucket, objectName, e.getMessage(), e);
            StudyOnlineException.cast("Failed to upload file to file system");
        }
        return false;
    }

    //Get md5 of file
    private String getFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            String fileMd5 = DigestUtils.md5Hex(fileInputStream);
            return fileMd5;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param companyId           Company id
     * @param fileMd5             Md5 value of File
     * @param uploadFileParamsDto file info
     * @param bucket              bucket
     * @param objectName          object name
     * @return org.studyonline.media.model.po.MediaFiles
     * @description save file info into database
     * @author Chengguang Li
     * @date 04/02/2024
     */
    @Transactional
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName) {
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles == null) {
            mediaFiles = new MediaFiles();
            BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
            mediaFiles.setId(fileMd5);
            mediaFiles.setFileId(fileMd5);
            mediaFiles.setCompanyId(companyId);
            mediaFiles.setBucket(bucket_mediaFiles);
            mediaFiles.setUrl("/" + bucket + "/" + objectName);
            mediaFiles.setFilePath(objectName);
            mediaFiles.setCreateDate(LocalDateTime.now());
            mediaFiles.setAuditStatus("002003");
            mediaFiles.setStatus("1");
            //save file info into database
            int insert = mediaFilesMapper.insert(mediaFiles);
            if (insert < 1) {
                log.error("save file info into database failed, {}", mediaFiles.toString());
                StudyOnlineException.cast("Save file info failed");
            }
        }
        //Record pending tasks -  Record data to the MediaProcess table
        // Only record if it is avi video
        addWaitingTask(mediaFiles);
        log.debug("Save file information to database successfully,{}", mediaFiles.toString());
        return mediaFiles;
    }

    @Override
    public RestResponse<Boolean> checkFile(String fileMd5) {
        //check database to query file information
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles != null) {
            //If it doesn't exist in database, check the MinIO
            //bucket
            String bucket = mediaFiles.getBucket();
            //storage directory
            String filePath = mediaFiles.getFilePath();
            //file stream
            InputStream stream = null;
            try {
                stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucket)
                                .object(filePath)
                                .build());
                if (stream != null) {
                    //File exist
                    return RestResponse.success(true);
                }
            } catch (Exception e) {
                log.error("CheckFile Failed, fileMd5: {}, error msg: ", fileMd5, e);
            }
        }
        //File does not exist
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex) {
        //Get the chunked file directory
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //Get the path of the chunked file
        String chunkFilePath = chunkFileFolderPath + chunkIndex;
        //file stream
        InputStream fileInputStream = null;
        try {
            fileInputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket_videoFiles)
                            .object(chunkFilePath)
                            .build());

            if (fileInputStream != null) {
                //Chunk already exists
                return RestResponse.success(true);
            }
        } catch (Exception e) {
            log.error("CheckChunk Failed, fileMd5: {}, error msg: ", fileMd5, e);
        }
        //The chunk does not exist
        return RestResponse.success(false);

    }

    @Override
    public RestResponse uploadChunk(String fileMd5, int chunk, String localChunkFilePath) {
        //Get the directory path of the chunked file
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //Get the path of the chunked file
        String chunkFilePath = chunkFileFolderPath + chunk;
        //Get mimeType
        String mimeType = getMimeType(null);
        try {
            //Store files to minIO
            boolean b = addMediaFilesToMinIo(localChunkFilePath, mimeType, bucket_videoFiles, chunkFilePath);
            if (b) {
                return RestResponse.success(true);
            }
        } catch (Exception e) {
            log.debug("Upload chunked files:{},fail:{}", chunkFilePath, e.getMessage());
        }
        return RestResponse.validfail("Upload chunked files Failed", false);
    }

    @Override
    public RestResponse mergechunks(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) {
        //=====1. Find the chunk files And merge these chunk files=====
        //Get chunked file path
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //Compose chunked file paths into List<ComposeSource>
        List<ComposeSource> sourceObjectList = Stream.iterate(0, i -> ++i)
                .limit(chunkTotal)
                .map(i -> ComposeSource.builder()
                        .bucket(bucket_videoFiles)
                        .object(chunkFileFolderPath.concat(Integer.toString(i)))
                        .build())
                .collect(Collectors.toList());
        //=====2. Merge Chunk Files=====
        String fileName = uploadFileParamsDto.getFilename();//File Name
        String extName = fileName.substring(fileName.lastIndexOf("."));// file extension name
        String mergeFilePath = getFilePathByMd5(fileMd5, extName);//Merge file paths
        try {
           //Compose Files
            ObjectWriteResponse response = minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(bucket_videoFiles)
                            .object(mergeFilePath)
                            .sources(sourceObjectList)
                            .build());
            log.debug("Merged files successfully:{}",mergeFilePath);
        }catch (Exception e){
            log.error("Failed to merge files,fileMd5:{},Exception:{}",fileMd5,e.getMessage(),e);
            return RestResponse.validfail("Failed to merge files",false);
        }

        //=====3. Check the merged file and original file=====
        File minioFile = downloadFileFromMinIO(bucket_videoFiles, mergeFilePath);
        if(minioFile == null){
            log.debug("Failed to download merged file,mergeFilePath:{}",mergeFilePath);
            return RestResponse.validfail("Failed to download merged file",false);
        }
        try (InputStream newFileInputStream = new FileInputStream(minioFile)) {
            //md5 value of file on minio
            String md5Hex = DigestUtils.md5Hex(newFileInputStream);
            //Compare md5 values. If they are inconsistent, the file is incomplete.
            if(!fileMd5.equals(md5Hex)){
                log.error("Md5 value is different, Original File Md5: {}, Merged File Md5: {}",fileMd5, md5Hex);
                return RestResponse.validfail("File merge verification failed, and the final upload failed.",false);
            }
            //File Size
            uploadFileParamsDto.setFileSize(minioFile.length());
        }catch (Exception e){
            log.error("Verification file failed,fileMd5:{},Exception:{}",fileMd5,e.getMessage(),e);
            return RestResponse.validfail("File merge verification failed, and the final upload failed.",false);
        }finally {
            if(minioFile!=null){
                minioFile.delete();
            }
        }

        //=====4. Save the file info into database=====
        MediaFiles mediaFiles = currentServiceProxy.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_videoFiles, mergeFilePath);
        if(mediaFiles == null){
            return RestResponse.validfail("Failed to write file information to database.",false);
        }

        //=====5. Delete the chunk files=====
        clearChunkFiles(chunkFileFolderPath,chunkTotal);
       return RestResponse.success(true);
    }

    //Get the directory of chunked files
    private String getChunkFileFolderPath(String fileMd5) {
        return fileMd5.substring(0, 1) + "/" + fileMd5.substring(1, 2) + "/" + fileMd5 + "/" + "chunk" + "/";
    }

    private String getFilePathByMd5(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }
    /**
     * Download files from minio
     * @param bucket bucket
     * @param objectName object name
     * @return Downloaded file
     */
    public File downloadFileFromMinIO(String bucket,String objectName)
    {
        //Temporary  file
        File minioFile = null;
        FileOutputStream outputStream = null;
        try{
            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            //Create Temporary File
            minioFile=File.createTempFile("minio", ".merge");
            outputStream = new FileOutputStream(minioFile);
            IOUtils.copy(stream,outputStream);
            return minioFile;
        } catch (Exception e) {
            log.error("File Download failed: {}",e.getMessage());
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("File Download failed: {}",e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * Clear chunked files
     * @param chunkFileFolderPath Chunked file path
     * @param chunkTotal Total number of chunked files
     */
    private void clearChunkFiles(String chunkFileFolderPath,int chunkTotal){

        try {
            List<DeleteObject> deleteObjects = Stream.iterate(0, i -> ++i)
                    .limit(chunkTotal)
                    .map(i -> new DeleteObject(chunkFileFolderPath.concat(Integer.toString(i))))
                    .collect(Collectors.toList());

            RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder().bucket(bucket_videoFiles).objects(deleteObjects).build();
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
            results.forEach(r->{
                DeleteError deleteError = null;
                try {
                    deleteError = r.get();
                } catch (Exception e) {
                    log.error("Clear chunked file failed,objectname:{}",deleteError.objectName(),e);
                }
            });
        } catch (Exception e) {
            log.error("Clear chunked file failed,chunkFileFolderPath:{}",chunkFileFolderPath,e);
        }
    }


    /**
     * Add pending tasks
     * @param mediaFiles Media asset file information
     */
    private void addWaitingTask(MediaFiles mediaFiles){
        //file name
        String filename = mediaFiles.getFilename();
        //file extension
        String exension = filename.substring(filename.lastIndexOf("."));
        //File mimeType
        String mimeType = getMimeType(exension);
        //If it is an avi video, add it to the video pending list
        if(mimeType.equals("video/x-msvideo")){
            MediaProcess mediaProcess = new MediaProcess();
            BeanUtils.copyProperties(mediaFiles,mediaProcess);
            mediaProcess.setStatus("1");// Pending
            mediaProcess.setFailCount(0);//The number of failures defaults to 0
            mediaProcess.setCreateDate(LocalDateTime.now());
            mediaProcess.setUrl(null);
            int insert = mediaProcessMapper.insert(mediaProcess);
            if(insert < 1){
                log.error("Failed to save file information to database MediaProcess table, {}", mediaFiles.toString());
                StudyOnlineException.cast("Failed to save file information to database MediaProcess table");
            }
        }
    }

}



