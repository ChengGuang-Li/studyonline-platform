package org.studyonline.learning.feignclient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.studyonline.content.model.po.CoursePublish;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 28/02/2024 5:12 pm
 */
@Slf4j
@Component
public class ContentServiceClientFallbackFactory implements FallbackFactory<ContentServiceClient> {
    @Override
    public ContentServiceClient create(Throwable throwable) {
        return new ContentServiceClient() {

            @Override
            public CoursePublish getCoursepublish(Long courseId) {
                log.error("A circuit breaker occurs when calling the content management service.:{}", throwable.toString(),throwable);
                return null;
            }
        };
    }
}
