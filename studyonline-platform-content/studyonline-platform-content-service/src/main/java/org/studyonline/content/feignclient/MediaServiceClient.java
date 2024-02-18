package org.studyonline.content.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.studyonline.content.config.MultipartSupportConfig;

import java.io.IOException;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 18/02/2024 7:37 pm
 */

@FeignClient(value = "media-api",configuration = {MultipartSupportConfig.class},fallback = MediaServiceClientFallbackFactory.class)
public interface MediaServiceClient {

    @RequestMapping(value = "/media/upload/coursefile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("filedata") MultipartFile filedata, @RequestParam(value= "objectName",required=false) String objectName) throws IOException;

}
