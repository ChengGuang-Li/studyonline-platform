package org.studyonline.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @description Course Query Parameters DTO
 * @author ChengGuang
 * @date 2023/12/29 22:30
 * @version 1.0
 */
@Data
@ToString
public class QueryCourseParamsDto {

    //Review Status
    private String auditStatus;

    //course name
    private String courseName;

    //Course Publishing Status 
    private String publishStatus;
}
