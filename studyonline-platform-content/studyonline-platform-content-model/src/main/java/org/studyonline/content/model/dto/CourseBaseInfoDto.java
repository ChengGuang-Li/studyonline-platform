package org.studyonline.content.model.dto;

import lombok.Data;
import org.studyonline.content.model.po.CourseBase;

/**
 * @description Course Basic Info DTO
 * @author  Chengguang Li
 * @date 10/01/2024 10:00PM
 * @version 1.0
 */
@Data
public class CourseBaseInfoDto extends CourseBase {

 /**
  * Charging rules, corresponding data dictionary
  */
 private String charge;

 /**
  * Price
  */
 private Float price;


 /**
  * Original Price
  */
 private Float originalPrice;

 /**
  * Social Medial QQ
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

 /**
  * Big category name
  */
 private String mtName;

 /**
  * Small category name
  */
 private String stName;

}
