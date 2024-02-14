package org.studyonline.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: Model for binding media assets and course plans
 * @Author: Chengguang Li
 * @Date: 14/02/2024 3:51 pm
 */
@Data
@ApiModel(value="BindTeachplanMediaDto", description="Teaching Plan-Media Asset Binding Submit Data")
public class BindTeachplanMediaDto {

    @ApiModelProperty(value = "Media asset file id", required = true)
    private String mediaId;

    @ApiModelProperty(value = "Media asset file name", required = true)
    private String fileName;

    @ApiModelProperty(value = "Course Plan Identification", required = true)
    private Long teachplanId;

}
