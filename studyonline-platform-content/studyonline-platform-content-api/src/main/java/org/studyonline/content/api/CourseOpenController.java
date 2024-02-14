package org.studyonline.content.api;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.content.model.dto.CoursePreviewDto;
import org.studyonline.content.service.CourseBaseInfoService;
import org.studyonline.content.service.CoursePublishService;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 14/02/2024 10:17 pm
 */

@Api(value = "Course public inquiry interface",tags = "Course public inquiry interface")
@RestController
@RequestMapping("/open")
public class CourseOpenController {
    @Autowired
    private CourseBaseInfoService courseBaseInfoService;

    @Autowired
    private CoursePublishService coursePublishService;


    @GetMapping("/course/whole/{courseId}")
    public CoursePreviewDto getPreviewInfo(@PathVariable("courseId") Long courseId) {
        //Get course preview information
        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(courseId);
        return coursePreviewInfo;
    }

}
