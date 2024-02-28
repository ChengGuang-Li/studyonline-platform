package org.studyonline.learning.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:36 pm
 */
@Data
@TableName("xc_course_tables")
public class CourseTables implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Course selection record id
     */
    private Long chooseCourseId;

    /**
     * user id
     */
    private String userId;

    /**
     * course id
     */
    private Long courseId;

    /**
     * Institution ID
     */
    private Long companyId;

    /**
     * Course Name
     */
    private String courseName;
    /**
     * Course Type
     */
    private String courseType;


    /**
     * create date
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * Start service time
     */
    private LocalDateTime validtimeStart;

    /**
     * Expire date
     */
    private LocalDateTime validtimeEnd;

    /**
     * Update time
     */
    private LocalDateTime updateDate;

    /**
     * Remark
     */
    private String remarks;


}