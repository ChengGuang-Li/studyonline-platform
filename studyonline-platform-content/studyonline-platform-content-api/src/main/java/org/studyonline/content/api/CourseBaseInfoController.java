package org.studyonline.content.api;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.model.dto.QueryCourseParamsDto;
import org.studyonline.content.model.po.CourseBase;

/**
 * @description Course Information Editing Interface
 * @author ChengGuang
 * @date 2023/12/29 22:30
 * @version 1.0
 */
@RestController
public class CourseBaseInfoController {

    @RequestMapping("/course/list")
    public PageResult<CourseBase>  list(PageParams pageParams, @RequestBody(required=false)  QueryCourseParamsDto queryCourseParams){

        return null;

    }
}
