package org.studyonline.content.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.studyonline.content.model.po.Teachplan;
import org.studyonline.content.model.po.TeachplanMedia;

import java.util.List;

/**
 * @description Course plan tree structure dto
 * @author  Chengguang Li
 * @date 23/01/2024
 * @version 1.0
 */

@Data
@ToString
public class TeachplanDto extends Teachplan {

    @ApiModelProperty(value = "Course Media Info", required = true)
    private TeachplanMedia teachplanMedia;

    @ApiModelProperty(value = "Lesson Plans Subdirectory", required = true)
    private List<TeachplanDto> teachPlanTreeNodes;

}
