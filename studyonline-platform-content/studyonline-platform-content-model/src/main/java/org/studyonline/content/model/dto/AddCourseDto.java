package org.studyonline.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.studyonline.base.exception.ValidationGroups;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @description Add Course dto
 * @author  Chengguang Li
 * @date 10/01/2024 10:00PM
 * @version 1.0
 */
@Data
@ApiModel(value="AddCourseDto", description="add new course information")
public class AddCourseDto {

 @NotEmpty(message = "The name of the new course cannot be empty",groups={ValidationGroups.Insert.class})
 @NotEmpty(message = "Modify course name cannot be empty",groups={ValidationGroups.Update.class})
 @ApiModelProperty(value = "Course Name", required = true)
 private String name;

 @NotEmpty(message = "Target people cannot be empty")
 @Size(message = "Too little content for the target audience",min = 10)
 @ApiModelProperty(value = "Target People", required = true)
 private String users;

 @ApiModelProperty(value = "Course Tags")
 private String tags;

 @NotEmpty(message = "Course Category Cannot Be Empty")
 @ApiModelProperty(value = "Big Category", required = true)
 private String mt;

 @NotEmpty(message = "Course Category Cannot Be Empty")
 @ApiModelProperty(value = "Small Category", required = true)
 private String st;

 @NotEmpty(message = "Course Level Cannot Be Empty ")
 @ApiModelProperty(value = "Course Level", required = true)
 private String grade;

 @ApiModelProperty(value = "Teaching Mode", required = true)
 private String teachmode;

 @ApiModelProperty(value = "Course Description")
 private String description;

 @ApiModelProperty(value = "Course Images", required = true)
 private String pic;

 @NotEmpty(message = "Charging rules cannot be empty")
 @ApiModelProperty(value = "Charging rules, corresponding data dictionary", required = true)
 private String charge;

 @ApiModelProperty(value = "Price")
 private Float price;

 @ApiModelProperty(value = "original Price")
 private Float originalPrice;


 @ApiModelProperty(value = "Social Medial")
 private String qq;

 @ApiModelProperty(value = "Wechat")
 private String wechat;
 @ApiModelProperty(value = "Phone")
 private String phone;

 @ApiModelProperty(value = "Validity period")
 private Integer validDays;
}
