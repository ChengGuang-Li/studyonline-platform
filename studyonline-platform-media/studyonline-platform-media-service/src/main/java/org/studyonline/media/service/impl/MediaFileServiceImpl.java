package org.studyonline.media.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.media.mapper.MediaFilesMapper;
import org.studyonline.media.model.dto.QueryMediaParamsDto;
import org.studyonline.media.model.dto.UploadFileParamsDto;
import org.studyonline.media.model.dto.UploadFileResultDto;
import org.studyonline.media.model.po.MediaFiles;
import org.studyonline.media.service.MediaFileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MediaFileServiceImpl implements MediaFileService {

    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Autowired
    MinioClient minioClient;

    @Autowired
    MediaFileService currentServiceProxy;

    //normal files(images,files)
    @Value("${minio.bucket.files}")
    private String bucket_mediaFiles;

    //video files
    @Value("${minio.bucket.videofiles}")
    private String bucket_videofiles;

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
        if(!file.exists()){
            StudyOnlineException.cast("File is not exist");
        }
        String fileName = uploadFileParamsDto.getFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String defaultFolderPath = getDefaultFolderPath();
        String fileMd5 = getFileMd5(file);
        String objectName = defaultFolderPath + fileMd5 + extension;

        String mimeType = getMimeType(extension);
        boolean isUploadSuccess = addMediaFilesToMinIo(localFilePath, mimeType, bucket_mediaFiles, objectName);
        if(!isUploadSuccess){
           StudyOnlineException.cast("Upload File Failed !");
        }
        //save file info into database
        //check file exist in database or not
        MediaFiles mediaFiles = currentServiceProxy.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_mediaFiles, objectName);
        UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
        BeanUtils.copyProperties(mediaFiles,uploadFileResultDto);

        return uploadFileResultDto;
    }

    public String getMimeType(String extension){
        if(extension==null){
            extension = "";
        }
        //Get mimeType based on extension
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        //Generic mimeType, byte stream
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if(extensionMatch != null){
            mimeType = extensionMatch.getMimeType();
        }
        return  mimeType;
    }

    //Get the file default storage directory path year/month/day
    private String getDefaultFolderPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String folder = sdf.format(new Date()).replace("-", "/") + "/";
        return folder;
    }

    public boolean addMediaFilesToMinIo(String localFilePath,String mimeType,String bucket,String objectName){
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().bucket(bucket)
                    .filename(localFilePath).object(objectName).contentType(mimeType).build();
            minioClient.uploadObject(uploadObjectArgs);
            log.debug("upload file into MinIo successfully,bucket:{}，objectName:{}",bucket,objectName);
            return true;
        }catch (Exception e){
            log.error("upload File into MinIo failed, bucket:{}, objectName:{},failed reasons:{}",bucket,objectName,e.getMessage(),e);
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
     * @description save file info into database
     * @param companyId  Company id
     * @param fileMd5  Md5 value of File
     * @param uploadFileParamsDto  file info
     * @param bucket  bucket
     * @param objectName object name
     * @return org.studyonline.media.model.po.MediaFiles
     * @author Chengguang Li
     * @date 04/02/2024
     */
    @Transactional
    public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName){
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if(mediaFiles == null ){
            mediaFiles = new MediaFiles();
            BeanUtils.copyProperties(uploadFileParamsDto,mediaFiles);
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
            if(insert < 1){
                log.error("save file info into database failed, {}",mediaFiles.toString());
                StudyOnlineException.cast("Save file info failed");
            }
        }

        return mediaFiles;
    }


    }
