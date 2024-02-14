package org.studyonline.content.service;

import org.studyonline.content.model.dto.BindTeachplanMediaDto;
import org.studyonline.content.model.dto.SaveTeachplanDto;
import org.studyonline.content.model.dto.TeachplanDto;
import org.studyonline.content.model.po.TeachplanMedia;

import java.util.List;

/**
 * @description Course Basic Information Management Business Interface
 * @author  Chengguang Li
 * @date  23/01/2023
 * @version 1.0
 */
public interface TeachplanService {
    /**
     * @description Query the course plan tree structure
     * @param courseId  Course Id
     * @return List<TeachplanDto>
     * @author  Chengguang Li
     * @date 23/01/2023
     */
    public List<TeachplanDto> findTeachplanTree(long courseId);

    /**
     * @description save/update course information
     * @param teachplanDto  Course Plan information
     * @return void
     * @author Chengguang Li
     * @date  29/01/2024
     */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);


    /**
     * @description Curriculum plan and media resource information binding
     * @param bindTeachplanMediaDto
     * @return org.studyonline.content.model.po.TeachplanMedia
     * @author Chengguang Li
     * @date 14/02/2024 3:51 pm
     */
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);
}
