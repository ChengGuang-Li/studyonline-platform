package org.studyonline.messagesdk.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 17/02/2024 10:59 pm
 */
@Data
@ToString
@TableName("mq_message")
public class MqMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * message id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Message type code: course_publish ,  media_test,
     */
    private String messageType;

    /**
     * Related business information
     */
    private String businessKey1;

    /**
     * Related business information
     */
    private String businessKey2;

    /**
     * Related business information
     */
    private String businessKey3;

    /**
     * Number of executions
     */
    private Integer executeNum;

    /**
     * Processing status, 0: initial, 1: successful
     */
    private String state;

    /**
     * Reply failure time
     */
    private LocalDateTime returnfailureDate;

    /**
     * Reply success time
     */
    private LocalDateTime returnsuccessDate;

    /**
     * Reply failed content
     */
    private String returnfailureMsg;

    /**
     * Last execution time
     */
    private LocalDateTime executeDate;

    /**
     * Phase 1 processing status, 0: initial, 1: successful
     */
    private String stageState1;

    /**
     * Phase 2 processing status, 0: initial, 1: successful
     */
    private String stageState2;

    /**
     * Phase 3 processing status, 0: initial, 1: successful
     */
    private String stageState3;

    /**
     * Phase 4 processing status, 0: initial, 1: successful
     */
    private String stageState4;

}

