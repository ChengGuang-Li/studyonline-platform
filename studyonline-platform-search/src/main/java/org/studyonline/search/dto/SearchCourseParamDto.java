package org.studyonline.search.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 8:06 pm
 */
@Data
@ToString
public class SearchCourseParamDto {

    //keywords
    private String keywords;

    //main category
    private String mt;

    //small category
    private String st;

    //Difficulty level
    private String grade;




}

