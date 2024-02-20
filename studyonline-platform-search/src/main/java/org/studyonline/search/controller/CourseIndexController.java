package org.studyonline.search.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.search.po.CourseIndex;
import org.studyonline.search.service.IndexService;
/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 8:03 pm
 */
@Api(value = "Course information index interface", tags = "Course information index interface")
@RestController
@RequestMapping("/index")
public class CourseIndexController {

    @Value("${elasticsearch.course.index}")
    private String courseIndexStore;

    @Autowired
    IndexService indexService;

    @ApiOperation("Add course index")
    @PostMapping("course")
    public Boolean add(@RequestBody CourseIndex courseIndex) {

        Long id = courseIndex.getId();
        if(id==null){
            StudyOnlineException.cast("Course id is empty");
        }
        Boolean result = indexService.addCourseIndex(courseIndexStore, String.valueOf(id), courseIndex);
        if(!result){
            StudyOnlineException.cast("Failed to add course index");
        }
        return result;

    }
}
