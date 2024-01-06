package org.studyonline.content.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.model.dto.QueryCourseParamsDto;
import org.studyonline.content.model.po.CourseBase;
import org.studyonline.content.service.CourseBaseInfoService;

/**
 * @description Course Information Editing Interface
 * @author ChengGuang
 * @date 2023/12/29 22:30
 * @version 1.0
 */
@Api(value = "Course information management interface",tags = "Course information management interface")
@RestController
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("Course information query interface")
    @PostMapping("/course/list")
    public PageResult<CourseBase>  list(PageParams pageParams, @RequestBody(required=false) @ApiParam("Parameters for Course Information Query") QueryCourseParamsDto queryCourseParams){

        return courseBaseInfoService.queryCourseBaseInfoList(pageParams, queryCourseParams);
    }
}
