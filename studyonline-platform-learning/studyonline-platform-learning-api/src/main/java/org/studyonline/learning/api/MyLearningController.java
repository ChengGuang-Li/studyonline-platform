package org.studyonline.learning.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.base.model.RestResponse;
import org.studyonline.learning.service.LearningService;
import org.studyonline.learning.util.SecurityUtil;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:42 pm
 */
@Api(value = "Learning process management interface", tags = "Learning process management interface")
@Slf4j
@RestController
public class MyLearningController {

    @Autowired
    LearningService learningService;

    /**
     *
     * @param courseId Course Id
     * @param teachplanId Course plan Id
     * @param mediaId Media Files Id
     * @return
     */
    @ApiOperation("Get video")
    @GetMapping("/open/learn/getvideo/{courseId}/{teachplanId}/{mediaId}")
    public RestResponse<String> getvideo(@PathVariable("courseId") Long courseId, @PathVariable("teachplanId") Long teachplanId, @PathVariable("mediaId") String mediaId) {
        SecurityUtil.User user = SecurityUtil.getUser();
        String id = user.getId();

        RestResponse<String> video = learningService.getVideo(id, courseId, teachplanId, mediaId);

        return video;

    }


}