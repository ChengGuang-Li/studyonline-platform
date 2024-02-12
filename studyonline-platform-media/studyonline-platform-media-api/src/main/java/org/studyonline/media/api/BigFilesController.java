package org.studyonline.media.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.studyonline.base.model.RestResponse;
import org.studyonline.media.model.dto.UploadFileParamsDto;
import org.studyonline.media.service.MediaFileService;

import java.io.File;

/**
 * @author Chengguang Li
 * @version 1.0
 * @description Large file upload interface
 * @date 12/02/2024 21:24
 */
@Api(value = "Large file upload interface", tags = "Large file upload interface")
@RestController
public class BigFilesController {

    @Autowired
    private MediaFileService mediaFileService;
    @ApiOperation(value = "Check files before uploading")
    @PostMapping("/upload/checkfile")
    public RestResponse<Boolean> checkfile(@RequestParam("fileMd5") String fileMd5) throws Exception {
        RestResponse<Boolean> booleanRestResponse = mediaFileService.checkFile(fileMd5);
        return booleanRestResponse;
    }


    @ApiOperation(value = "Detection of chunked files before uploading")
    @PostMapping("/upload/checkchunk")
    public RestResponse<Boolean> checkchunk(@RequestParam("fileMd5") String fileMd5,
                                            @RequestParam("chunk") int chunk) throws Exception {

        RestResponse<Boolean> booleanRestResponse = mediaFileService.checkChunk(fileMd5, chunk);
        return booleanRestResponse;
    }

    @ApiOperation(value = "Upload chunked files")
    @PostMapping("/upload/uploadchunk")
    public RestResponse uploadchunk(@RequestParam("file") MultipartFile file,
                                    @RequestParam("fileMd5") String fileMd5,
                                    @RequestParam("chunk") int chunk) throws Exception {
        File tempFile = File.createTempFile("minio", ".temp");
        file.transferTo(tempFile);
        String localFilePath = tempFile.getAbsolutePath();

        RestResponse restResponse = mediaFileService.uploadChunk(fileMd5, chunk, localFilePath);

        return restResponse;
    }

    @ApiOperation(value = "Merge files")
    @PostMapping("/upload/mergechunks")
    public RestResponse mergechunks(@RequestParam("fileMd5") String fileMd5,
                                    @RequestParam("fileName") String fileName,
                                    @RequestParam("chunkTotal") int chunkTotal) throws Exception {
        Long companyId = 1232141425L;
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFilename(fileName);
        uploadFileParamsDto.setFileType("001002");
        uploadFileParamsDto.setTags("Course Videos");
        uploadFileParamsDto.setRemark("");

        RestResponse mergechunks = mediaFileService.mergechunks(companyId, fileMd5, chunkTotal, uploadFileParamsDto);
        return mergechunks;
    }

}
