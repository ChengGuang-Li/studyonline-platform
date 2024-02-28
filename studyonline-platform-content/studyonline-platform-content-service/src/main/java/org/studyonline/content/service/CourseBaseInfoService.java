package org.studyonline.content.service;

import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.model.dto.AddCourseDto;
import org.studyonline.content.model.dto.CourseBaseInfoDto;
import org.studyonline.content.model.dto.EditCourseDto;
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
  public PageResult<CourseBase> queryCourseBaseInfoList(PageParams pageParams,QueryCourseParamsDto queryCourseParams,Long companyId);

    /*
     * @description                 Add new Course
     * @param companyId             Company Id
     * @param addCourseDto          Add Course
     * @return  org.studyonline.content.model.dto.CourseBaseInfoDto
     * @author chengguang li
     * @date 11/05/2024 14:00
     */
   public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
    /*
     * @description            Query course information by courseId
     * @param Long             Course Id
     * @return  org.studyonline.content.model.dto.CourseBaseInfoDto
     * @author chengguang li
     * @date 17/01/2024 14:00
     */
    public CourseBaseInfoDto getCourseBaseInfoById(Long id);

    /*
     * @description                                            Update course information
     * @param companyId                                        Company Id
     * @param org.studyonline.content.model.dto.EditCourseDto            Edit Course Information
     * @return  org.studyonline.content.model.dto.CourseBaseInfoDto
     * @author chengguang li
     * @date 17/01/2024 14:00
     */
    public CourseBaseInfoDto updateCourseInfo(Long companyId,EditCourseDto editCourseDto);

}
