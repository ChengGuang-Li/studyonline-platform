package org.studyonline.messagesdk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studyonline.messagesdk.mapper.MqMessageHistoryMapper;
import org.studyonline.messagesdk.mapper.MqMessageMapper;
import org.studyonline.messagesdk.model.po.MqMessage;
import org.studyonline.messagesdk.model.po.MqMessageHistory;
import org.studyonline.messagesdk.service.MqMessageService;

import java.util.List;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 17/02/2024 11:07 pm
 */
@Slf4j
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage> implements MqMessageService {

    @Autowired
    MqMessageMapper mqMessageMapper;

    @Autowired
    MqMessageHistoryMapper mqMessageHistoryMapper;


    @Override
    public List<MqMessage> getMessageList(int shardIndex, int shardTotal, String messageType, int count) {
        return mqMessageMapper.selectListByShardIndex(shardTotal,shardIndex,messageType,count);
    }

    @Override
    public MqMessage addMessage(String messageType, String businessKey1, String businessKey2, String businessKey3) {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setMessageType(messageType);
        mqMessage.setBusinessKey1(businessKey1);
        mqMessage.setBusinessKey2(businessKey2);
        mqMessage.setBusinessKey3(businessKey3);
        int insert = mqMessageMapper.insert(mqMessage);
        if(insert>0){
            return mqMessage;
        }else{
            return null;
        }

    }

    @Transactional
    @Override
    public int completed(long id) {
        MqMessage mqMessage = new MqMessage();
        //mission accomplished
        mqMessage.setState("1");
        int update = mqMessageMapper.update(mqMessage, new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId, id));
        if(update>0){

            mqMessage = mqMessageMapper.selectById(id);
            //Add to history table
            MqMessageHistory mqMessageHistory = new MqMessageHistory();
            BeanUtils.copyProperties(mqMessage,mqMessageHistory);
            mqMessageHistoryMapper.insert(mqMessageHistory);
            //Delete message
            mqMessageMapper.deleteById(id);
            return 1;
        }
        return 0;

    }

    @Override
    public int completedStageOne(long id) {
        MqMessage mqMessage = new MqMessage();
        //Complete stage 1 mission
        mqMessage.setStageState1("1");
        return mqMessageMapper.update(mqMessage,new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId,id));
    }

    @Override
    public int completedStageTwo(long id) {
        MqMessage mqMessage = new MqMessage();
        //Complete stage 2 mission
        mqMessage.setStageState2("1");
        return mqMessageMapper.update(mqMessage,new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId,id));
    }

    @Override
    public int completedStageThree(long id) {
        MqMessage mqMessage = new MqMessage();
        //Complete stage 3 mission
        mqMessage.setStageState3("1");
        return mqMessageMapper.update(mqMessage,new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId,id));
    }

    @Override
    public int completedStageFour(long id) {
        MqMessage mqMessage = new MqMessage();
        //Complete stage 4 mission
        mqMessage.setStageState4("1");
        return mqMessageMapper.update(mqMessage,new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId,id));
    }

    @Override
    public int getStageOne(long id) {
        return Integer.parseInt(mqMessageMapper.selectById(id).getStageState1());
    }

    @Override
    public int getStageTwo(long id) {
        return Integer.parseInt(mqMessageMapper.selectById(id).getStageState2());
    }

    @Override
    public int getStageThree(long id) {
        return Integer.parseInt(mqMessageMapper.selectById(id).getStageState3());
    }

    @Override
    public int getStageFour(long id) {
        return Integer.parseInt(mqMessageMapper.selectById(id).getStageState4());
    }


}
