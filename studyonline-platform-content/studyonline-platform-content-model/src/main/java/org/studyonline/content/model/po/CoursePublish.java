package org.studyonline.content.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: Course Publish
 * @Author: Chengguang Li
 * @Date: 16/02/2024 4:37 pm
 */
@Data
@TableName("course_publish")
public class CoursePublish implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  key
     */
    private Long id;

    /**
     * Institution ID
     */
    private Long companyId;

    /**
     * Company Name
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
     * tags
     */
    private String tags;

    /**
     *  Created People
     */
    private String username;

    /**
     *
     */
    private String mt;

    /**
     * main category name
     */
    private String mtName;

    /**
     * Small classification
     */
    private String st;

    /**
     * Small classification name
     */
    private String stName;

    /**
     * Course Level
     */
    private String grade;

    /**
     * teach model
     */
    private String teachmode;

    /**
     * Course pictures
     */
    private String pic;

    /**
     * Course Introduction
     */
    private String description;

    /**
     * Course marketing information, json format
     */
    private String market;

    /**
     * All lesson plans, json format
     */
    private String teachplan;

    /**
     * Teacher information, json format
     */
    private String teachers;

    /**
     * release time
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    /**
     * Added time
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime onlineDate;

    /**
     * Removal time
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime offlineDate;

    /**
     * Post status
     */
    private String status;

    /**
     * Remark
     */
    private String remark;

    /**
     * Charging rules, corresponding data dictionary--203
     */
    private String charge;

    /**
     * Current price
     */
    private Float price;

    /**
     * original price
     */
    private Float originalPrice;

    /**
     * Course validity days
     */
    private Integer validDays;


}