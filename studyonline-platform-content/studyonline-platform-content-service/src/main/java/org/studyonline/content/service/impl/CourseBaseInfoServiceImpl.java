package org.studyonline.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.mapper.CourseBaseMapper;
import org.studyonline.content.mapper.CourseCategoryMapper;
import org.studyonline.content.mapper.CourseMarketMapper;
import org.studyonline.content.model.dto.AddCourseDto;
import org.studyonline.content.model.dto.CourseBaseInfoDto;
import org.studyonline.content.model.dto.EditCourseDto;
import org.studyonline.content.model.dto.QueryCourseParamsDto;
import org.studyonline.content.model.po.CourseBase;
import org.studyonline.content.model.po.CourseCategory;
import org.studyonline.content.model.po.CourseMarket;
import org.studyonline.content.service.CourseBaseInfoService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description Course Information Management Implementation
 * @author ChengGuang
 * @date 01/05/2024 22:30
 * @version 1.0
 */

@Service
@Slf4j
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseInfoList(PageParams pageParams, QueryCourseParamsDto queryCourseParams) {
         //Build query object
         LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
         //Build query conditions
         queryWrapper.like(StringUtils.isNotEmpty(queryCourseParams.getCourseName()), CourseBase::getName,queryCourseParams.getCourseName());
         queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParams.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParams.getAuditStatus());
         queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParams.getPublishStatus()),CourseBase::getStatus,queryCourseParams.getPublishStatus());

         //Pagination Object
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        // Retrieve results based on the queried data content.
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        List<CourseBase> list = pageResult.getRecords();
        long total = pageResult.getTotal();
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto) {
        //Insert data into Course_Base Table
        CourseBase courseBaseNew = new CourseBase();
        BeanUtils.copyProperties(addCourseDto,courseBaseNew);
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCreateDate(LocalDateTime.now());
        courseBaseNew.setStatus("203001");
        courseBaseNew.setAuditStatus("202002");
        int insert = courseBaseMapper.insert(courseBaseNew);
        if(insert < 1){
            throw new RuntimeException("Failed to add basic course information");
        }
        //Insert data into  Course_Market Table
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(addCourseDto,courseMarket);
        courseMarket.setId(courseBaseNew.getId());//Course Id
        //save course market info
        int isSuccessed = saveCourseMarket(courseMarket);
        if(isSuccessed < 1){
            throw new RuntimeException("Failed to add basic course information");
        }
        //get course info for return
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfoById(courseBaseNew.getId());

        return courseBaseInfo;
    }

//    @Override
//    public CourseBaseInfoDto queryCourseBaseById(Long id) {
//
//    }

    //if it exists -> update, or insert
     public int saveCourseMarket(CourseMarket courseMarket){
         Long id = courseMarket.getId();
         CourseMarket courseMarketInfo = courseMarketMapper.selectById(id);
         int result = 0;
         if(courseMarketInfo == null){
            //insert data
             result = courseMarketMapper.insert(courseMarket);
         }else{
            //update data
             BeanUtils.copyProperties(courseMarket,courseMarketInfo);
             courseMarketInfo.setId(id);
             result = courseMarketMapper.updateById(courseMarketInfo);
         }
         return result;
     }

     //Get course info
     @Override
     public CourseBaseInfoDto getCourseBaseInfoById(Long courseId){
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase == null){
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if(courseMarket == null ){
            return null;
        }
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }

        //query category info
        CourseCategory courseCategory = courseCategoryMapper.selectById(courseBase.getSt());
        CourseCategory courseCategory1 = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setSt(courseCategory.getName()); //small category name
        courseBaseInfoDto.setMtName(courseCategory1.getName());//Big category name
        return courseBaseInfoDto;
    }

    @Override
    public CourseBaseInfoDto updateCourseInfo(Long companyId, EditCourseDto editCourseDto) {
        //validation :  This institution can only modify the courses of this institution
        Long id = editCourseDto.getId(); //Course Id
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfoById(id);
        if(courseBaseInfo == null){
            StudyOnlineException.cast("Course Information does not exist");
        }
        // This institution can only modify the courses of this institution
        if(!companyId.equals(courseBaseInfo.getCompanyId())){
            StudyOnlineException.cast("Only courses of this institution can be modified");
        }
        //Encapsulated data
        BeanUtils.copyProperties(editCourseDto,courseBaseInfo);
        courseBaseInfo.setChangeDate(LocalDateTime.now());
        //Update Course Base Info
        int isSuccess = courseBaseMapper.updateById(courseBaseInfo);
        if(isSuccess < 1){
            StudyOnlineException.cast("Edit Course Information failed");
        }
        //Update Course Market Info
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(editCourseDto,courseMarket);
        courseMarket.setId(id);
        int result = saveCourseMarket(courseMarket);
        if(result < 1){
            StudyOnlineException.cast("Edit Course Information failed");
        }
        CourseBaseInfoDto courseBaseInfoById = getCourseBaseInfoById(id);
        return courseBaseInfoById;
    }
}
