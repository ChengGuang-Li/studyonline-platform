package org.studyonline.learning.service;

import org.studyonline.learning.model.dto.ChooseCourseDto;
import org.studyonline.learning.model.dto.CourseTablesDto;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 28/02/2024 5:07 pm
 */
public interface MyCourseTablesService {

    /**
     * @description  Add course selection
     * @param userId user id
     * @param courseId course id
     * @return org.studyonline.learning.model.dto.ChooseCourseDto
     * @author: Chengguang Li
     * @date: 28/02/2024 5:07 pm
     */
    public ChooseCourseDto addChooseCourse(String userId, Long courseId);

    /**
     * @description Determine study qualifications
     * @param userId
     * @param courseId
     * @return CourseTablesDto Study qualification status [{"code":"702001","desc":"Normal study"},{"code":"702002","desc":"No course selection or no payment after course selection"},{"code ":"702003","desc":"Expired and need to apply for renewal or repayment"}]
     * @author: Chengguang Li
     * @date: 28/02/2024 5:07 pm
     */
    public CourseTablesDto getLearningStatus(String userId, Long courseId);
}
