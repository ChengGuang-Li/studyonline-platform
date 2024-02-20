package org.studyonline.search.service;

import org.studyonline.search.dto.SearchCourseParamDto;
import org.studyonline.search.dto.SearchPageResultDto;
import org.studyonline.search.po.CourseIndex;
import org.studyonline.base.model.PageParams;
/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 19/02/2024 8:12 pm
 */
public interface CourseSearchService {


    /**
     * @description Search course listings
     * @param pageParams Paging parameters
     * @param searchCourseParamDto search condition
     * @return org.studyonline.base.model.PageResult<org.studyonline.search.po.CourseIndex> curriculum schedule
     * @Author: Chengguang Li
     * @Date: 19/02/2024 8:12 pm
     */
    SearchPageResultDto<CourseIndex> queryCoursePubIndex(PageParams pageParams, SearchCourseParamDto searchCourseParamDto);

}
