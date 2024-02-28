package org.studyonline.content.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.studyonline.ContentServiceApplication;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.model.dto.QueryCourseParamsDto;
import org.studyonline.content.model.po.CourseBase;

@Slf4j
@SpringBootTest(classes = ContentServiceApplication.class)
class CourseBaseInfoServiceTest {
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Test
    public void testCourseBaseInfoService() {
        //query condition
        QueryCourseParamsDto queryCourseParamsDto = new QueryCourseParamsDto();
        queryCourseParamsDto.setCourseName("java");
        queryCourseParamsDto.setAuditStatus("202004");
        queryCourseParamsDto.setPublishStatus("203001");

        //Pagination parameters
        PageParams pageParams = new PageParams(1L, 10L);
        Long companyId = 12345678L;
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseInfoList(pageParams, queryCourseParamsDto,companyId);
        Assertions.assertNotNull(courseBasePageResult);
    }

}