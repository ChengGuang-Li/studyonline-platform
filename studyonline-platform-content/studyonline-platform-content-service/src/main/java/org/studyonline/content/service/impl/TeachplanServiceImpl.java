package org.studyonline.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.content.mapper.TeachplanMapper;
import org.studyonline.content.mapper.TeachplanMediaMapper;
import org.studyonline.content.model.dto.BindTeachplanMediaDto;
import org.studyonline.content.model.dto.SaveTeachplanDto;
import org.studyonline.content.model.dto.TeachplanDto;
import org.studyonline.content.model.po.Teachplan;
import org.studyonline.content.model.po.TeachplanMedia;
import org.studyonline.content.service.TeachplanService;

import java.util.HashMap;
import java.util.List;

@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;

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

    @Override
    @Transactional
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {
        //Course Plan id
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan==null){
            StudyOnlineException.cast("Teaching plan does not exist");
        }
        Integer grade = teachplan.getGrade();
        if(grade!=2){
            StudyOnlineException.cast("Only second-level teaching plans are allowed to bind media assets files");
        }
        //Delete tht original data by courseId

        int delete = teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>().eq(TeachplanMedia::getTeachplanId, bindTeachplanMediaDto.getTeachplanId()));

        //insert the new data
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        BeanUtils.copyProperties(bindTeachplanMediaDto,teachplanMedia);
        teachplanMedia.setCourseId(teachplan.getCourseId());
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMediaMapper.insert(teachplanMedia);
        return teachplanMedia;
    }
}
