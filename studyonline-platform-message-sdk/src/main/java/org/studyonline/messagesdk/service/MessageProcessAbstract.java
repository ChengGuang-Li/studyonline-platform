package org.studyonline.messagesdk.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.studyonline.messagesdk.model.po.MqMessage;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 17/02/2024 11:03 pm
 */
@Slf4j
@Data
public abstract class MessageProcessAbstract {

    @Autowired
     MqMessageService mqMessageService;

    /**
     * @param mqMessage Execute task content
     * @return boolean true:Success，false: Failed
     * @description Task processing
     * @author Chengguang Li
     * @date 17/02/2024 11:03 pm
     */
    public abstract boolean execute(MqMessage mqMessage);


    /**
     * @description Scan the message table to perform multithreaded taskss
     * @param shardIndex fragment sequence number
     * @param shardTotal Total number of shards
     * @param messageType  Message type
     * @param count  The total number of tasks taken out at one time
     * @param timeout Estimated task execution time. If the task has not ended by this time, it will be forced to end. The unit is seconds.
     * @return void
     * @author Chengguang Li
     * @date 17/02/2024 11:03 pm
     */
    public void process(int shardIndex, int shardTotal,  String messageType,int count,long timeout) {

        try {
            //Scan message table for task list
            List<MqMessage> messageList = mqMessageService.getMessageList(shardIndex, shardTotal,messageType, count);
            //Number of tasks
            int size = messageList.size();
            log.debug("取出待处理消息"+size+"条");
            if(size<=0){
                return ;
            }

            //Create thread pool
            ExecutorService threadPool = Executors.newFixedThreadPool(size);
            //Counter
            CountDownLatch countDownLatch = new CountDownLatch(size);
            messageList.forEach(message -> {
                threadPool.execute(() -> {
                    log.debug("开始任务:{}",message);
                    //process tasks
                    try {
                        boolean result = execute(message);
                        if(result){
                            log.debug("任务执行成功:{})",message);
                            //Update task status, delete message table records, and add to history table
                            int completed = mqMessageService.completed(message.getId());
                            if (completed>0){
                                log.debug("任务执行成功:{}",message);
                            }else{
                                log.debug("任务执行失败:{}",message);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.debug("任务出现异常:{},任务:{}",e.getMessage(),message);
                    }finally {
                        //counter down
                        countDownLatch.countDown();
                    }
                    log.debug("结束任务:{}",message);

                });
            });

            //Wait, give a sufficient timeout period to prevent infinite waiting.
            // If the timeout period is reached and the processing is not completed, the task will be ended.
            countDownLatch.await(timeout, TimeUnit.SECONDS);
            System.out.println("结束....");
        } catch (InterruptedException e) {
            e.printStackTrace();

        }


    }



}