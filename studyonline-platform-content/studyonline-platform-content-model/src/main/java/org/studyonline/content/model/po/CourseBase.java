package org.studyonline.content.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * Basic course information
 * </p>
 *
 * @author ChengGuang
 */
@Data
@TableName("course_base")
public class CourseBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Institution ID
     */
    private Long companyId;

    /**
     * institution name
     */
    private String companyName;

    /**
     * Course Title
     */
    private String name;

    /**
     * Target People
     */
    private String users;

    /**
     * Course Tags
     */
    private String tags;

    /**
     * main category
     */
    private String mt;

    /**
     * Small classifications
     */
    private String st;

    /**
     * Course Level
     */
    private String grade;

    /**
     * Educational Modes(Common, Recorded, Live,etc.)
     */
    private String teachmode;

    /**
     * Course Introduction
     */
    private String description;

    /**
     * Course pictures
     */
    private String pic;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * Edit Time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changeDate;

    /**
     * Creator
     */
    private String createPeople;

    /**
     * Updater
     */
    private String changePeople;

    /**
     * Review Status
     */
    private String auditStatus;

    /**
     * Course Publishing Status ( Not Published, Published, Offline)
     */
    private String status;

}
