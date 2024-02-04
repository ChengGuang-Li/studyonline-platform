package org.studyonline.media.service;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.media.model.dto.QueryMediaParamsDto;
import org.studyonline.media.model.dto.UploadFileParamsDto;
import org.studyonline.media.model.dto.UploadFileResultDto;
import org.studyonline.media.model.po.MediaFiles;

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
     * 上传文件
     * @param companyId Institution ID
     * @param uploadFileParamsDto Upload file information
     * @param localFilePath file disk path
     * @return File information
     */
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

}
