package org.studyonline.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.model.po.CoursePublish;
import org.studyonline.learning.feignclient.ContentServiceClient;
import org.studyonline.learning.mapper.ChooseCourseMapper;
import org.studyonline.learning.mapper.CourseTablesMapper;
import org.studyonline.learning.model.dto.ChooseCourseDto;
import org.studyonline.learning.model.dto.CourseTablesDto;
import org.studyonline.learning.model.dto.MyCourseTableParams;
import org.studyonline.learning.model.po.ChooseCourse;
import org.studyonline.learning.model.po.CourseTables;
import org.studyonline.learning.service.MyCourseTablesService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 28/02/2024 5:08 pm
 */
@Slf4j
@Service
public class MyCourseTablesServiceImpl implements MyCourseTablesService {
    @Autowired
    ChooseCourseMapper chooseCourseMapper;

    @Autowired
    CourseTablesMapper courseTablesMapper;

    @Autowired
    ContentServiceClient contentServiceClient;



    @Override
    public ChooseCourseDto addChooseCourse(String userId, Long courseId) {
        //Select a course and call content management to query the charging rules of the course.
        CoursePublish coursepublish = contentServiceClient.getCoursepublish(courseId);
        if(coursepublish == null){
            StudyOnlineException.cast("Course does not exist");
        }
        //Charging rules
        String charge = coursepublish.getCharge();
        //Course selection records
        ChooseCourse chooseCourse = null;
        if("201000".equals(charge)){//Free Course
            //Write to the course selection record form
            chooseCourse = addFreeCoruse(userId, coursepublish);
            //Write to my class schedule
            CourseTables courseTables = addCourseTabls(chooseCourse);

        }else{
            //If there is a fee-based course, data will be written to the course selection record form.
            chooseCourse = addChargeCoruse(userId, coursepublish);
        }

        //  Determine a student’s academic qualifications
        CourseTablesDto xcCourseTablesDto = getLearningStatus(userId, courseId);

        //Construct return value
        ChooseCourseDto xcChooseCourseDto = new ChooseCourseDto();
        BeanUtils.copyProperties(chooseCourse,xcChooseCourseDto);
        //Set study eligibility status
        xcChooseCourseDto.setLearnStatus(xcCourseTablesDto.getLearnStatus());

        return xcChooseCourseDto;
    }

    @Override
    public CourseTablesDto getLearningStatus(String userId, Long courseId) {
        CourseTablesDto courseTablesDto = new CourseTablesDto();
        //Check my course schedule. If you can’t find it, it means you haven’t selected a course.
        CourseTables courseTables = getXcCourseTables(userId, courseId);
        if(courseTables == null){
            //"code":"702002","desc":"No course selection or no payment after course selection"
            courseTablesDto.setLearnStatus("702002");
            return courseTablesDto;
        }

        //If it is found, determine whether it is expired. If it is expired, you cannot continue learning. If it is not expired, you can continue learning.
        boolean before = courseTables.getValidtimeEnd().isBefore(LocalDateTime.now());
        if(before){
            //"code":"702003","desc":"Expired and need to apply for renewal or repayment"
            BeanUtils.copyProperties(courseTables,courseTablesDto);
            courseTablesDto.setLearnStatus("702003");
        }else{
            //"code":"702001","desc":"Normal learning"
            BeanUtils.copyProperties(courseTables,courseTablesDto);
            courseTablesDto.setLearnStatus("702001");
        }
        return courseTablesDto;
    }

    @Override
    public boolean saveChooseCourseSuccess(String chooseCourseId) {
        //query course_selection table by course Id
        ChooseCourse chooseCourse = chooseCourseMapper.selectById(chooseCourseId);
        if(chooseCourse == null){
            log.debug("Receive the message to purchase the course, but cannot find the course selection record from the database based on the course selection ID, the course selection ID :{}",chooseCourseId);
            return false;
        }
        String status = chooseCourse.getStatus(); //course status
        //Update to paid only if unpaid
        if("701002".equals(status)){
            //Update the status of the course selection record to payment successful
            chooseCourse.setStatus("701001");
            int i = chooseCourseMapper.updateById(chooseCourse);
            if(i<=0){
                log.debug("Failed to add course selection record:{}",chooseCourse);
                StudyOnlineException.cast("Failed to add course selection record");
            }

            //Insert records into my course schedule
            addCourseTabls(chooseCourse);
            return true;
        }

        return false;
    }

    @Override
    public PageResult<CourseTables> mycoursetables(MyCourseTableParams params) {
        String userId = params.getUserId();
        int pageNo = params.getPage();
        int size = params.getSize();
        Page<CourseTables> courseTablesPage = new Page<>(pageNo, size);
        LambdaQueryWrapper<CourseTables> lambdaQueryWrapper = new LambdaQueryWrapper<CourseTables>().eq(CourseTables::getUserId, userId);
        Page<CourseTables> result = courseTablesMapper.selectPage(courseTablesPage, lambdaQueryWrapper);
        List<CourseTables> records = result.getRecords();
        long total = result.getTotal();
        PageResult pageResult = new PageResult(records, total, pageNo, size);
        return pageResult;
    }


    /** Add a paid course
     *
     * @param userId
     * @param coursepublish
     * @return
     */
    public ChooseCourse addChargeCoruse(String userId, CoursePublish coursepublish){

        //Course Id
        Long courseId = coursepublish.getId();
        //If there is a paid course selection record and the course selection status is pending payment, return directly.
        LambdaQueryWrapper<ChooseCourse> queryWrapper = new LambdaQueryWrapper<ChooseCourse>().eq(ChooseCourse::getUserId, userId)
                .eq(ChooseCourse::getCourseId, courseId)
                .eq(ChooseCourse::getOrderType, "700002")//Paid courses
                .eq(ChooseCourse::getStatus, "701002");//To be paid
        List<ChooseCourse> chooseCours = chooseCourseMapper.selectList(queryWrapper);
        if(chooseCours.size()>0){
            return chooseCours.get(0);
        }

        //Write data to the course selection record table
        ChooseCourse chooseCourse = new ChooseCourse();

        chooseCourse.setCourseId(courseId);
        chooseCourse.setCourseName(coursepublish.getName());
        chooseCourse.setUserId(userId);
        chooseCourse.setCompanyId(coursepublish.getCompanyId());
        chooseCourse.setOrderType("700002");//Paid courses
        chooseCourse.setCreateDate(LocalDateTime.now());
        chooseCourse.setCoursePrice(coursepublish.getPrice());
        chooseCourse.setValidDays(365);
        chooseCourse.setStatus("701002");//To be paid
        chooseCourse.setValidtimeStart(LocalDateTime.now());//Validity start time
        chooseCourse.setValidtimeEnd(LocalDateTime.now().plusDays(365));//Validity end time

        int insert = chooseCourseMapper.insert(chooseCourse);
        if(insert<=0){
            StudyOnlineException.cast("Failed to add course selection record");
        }

        return chooseCourse;

    }

    /**
     * Query a course in my course schedule based on courses and users
     * @param userId
     * @param courseId
     * @return
     */
    public CourseTables getXcCourseTables(String userId, Long courseId){
        CourseTables courseTables = courseTablesMapper.selectOne(new LambdaQueryWrapper<CourseTables>().eq(CourseTables::getUserId, userId).eq(CourseTables::getCourseId, courseId));
        return courseTables;

    }

    /**
     * Add data to my course schedule
     * @param chooseCourse
     * @return
     */
    public CourseTables addCourseTabls(ChooseCourse chooseCourse){

        //Only after successful course selection can you add it to my course schedule.
        String status = chooseCourse.getStatus();
        if(!"701001".equals(status)){
            //701001 -> Course Selection Successfully
            StudyOnlineException.cast("The course selection was unsuccessful and could not be added to the course schedule.");
        }
        CourseTables courseTables = getXcCourseTables(chooseCourse.getUserId(), chooseCourse.getCourseId());
        if(courseTables !=null){
            return courseTables;
        }

        courseTables = new CourseTables();
        BeanUtils.copyProperties(chooseCourse, courseTables);
        courseTables.setChooseCourseId(chooseCourse.getId());//Record the progress of the course selection schedule
        courseTables.setCourseType(chooseCourse.getOrderType());//Course selection type
        courseTables.setUpdateDate(LocalDateTime.now());
        int insert = courseTablesMapper.insert(courseTables);
        if(insert<=0){
            StudyOnlineException.cast("Adding my course schedule failed");
        }

        return courseTables;
    }

    /**
     *  Add free courses. Free courses are added to the course selection record and my course schedule.
     * @param userId
     * @param coursepublish
     * @return
     */
    public ChooseCourse addFreeCoruse(String userId, CoursePublish coursepublish) {
        //Course Id
        Long courseId = coursepublish.getId();
        //If there is a free course selection record and the course selection status is successful, it will be returned directly.
        LambdaQueryWrapper<ChooseCourse> queryWrapper = new LambdaQueryWrapper<ChooseCourse>().eq(ChooseCourse::getUserId, userId)
                .eq(ChooseCourse::getCourseId, courseId)
                .eq(ChooseCourse::getOrderType, "700001")//free courses
                .eq(ChooseCourse::getStatus, "701001");//Course selection successful
        List<ChooseCourse> chooseCours = chooseCourseMapper.selectList(queryWrapper);
        if(chooseCours.size()>0){
            return chooseCours.get(0);
        }

        //Write data to the course selection record form
        ChooseCourse chooseCourse = new ChooseCourse();

        chooseCourse.setCourseId(courseId);
        chooseCourse.setCourseName(coursepublish.getName());
        chooseCourse.setUserId(userId);
        chooseCourse.setCompanyId(coursepublish.getCompanyId());
        chooseCourse.setOrderType("700001");//Free Course
        chooseCourse.setCreateDate(LocalDateTime.now());
        chooseCourse.setCoursePrice(coursepublish.getPrice());
        chooseCourse.setValidDays(365);
        chooseCourse.setStatus("701001");//Course selection successful
        chooseCourse.setValidtimeStart(LocalDateTime.now());//Validity start time
        chooseCourse.setValidtimeEnd(LocalDateTime.now().plusDays(365));//Validity end time

        int insert = chooseCourseMapper.insert(chooseCourse);
        if(insert<=0){
            StudyOnlineException.cast("Failed to add course selection record");
        }

        return chooseCourse;
    }

}
