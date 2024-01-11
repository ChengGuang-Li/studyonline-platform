package org.studyonline.content.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.content.model.dto.CourseCategoryTreeDto;
import org.studyonline.content.service.CourseCategoryService;

import java.util.List;


/**
 * @description Course Information Editing Interface
 * @author ChengGuang
 * @date 10/01/2024 18:00PM
 * @version 1.0
 */
@RestController
@Slf4j
@Api(value = "Course classification query interface", tags = "Course classification query interface")
public class CourseCategoryController {

    @Autowired
    CourseCategoryService courseCategoryService;

    @PostMapping("/course-category/tree-nodes/{code}")
    @ApiOperation("Course classification query interface")
    public List<CourseCategoryTreeDto> queryCourseCategories(@PathVariable @ApiParam("Course Category Code") String code){
        List<CourseCategoryTreeDto> results = courseCategoryService.queryTreeNodes(code);
        return results;
    }
}
