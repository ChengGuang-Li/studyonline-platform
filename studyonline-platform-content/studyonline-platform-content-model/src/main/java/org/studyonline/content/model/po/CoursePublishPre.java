package org.studyonline.content.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: Course pre-release
 * @Author: Chengguang Li
 * @Date: 16/02/2024 16:35PM 4:35 pm
 */
@Data
@TableName("course_publish_pre")
public class CoursePublishPre implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Key
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
     * For people
     */
    private String users;

    /**
     * tags
     */
    private String tags;

    /**
     * created people
     */
    private String username;

    /**
     * main category
     */
    private String mt;

    /**
     * main category name
     */
    private String mtName;

    /**
     * small category
     */
    private String st;

    /**
     * small category name
     */
    private String stName;

    /**
     * Course Level
     */
    private String grade;

    /**
     * teach mode
     */
    private String teachmode;

    /**
     * course images
     */
    private String pic;

    /**
     * course introduction
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
     * Submission time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * review time
     */
    private LocalDateTime auditDate;

    /**
     * state
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
