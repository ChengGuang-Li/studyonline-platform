package org.studyonline.content.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.studyonline.content.model.dto.CourseCategoryTreeDto;

import java.util.List;


@SpringBootTest
class CourseCategoryServiceTest {
   @Autowired
    CourseCategoryService courseCategoryService;

    @Test
    public void  testCourseCategoryWhenSuccess(){
        List<CourseCategoryTreeDto> childrenNodes = courseCategoryService.queryTreeNodes("1");
        Assertions.assertNotNull(childrenNodes);
    }
}