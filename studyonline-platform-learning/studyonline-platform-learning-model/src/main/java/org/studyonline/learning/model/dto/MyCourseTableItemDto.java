package org.studyonline.learning.model.dto;

import lombok.Data;
import lombok.ToString;
import org.studyonline.learning.model.po.CourseTables;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:37 pm
 */
@Data
@ToString
public class MyCourseTableItemDto extends CourseTables {

    /**
     * Recent study time
     */
    private LocalDateTime learnDate;

    /**
     * Study duration
     */
    private Long learnLength;

    /**
     * Chapter id
     */
    private Long teachplanId;

    /**
     * Chapter name
     */
    private String teachplanName;


}
