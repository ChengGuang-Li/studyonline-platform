package org.studyonline.learning.service;

import org.studyonline.base.model.RestResponse;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 04/03/2024 2:31 pm
 */
public interface LearningService {

    /**
     *
     * @param userId
     * @param courseId
     * @param teachplanId
     * @param mediaId
     * @return
     */
    public RestResponse<String> getVideo(String userId, Long courseId, Long teachplanId, String mediaId);
}
