package org.studyonline.learning.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.learning.config.PayNotifyConfig;
import org.studyonline.learning.service.MyCourseTablesService;
import org.studyonline.messagesdk.model.po.MqMessage;

/**
 * @Description: Receive message notification processing class
 * @Author: Chengguang Li
 * @Date: 04/03/2024 12:23 pm
 */
@Slf4j
@Service
public class ReceivePayNotifyService {
    @Autowired
    MyCourseTablesService myCourseTablesService;

    @RabbitListener(queues = PayNotifyConfig.PAYNOTIFY_QUEUE)
    public void receive(Message message) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Parse out the message
        byte[] body = message.getBody();
        String jsonString = new String(body);
        //Convert to object
        MqMessage mqMessage = JSON.parseObject(jsonString, MqMessage.class);
        //Parse the content of the message
        //Course Id
        String chooseCourseId = mqMessage.getBusinessKey1();
        //Order Type
        String orderType = mqMessage.getBusinessKey2();
        //The Learning Center service only requires the payment of order results for purchasing courses.
        if (orderType.equals("60201")) {
            //According to the message content, update course selection records and insert records into my course schedule.
            boolean b = myCourseTablesService.saveChooseCourseSuccess(chooseCourseId);
            if (!b) {
                StudyOnlineException.cast("Failed to ensure course selection record status");
            }


        }


    }
}
