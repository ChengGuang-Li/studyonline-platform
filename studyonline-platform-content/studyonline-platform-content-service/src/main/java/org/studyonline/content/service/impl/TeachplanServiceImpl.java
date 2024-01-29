package org.studyonline.content.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.studyonline.content.mapper.TeachplanMapper;
import org.studyonline.content.model.dto.SaveTeachplanDto;
import org.studyonline.content.model.dto.TeachplanDto;
import org.studyonline.content.model.po.Teachplan;
import org.studyonline.content.service.TeachplanService;

import java.util.HashMap;
import java.util.List;

@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {
        //Determine whether to add or modify by course plan ID
        Long id = teachplanDto.getId();//course plan Id
        if(id == null){
            //insert
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(teachplanDto,teachplan);
            //determine the Order field
            HashMap<String,Long> map = new HashMap<String,Long>();
            map.put("courseId",teachplanDto.getCourseId());
            map.put("parentId",teachplanDto.getParentid());
            int countNum = teachplanMapper.countByCourseIdAndParentidLong(map);
            teachplan.setOrderby(countNum+1);
            int insert = teachplanMapper.insert(teachplan);
        }else{
            //update
            Teachplan teachplan = teachplanMapper.selectById(teachplanDto.getId());
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }
    }
}
