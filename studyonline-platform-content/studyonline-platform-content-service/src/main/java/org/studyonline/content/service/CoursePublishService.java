package org.studyonline.content.service;

import org.studyonline.content.model.dto.CoursePreviewDto;

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

}
