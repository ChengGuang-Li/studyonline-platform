package org.studyonline.learning.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:36 pm
 */
@Data
@TableName("xc_learn_record")
public class LearnRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * course id
     */
    private Long courseId;

    /**
     * Course Name
     */
    private String courseName;

    /**
     * User Id
     */
    private String userId;

    /**
     * Recent Study Time
     */
    private LocalDateTime learnDate;

    /**
     * Study Duration
     */
    private Long learnLength;

    /**
     * Chapter Id
     */
    private Long teachplanId;

    /**
     * Chapter Name
     */
    private String teachplanName;


}
