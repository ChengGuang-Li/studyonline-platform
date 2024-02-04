package org.studyonline.media.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("mq_message")
public class MqMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * message id
     */
    private String id;

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
     * message queue host
     */
    private String mqHost;

    /**
     * message queue port
     */
    private Integer mqPort;

    /**
     * Message Queuing Virtual Host
     */
    private String mqVirtualhost;

    /**
     * queue name
     */
    private String mqQueue;

    /**
     * Notification times
     */
    private Integer informNum;

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
     * Last notification time
     */
    private LocalDateTime informDate;
}
