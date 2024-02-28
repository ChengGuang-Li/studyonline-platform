package org.studyonline.learning.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 26/02/2024 10:35 pm
 */
@Data
@TableName("xc_choose_course")
public class ChooseCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Course Id
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
     * Institution ID
     */
    private Long companyId;

    /**
     * Course selection type
     */
    private String orderType;

    /**
     * add time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * Course validity period (days)
     */
    private Integer validDays;

    private Float coursePrice;

    /**
     * Course selection status
     */
    private String status;

    /**
     * Start service time
     */
    private LocalDateTime validtimeStart;

    /**
     * End service time
     */
    private LocalDateTime validtimeEnd;

    /**
     * Remark
     */
    private String remarks;


}
