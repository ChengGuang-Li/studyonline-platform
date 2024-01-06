package org.studyonline.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.base.model.PageParams;
import org.studyonline.base.model.PageResult;
import org.studyonline.content.mapper.CourseBaseMapper;
import org.studyonline.content.model.dto.QueryCourseParamsDto;
import org.studyonline.content.model.po.CourseBase;
import org.studyonline.content.service.CourseBaseInfoService;

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
}
