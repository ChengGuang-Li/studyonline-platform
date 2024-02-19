package org.studyonline.content.feignclient;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.studyonline.content.model.po.CourseIndex;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 10:53 pm
 */

@Component
@Slf4j
public class SearchServiceClientFallbackFactory implements FallbackFactory<SearchServiceClient> {
    @Override
    public SearchServiceClient create(Throwable throwable) {
        return new SearchServiceClient() {
            @Override
            public Boolean add(CourseIndex courseIndex) {
                log.error("Failed to add course index,{}",throwable.getMessage(),throwable);
                return false;
            }
        };
    }
}
