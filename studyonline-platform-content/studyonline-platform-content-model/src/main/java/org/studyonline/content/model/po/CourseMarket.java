package org.studyonline.content.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *      Course Marketing Information
 * </p>
 *
 * @author Chengguang Li
 */
@Data
@TableName("course_market")
public class CourseMarket implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Key, Course Id
     */
    private Long id;

    /**
     * Charging rules, corresponding data dictionary
     */
    private String charge;

    /**
     * Current price
     */
    private Float price;

    /**
     * Original price
     */
    private Float originalPrice;

    /**
     * Social Medial:  QQ
     */
    private String qq;

    /**
     * Wechat
     */
    private String wechat;

    /**
     * Phone
     */
    private String phone;

    /**
     * Validity days
     */
    private Integer validDays;


}