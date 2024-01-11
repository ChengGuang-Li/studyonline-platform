package org.studyonline.content.service;

import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.model.dto.AddCourseDto;
import org.studyonline.content.model.dto.CourseBaseInfoDto;
import org.studyonline.content.model.dto.QueryCourseParamsDto;
import org.studyonline.content.model.po.CourseBase;

/**
 * @description Course Information Management Interface
 * @author ChengGuang
 * @date 01/05/2024 22:30
 * @version 1.0
 */
public interface CourseBaseInfoService {
    /*
     * @description                  Course Query Interface
     * @param pageParams             Pagination Parameters
     * @param queryCourseParamsDto   Query condition
     * @return org.studyonline.base.model.PageResult<org.studyonline.content.model.po.CourseBase>
     * @author chengguang li
     * @date 05/01/2024 22:30
     */
  public PageResult<CourseBase> queryCourseBaseInfoList(PageParams pageParams,QueryCourseParamsDto queryCourseParams);

    /*
     * @description                  Add new Course
     * @param pageParams             Company Id
     * @param queryCourseParamsDto   Add Course
     * @return  org.studyonline.content.model.dto.CourseBaseInfoDto
     * @author chengguang li
     * @date 11/05/2024 14:00
     */
   public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

}
