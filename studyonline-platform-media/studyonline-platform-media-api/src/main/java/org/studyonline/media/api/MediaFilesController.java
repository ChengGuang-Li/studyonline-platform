package org.studyonline.media.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.media.model.dto.QueryMediaParamsDto;
import org.studyonline.media.model.dto.UploadFileParamsDto;
import org.studyonline.media.model.dto.UploadFileResultDto;
import org.studyonline.media.model.po.MediaFiles;
import org.studyonline.media.service.MediaFileService;

import java.io.File;
import java.io.IOException;

@Api(value = "Media asset file management interface",tags = "Media asset file management interface")
@RestController
public class MediaFilesController {
    @Autowired
    private MediaFileService mediaFileService;

    
    @ApiOperation("Media asset list query interface")
    @PostMapping("/files")
    public PageResult<MediaFiles> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto){
        Long companyId = 1232141425L;
        return mediaFileService.queryMediaFiels(companyId,pageParams,queryMediaParamsDto);

    }

    @ApiOperation("Upload Pictures")
    @RequestMapping(value = "/upload/coursefile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResultDto upload(@RequestPart("filedata")MultipartFile filedata) throws IOException {

        File tempFile = File.createTempFile("minio", ".temp");
        filedata.transferTo(tempFile);
        String localFilePath = tempFile.getAbsolutePath();
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        //prepare file info
        uploadFileParamsDto.setFilename(filedata.getOriginalFilename());
        uploadFileParamsDto.setFileSize(filedata.getSize());
        uploadFileParamsDto.setFileType("001001");

        Long companyId = 1232141425L;
        //call mediaFileService to upload file info
        UploadFileResultDto uploadFileResultDto = mediaFileService.uploadFile(companyId, uploadFileParamsDto, localFilePath);

        return uploadFileResultDto;
    }
}
