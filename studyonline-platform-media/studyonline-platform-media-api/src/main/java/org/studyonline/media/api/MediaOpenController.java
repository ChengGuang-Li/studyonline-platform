package org.studyonline.media.api;

import com.alibaba.cloud.commons.lang.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.base.model.RestResponse;
import org.studyonline.media.model.po.MediaFiles;
import org.studyonline.media.service.MediaFileService;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 14/02/2024 10:21 pm
 */


@Api(value = "Media asset file management interface",tags = "Media asset file management interface")
@RestController
@RequestMapping("/open")
public class MediaOpenController {
    @Autowired
    MediaFileService mediaFileService;

    @ApiOperation("Preview file")
    @GetMapping("/preview/{mediaId}")
    public RestResponse<String> getPlayUrlByMediaId(@PathVariable String mediaId){

        MediaFiles mediaFiles = mediaFileService.getFileById(mediaId);

        if(mediaFiles == null || StringUtils.isEmpty(mediaFiles.getUrl())){
            return RestResponse.validfail("The video has not been transcoded yet");
        }
        return RestResponse.success(mediaFiles.getUrl());

    }


}
