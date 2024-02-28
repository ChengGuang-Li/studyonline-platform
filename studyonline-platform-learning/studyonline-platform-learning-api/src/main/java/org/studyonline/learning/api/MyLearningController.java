package org.studyonline.learning.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.base.model.RestResponse;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:42 pm
 */
@Api(value = "Learning process management interface", tags = "Learning process management interface")
@Slf4j
@RestController
public class MyLearningController {


    @ApiOperation("Get video")
    @GetMapping("/open/learn/getvideo/{courseId}/{teachplanId}/{mediaId}")
    public RestResponse<String> getvideo(@PathVariable("courseId") Long courseId, @PathVariable("courseId") Long teachplanId, @PathVariable("mediaId") String mediaId) {

        return null;

    }

}