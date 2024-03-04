package org.studyonline.learning.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.base.model.RestResponse;
import org.studyonline.content.model.po.CoursePublish;
import org.studyonline.learning.feignclient.ContentServiceClient;
import org.studyonline.learning.feignclient.MediaServiceClient;
import org.studyonline.learning.model.dto.CourseTablesDto;
import org.studyonline.learning.service.LearningService;
import org.studyonline.learning.service.MyCourseTablesService;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 04/03/2024 2:34 pm
 */

@Service
@Slf4j
public class LearningServiceImple implements LearningService {

    @Autowired
    MyCourseTablesService myCourseTablesService;

    @Autowired
    ContentServiceClient contentServiceClient;

    @Autowired
    MediaServiceClient mediaServiceClient;

    @Override
    public RestResponse<String> getVideo(String userId, Long courseId, Long teachplanId, String mediaId) {
        //Query course information
        CoursePublish coursepublish = contentServiceClient.getCoursepublish(courseId);
        if(coursepublish == null){
            return RestResponse.validfail("Course does not exist");
        }


        //User is logged in
        if(StringUtils.isNotEmpty(userId)){
            //Get study qualifications
            CourseTablesDto learningStatus = myCourseTablesService.getLearningStatus(userId, courseId);
            //Study qualifications, [{"code":"702001","desc":"Normal study"},{"code":"702002","desc":"No course selection or no payment after course selection"},{"code ":"702003","desc":"Expired and need to apply for renewal or repayment"}]
            String learnStatus = learningStatus.getLearnStatus();
            if("702002".equals(learnStatus)){
                return RestResponse.validfail("Unable to study because I did not choose a course or did not pay after choosing a course");
            }else if("702003".equals(learnStatus)){
                return RestResponse.validfail("Expired and need to apply for renewal or repayment");
            }else{
                //If you are qualified to study, you need to return the playback address of the video.
                RestResponse<String> playUrlByMediaId = mediaServiceClient.getPlayUrlByMediaId(mediaId);
                return playUrlByMediaId;

            }

        }
        //If the user is not logged in
        //Get the fee rules for the course
        String charge = coursepublish.getCharge();
        if("201000".equals(charge)){
            //If you are qualified to study, you need to return the playback address of the video.
            RestResponse<String> playUrlByMediaId = mediaServiceClient.getPlayUrlByMediaId(mediaId);
            return playUrlByMediaId;
        }
        return RestResponse.validfail("Courses need to be purchased");
    }
}
