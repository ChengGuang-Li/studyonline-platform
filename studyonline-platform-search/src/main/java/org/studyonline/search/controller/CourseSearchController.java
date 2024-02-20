package org.studyonline.search.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.base.model.PageParams;
import org.studyonline.search.dto.SearchCourseParamDto;
import org.studyonline.search.dto.SearchPageResultDto;
import org.studyonline.search.po.CourseIndex;
import org.studyonline.search.service.CourseSearchService;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 8:05 pm
 */
@Api(value = "Course search interface",tags = "Course search interface")
@RestController
@RequestMapping("/course")
public class CourseSearchController {

    @Autowired
    CourseSearchService courseSearchService;


    @ApiOperation("Course search list")
    @GetMapping("/list")
    public SearchPageResultDto<CourseIndex> list(PageParams pageParams, SearchCourseParamDto searchCourseParamDto){

        return courseSearchService.queryCoursePubIndex(pageParams,searchCourseParamDto);

    }
}
