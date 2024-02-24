package org.studyonline.content.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.studyonline.content.model.dto.CoursePreviewDto;
import org.studyonline.content.service.CoursePublishService;
import org.studyonline.content.util.SecurityUtil;

/**
 * @Description: Course preview, release
 * @Author: Chengguang Li
 * @Date: 14/02/2024 9:04 pm
 */

@Controller
public class CoursePublishController {
    @Autowired
    CoursePublishService coursePublishService;

    @GetMapping("/coursepreview/{courseId}")
    public ModelAndView preview(@PathVariable("courseId") Long courseId){
        //Get course preview information
        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(courseId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("model",coursePreviewInfo);
        modelAndView.setViewName("course_template");
        return modelAndView;
    }

    @ApiOperation("Course Submission")
    @ResponseBody
    @PostMapping("/courseaudit/commit/{courseId}")
    public void commitAudit(@PathVariable("courseId") Long courseId){
        SecurityUtil.User user = SecurityUtil.getUser();
        Long companyId = Long.parseLong(user.getCompanyId());
        coursePublishService.commitAudit(companyId,courseId);
    }


    @ApiOperation("Course Publish")
    @ResponseBody
    @PostMapping ("/coursepublish/{courseId}")
    public void coursepublish(@PathVariable("courseId") Long courseId){
        SecurityUtil.User user = SecurityUtil.getUser();
        Long companyId = Long.parseLong(user.getCompanyId());
        coursePublishService.publish(companyId,courseId);

    }

}
