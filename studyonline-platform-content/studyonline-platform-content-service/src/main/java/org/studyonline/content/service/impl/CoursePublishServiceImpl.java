package org.studyonline.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.content.model.dto.CourseBaseInfoDto;
import org.studyonline.content.model.dto.CoursePreviewDto;
import org.studyonline.content.model.dto.TeachplanDto;
import org.studyonline.content.service.CourseBaseInfoService;
import org.studyonline.content.service.CoursePublishService;
import org.studyonline.content.service.TeachplanService;

import java.util.List;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 14/02/2024 10:03 pm
 */
@Service
public class CoursePublishServiceImpl implements CoursePublishService {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    TeachplanService teachplanService;

    @Override
    public CoursePreviewDto getCoursePreviewInfo(Long courseId) {
        //Basic course information, course marketing information
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfoById(courseId);

        //Lesson Plan Information
        List<TeachplanDto> teachplanTree= teachplanService.findTeachplanTree(courseId);

        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        coursePreviewDto.setCourseBase(courseBaseInfo);
        coursePreviewDto.setTeachplans(teachplanTree);
        return coursePreviewDto;

    }
}
