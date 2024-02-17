package org.studyonline.messagesdk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.studyonline.messagesdk.model.po.MqMessage;

import java.util.List;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 17/02/2024 11:05 pm
 */
public interface MqMessageService extends IService<MqMessage> {

    /**
     * @description Scan the message table records using the same idea as scanning the video processing table
     * @param shardIndex fragment sequence number
     * @param shardTotal Total number of shards
     * @param count Number of scan records
     * @return java.util.List Message record
     * @author Chengguang Li
     * @date 17/02/2024 11:05 pm
     */
    public List<MqMessage> getMessageList(int shardIndex, int shardTotal, String messageType, int count);

    /**
     * @description Add message
     * @param businessKey1 business id
     * @param businessKey2 business id
     * @param businessKey3 business id
     * @return org.studyonline.messagesdk.model.po.MqMessage  message content
     * @author Chengguang Li
     * @date 17/02/2024 11:05 pm
     */
    public MqMessage addMessage(String messageType,String businessKey1,String businessKey2,String businessKey3);
    /**
     * @description mission accomplished
     * @param id message id
     * @return int Update successful: 1
     * @author Chengguang Li
     * @date 17/02/2024 11:05 pm
     */
    public int completed(long id);

    /**
     * @description Complete stage tasks
     * @param id message id
     * @return int Update successful: 1
     * @author Chengguang Li
     * @date 17/02/2024 11:05 pm
     */
    public int completedStageOne(long id);
    public int completedStageTwo(long id);
    public int completedStageThree(long id);
    public int completedStageFour(long id);

    /**
     * @description Query stage status
     * @param id
     * @return int
     * @author Chengguang Li
     * @date 17/02/2024 11:05 pm
     */
    public int getStageOne(long id);
    public int getStageTwo(long id);
    public int getStageThree(long id);
    public int getStageFour(long id);

}
