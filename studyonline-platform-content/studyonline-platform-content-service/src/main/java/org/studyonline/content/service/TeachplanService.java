package org.studyonline.content.service;

import org.studyonline.content.model.dto.SaveTeachplanDto;
import org.studyonline.content.model.dto.TeachplanDto;

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


}
