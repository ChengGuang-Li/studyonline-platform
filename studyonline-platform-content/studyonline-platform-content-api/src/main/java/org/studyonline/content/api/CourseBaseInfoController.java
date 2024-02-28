package org.studyonline.content.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.studyonline.base.exception.ValidationGroups;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.model.dto.AddCourseDto;
import org.studyonline.content.model.dto.CourseBaseInfoDto;
import org.studyonline.content.model.dto.EditCourseDto;
import org.studyonline.content.model.dto.QueryCourseParamsDto;
import org.studyonline.content.model.po.CourseBase;
import org.studyonline.content.service.CourseBaseInfoService;
import org.studyonline.content.util.SecurityUtil;

/**
 * @description Course Information Editing Interface
 * @author ChengGuang
 * @date 2023/12/29 22:30
 * @version 1.0
 */
@Api(value = "Course information management interface",tags = "Course information management interface")
@RestController
@Slf4j
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("Course information page query")
    @PreAuthorize("hasAuthority('teachmanager_course_list')")
    @PostMapping("/course/list")
    public PageResult<CourseBase>  list(PageParams pageParams, @RequestBody(required=false) @ApiParam("Parameters for Course Information Query") QueryCourseParamsDto queryCourseParams){
        SecurityUtil.User user = SecurityUtil.getUser();
        Long companyId = Long.parseLong(user.getCompanyId());
        return courseBaseInfoService.queryCourseBaseInfoList(pageParams, queryCourseParams,companyId);
    }


    @PostMapping("/course")
    @ApiOperation("Add New Course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class)  AddCourseDto addCourseDto){
        SecurityUtil.User user = SecurityUtil.getUser();
        Long companyId = Long.parseLong(user.getCompanyId());
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);

        return courseBase;
    }

    @ApiOperation("Course information query based on course ID")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
        CourseBaseInfoDto courseBaseInfoById = courseBaseInfoService.getCourseBaseInfoById(courseId);
        return courseBaseInfoById;
    }

    @ApiOperation("Edit Course Information ")
    @PutMapping("/course")
    public CourseBaseInfoDto updateCourseInfo( @RequestBody @Validated(ValidationGroups.Update.class) EditCourseDto editCourseDto){
        SecurityUtil.User user = SecurityUtil.getUser();
        Long companyId = Long.parseLong(user.getCompanyId());
        CourseBaseInfoDto courseBaseInfoDto = courseBaseInfoService.updateCourseInfo(companyId, editCourseDto);
        return courseBaseInfoDto;
    }
}
