package org.studyonline.content.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="EditCourseDto", description="Modify basic course information")
public class EditCourseDto extends AddCourseDto {

    @ApiModelProperty(value = "Course id", required = true)
    private Long id;

}
