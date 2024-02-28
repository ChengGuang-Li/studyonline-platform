package org.studyonline.learning.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.base.model.PageResult;
import org.studyonline.learning.model.dto.ChooseCourseDto;
import org.studyonline.learning.model.dto.CourseTablesDto;
import org.studyonline.learning.model.dto.MyCourseTableParams;
import org.studyonline.learning.model.po.CourseTables;
import org.studyonline.learning.service.MyCourseTablesService;
import org.studyonline.learning.util.SecurityUtil;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:41 pm
 */

@Api(value = "My course schedule interface", tags = "My course schedule interface")
@Slf4j
@RestController
public class MyCourseTablesController {

    @Autowired
    MyCourseTablesService myCourseTablesService;


    @ApiOperation("Add course selection")
    @PostMapping("/choosecourse/{courseId}")
    public ChooseCourseDto addChooseCourse(@PathVariable("courseId") Long courseId) {

        //Currently logged in user
        SecurityUtil.User user = SecurityUtil.getUser();
        if(user == null){
            StudyOnlineException.cast("Please Login");
        }
        //User Id
        String userId = user.getId();
        //Add course selection
        ChooseCourseDto xcChooseCourseDto = myCourseTablesService.addChooseCourse(userId, courseId);
        return xcChooseCourseDto;
    }

    @ApiOperation("Check study qualifications")
    @PostMapping("/choosecourse/learnstatus/{courseId}")
    public CourseTablesDto getLearnstatus(@PathVariable("courseId") Long courseId) {
        //Currently logged in user
        SecurityUtil.User user = SecurityUtil.getUser();
        if(user == null){
            StudyOnlineException.cast("Please Login");
        }
        //User id
        String userId = user.getId();

        CourseTablesDto learningStatus = myCourseTablesService.getLearningStatus(userId, courseId);

        return learningStatus;

    }

    @ApiOperation("My class schedule")
    @GetMapping("/mycoursetable")
    public PageResult<CourseTables> mycoursetable(MyCourseTableParams params) {
        return null;
    }

}
