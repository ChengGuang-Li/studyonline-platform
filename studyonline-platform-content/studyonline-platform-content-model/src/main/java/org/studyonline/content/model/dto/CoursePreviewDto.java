package org.studyonline.content.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: Course preview data model
 * @Author: Chengguang Li
 * @Date: 14/02/2024 9:57 pm
 */
@Data
@ToString
public class CoursePreviewDto {
    //Basic course information, course marketing information
    CourseBaseInfoDto courseBase;

    //Lesson Plan Information
    List<TeachplanDto> teachplans;

    //Teacher information


}
