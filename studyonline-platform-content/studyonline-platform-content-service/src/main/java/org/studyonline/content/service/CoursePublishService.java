package org.studyonline.content.service;

import org.studyonline.content.model.dto.CoursePreviewDto;
import org.studyonline.content.model.po.CoursePublish;

import java.io.File;

/**
 * @Description: Course preview and publishing interface
 * @Author: Chengguang Li
 * @Date: 14/02/2024 10:00 pm
 */
public interface CoursePublishService {

    /**
     * @description Get course preview information
     * @param courseId  CourseId
     * @return org.studyonline.content.model.dto.CoursePreviewDto
     * @author Chengguang Li
     * @date 14/02/2024 10:00 pm
     */
    public CoursePreviewDto getCoursePreviewInfo(Long courseId);


    /**
     * @description Submit review
     * @param courseId  course id
     * @return void
     * @author Chengguang Li
     * @date 16/02/2024 10:00 pm
     */
    public void commitAudit(Long companyId,Long courseId);

    /**
     * @description Course publishing interface
     * @param companyId Institution ID
     * @param courseId Course id
     * @return void
     * @author Chengguang Li
     * @date 16/02/2024 10:00 pm
     */
    public void publish(Long companyId,Long courseId);

    /**
     * @description Static course
     * @param courseId  course id
     * @return File Static files
     * @author Chengguang Li
     * @date 16/02/2024 10:00 pm
     */
    public File generateCourseHtml(Long courseId);
    /**
     * @description Upload course static page
     * @param file  Static files
     * @return void
     * @author Chengguang Li
     * @date 16/02/2024 10:00 pm
     */
    public void  uploadCourseHtml(Long courseId, File file);

    /**
     * Query course release information based on course id
     * @param courseId
     * @return
     */
    public CoursePublish getCoursePublish(Long courseId);

    public CoursePublish getCoursePublishCache(Long courseId);
}
