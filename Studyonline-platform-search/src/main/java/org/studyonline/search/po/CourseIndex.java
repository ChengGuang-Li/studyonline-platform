package org.studyonline.search.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 8:07 pm
 */
@Data
public class CourseIndex implements Serializable {

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
     * Company name
     */
    private String companyName;

    /**
     * Course Name
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
     * Main category
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
     * main category name
     */
    private String stName;



    /**
     * course difficulty level
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
     * release time
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

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
     * Original Price
     */
    private Float originalPrice;

    /**
     * Course validity days
     */
    private Integer validDays;

}