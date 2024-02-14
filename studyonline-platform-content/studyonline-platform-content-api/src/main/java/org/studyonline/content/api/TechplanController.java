package org.studyonline.content.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.studyonline.content.model.dto.BindTeachplanMediaDto;
import org.studyonline.content.model.dto.SaveTeachplanDto;
import org.studyonline.content.model.dto.TeachplanDto;
import org.studyonline.content.service.TeachplanService;

import java.util.List;

/**
 * @description Course plan interface
 * @author ChengGuang
 * @date 2023/12/29 22:30
 * @version 1.0
 */
@RestController
@Slf4j
@Api(value = "Course plan interface",tags = "Course plan interface")
public class TechplanController {

     @Autowired
     TeachplanService teachplanService;

     @GetMapping("/teachplan/{courseId}/tree-nodes")
     @ApiImplicitParam(value = "courseId",name = "course id",required = true,dataType = "Long",paramType = "path")
     @ApiOperation("Query the course plan tree structure")
     public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplanTree(courseId);
     }

     @PostMapping("/teachplan")
     @ApiOperation("Lesson plan creation or modification")
     public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){
          teachplanService.saveTeachplan(teachplan);
     }


     @ApiOperation(value = "Curriculum plan and media resource information binding")
     @PostMapping("/teachplan/association/media")
     public void associationMedia(@RequestBody BindTeachplanMediaDto bindTeachplanMediaDto){
          teachplanService.associationMedia(bindTeachplanMediaDto);
     }



}
