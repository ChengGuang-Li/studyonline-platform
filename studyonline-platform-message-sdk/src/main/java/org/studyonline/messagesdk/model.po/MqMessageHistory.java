package org.studyonline.messagesdk.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 17/02/2024 11:00 pm
 */
@Data
@TableName("mq_message_history")
public class MqMessageHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * message id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * Message type code
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
     * Processing status, 0: initial, 1: success, 2: failure
     */
    private Integer state;

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

    private String stageState1;

    private String stageState2;

    private String stageState3;

    private String stageState4;


}
