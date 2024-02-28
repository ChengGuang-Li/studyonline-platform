package org.studyonline.content.service.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.content.feignclient.SearchServiceClient;
import org.studyonline.content.mapper.CoursePublishMapper;
import org.studyonline.content.model.po.CourseIndex;
import org.studyonline.content.model.po.CoursePublish;
import org.studyonline.content.service.CoursePublishService;
import org.studyonline.messagesdk.model.po.MqMessage;
import org.studyonline.messagesdk.service.MessageProcessAbstract;
import org.studyonline.messagesdk.service.MqMessageService;

import java.io.File;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 18/02/2024 12:07 am
 */
@Slf4j
@Component
public class CoursePublishTask extends MessageProcessAbstract {

    @Autowired
    CoursePublishService coursePublishService;

    @Autowired
    SearchServiceClient searchServiceClient;

    @Autowired
    CoursePublishMapper coursePublishMapper;


    //Task scheduling entrance
    @XxlJob("CoursePublishJobHandler")
    public void coursePublishJobHandler() throws Exception {
        // Sharding parameters
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        log.debug("shardIndex="+shardIndex+",shardTotal="+shardTotal);
        //Parameters: fragment serial number, total number of fragments,
        // message type, maximum number of tasks fetched at one time, timeout for task scheduling execution
        process(shardIndex,shardTotal,"course_publish",30,60);
    }

    @Override
    public boolean execute(MqMessage mqMessage) {
        String businessKey1 = mqMessage.getBusinessKey1();
        long courseId = Long.parseLong(businessKey1);
        //Course static page upload to MinIo


        return false;
    }


    private void generateCourseHtml(MqMessage mqMessage,long courseId){
        log.debug("Start making the course static, course id:{}",courseId);
        //message id
        Long id = mqMessage.getId();
        //Message processing service
        MqMessageService mqMessageService = this.getMqMessageService();
        //Message idempotence processing
        int stageOne = mqMessageService.getStageOne(id);
        if(stageOne >0){
            log.debug("The course staticization has been processed and returned directly, course id:{}",courseId);
            return ;
        }
        // generate static html pages
        File file = coursePublishService.generateCourseHtml(courseId);
        if(file == null){
            StudyOnlineException.cast("Static pages is empty");
        }
        //upload file to MinIo
        coursePublishService.uploadCourseHtml(courseId,file);

        //Save the first stage state
        mqMessageService.completedStageOne(id);
    }

    //Cache course information to redis
    private void saveCourseCache(MqMessage mqMessage,long courseId){
        log.debug("Cache course information to redis, course id:{}",courseId);
        //TODO: save course info into cache
    }

    //Save course index information
    private void saveCourseIndex(MqMessage mqMessage,long courseId){
        log.debug("Save course index information, course id:{}",courseId);
        Long id = mqMessage.getId();
        MqMessageService mqMessageService = this.getMqMessageService();
        int stageTwo = mqMessageService.getStageTwo(id);
        if(stageTwo > 0){
            log.debug("The course index information has been written, no need to execute");
            return;
        }
        // Save Index Info
        CoursePublish coursePublish = coursePublishMapper.selectById(courseId);

        CourseIndex courseIndex = new CourseIndex();
        BeanUtils.copyProperties(coursePublish,courseIndex);
        Boolean add = searchServiceClient.add(courseIndex);
        if(!add){
             StudyOnlineException.cast("Remote call to add course index failed");
        }

        mqMessageService.completedStageTwo(courseId);

    }

}
