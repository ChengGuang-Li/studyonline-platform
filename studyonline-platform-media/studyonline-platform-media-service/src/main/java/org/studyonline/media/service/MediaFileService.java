package org.studyonline.media.service;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.base.model.RestResponse;
import org.studyonline.media.model.dto.QueryMediaParamsDto;
import org.studyonline.media.model.dto.UploadFileParamsDto;
import org.studyonline.media.model.dto.UploadFileResultDto;
import org.studyonline.media.model.po.MediaFiles;

import java.io.File;

public interface MediaFileService {

    /**
     * @description Media asset file query method
     * @param pageParams Paging parameters
     * @param queryMediaParamsDto Query conditions
     * @return org.studyonline.base.model.PageResult<org.studyonline.media.model.po.MediaFiles>
     * @author Chengguang Li
     * @date 02/02/2024 8:57
     */
    public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

    /**
     * Upload File
     * @param companyId Institution ID
     * @param uploadFileParamsDto Upload file information
     * @param localFilePath file disk path
     * @return File information
     */
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

    /**
     * Save media file info into database
     * @param companyId Institution ID
     * @param fileMd5 Md5 value of File
     * @param uploadFileParamsDto Upload file info
     * @param bucket bucket name on MinIo
     * @param objectName object name on MinIo
     * @return
     */
    public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName);


    /**
     * @description Check if the file exists
     * @param fileMd5 md5 of file
     * @return org.studyonline.base.model.RestResponse<java.lang.Boolean> false does not exist, true exists
     * @author Chengguang Li
     * @date 12/02/2024 21:24
     */
    public RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * @description Check if the chunk exists
     * @param fileMd5  md5 of file
     * @param chunkIndex  block sequence number
     * @return org.studyonline.base.model.RestResponse<java.lang.Boolean> false does not exist, true exists
     * @author Chengguang Li
     * @date 12/02/2024 21:24
     */
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    /**
     * @description Upload in chunks
     * @param fileMd5  File md5
     * @param chunk  block sequence number
     * @param localChunkFilePath  file local path
     * @return org.studyonline.base.model.RestResponse<java.lang.Boolean>
     * @author Chengguang Li
     * @date 12/02/2024 21:24
     */
    public RestResponse uploadChunk(String fileMd5,int chunk,String localChunkFilePath);


    /**
     * @description Merge Chunks
     * @param companyId  Institution ID
     * @param fileMd5  File md5
     * @param chunkTotal block sum
     * @param uploadFileParamsDto File information
     * @return org.studyonline.base.model.RestResponse<java.lang.Boolean>
     * @author Chengguang Li
     * @date 12/02/2024 22:45
     */
    public RestResponse mergechunks(Long companyId,String fileMd5,int chunkTotal,UploadFileParamsDto uploadFileParamsDto);


    /**
     * Download files from minio
     * @param bucket bucket
     * @param objectName object name
     * @return Downloaded file
     */
    public File downloadFileFromMinIO(String bucket, String objectName);

    /**
     * upload media files to MinIo
     * @param localFilePath
     * @param mimeType
     * @param bucket
     * @param objectName
     * @return
     */
    public boolean addMediaFilesToMinIo(String localFilePath, String mimeType, String bucket, String objectName);

    /**
     *  Find Media File info by mediaId
     * @param id
     * @return
     */
    public MediaFiles getFileById(String id);

}
