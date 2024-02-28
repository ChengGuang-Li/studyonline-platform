package org.studyonline.content.service.impl;

import com.alibaba.fastjson.JSON;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.content.config.MultipartSupportConfig;
import org.studyonline.content.feignclient.MediaServiceClient;
import org.studyonline.content.mapper.CourseBaseMapper;
import org.studyonline.content.mapper.CourseMarketMapper;
import org.studyonline.content.mapper.CoursePublishMapper;
import org.studyonline.content.mapper.CoursePublishPreMapper;
import org.studyonline.content.model.dto.CourseBaseInfoDto;
import org.studyonline.content.model.dto.CoursePreviewDto;
import org.studyonline.content.model.dto.TeachplanDto;
import org.studyonline.content.model.po.CourseBase;
import org.studyonline.content.model.po.CourseMarket;
import org.studyonline.content.model.po.CoursePublish;
import org.studyonline.content.model.po.CoursePublishPre;
import org.studyonline.content.service.CourseBaseInfoService;
import org.studyonline.content.service.CoursePublishService;
import org.studyonline.content.service.TeachplanService;
import org.studyonline.messagesdk.model.po.MqMessage;
import org.studyonline.messagesdk.service.MqMessageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 14/02/2024 10:03 pm
 */
@Service
@Slf4j
public class CoursePublishServiceImpl implements CoursePublishService {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    TeachplanService teachplanService;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CoursePublishPreMapper coursePublishPreMapper;

    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CoursePublishMapper coursePublishMapper;

    @Autowired
    MqMessageService mqMessageService;

    @Autowired
    MediaServiceClient mediaServiceClient;

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

    @Transactional
    @Override
    public void commitAudit(Long companyId, Long courseId) {
        //If the review status of a course is Submitted, repeated submissions are not allowed
        CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfoById(courseId);
        if(courseBaseInfo == null){
            StudyOnlineException.cast("Cannot find this Course");
        }
        String status = courseBaseInfo.getStatus();
        if("202003".equals(status)){
            StudyOnlineException.cast("The course has been submitted, please wait for review");
        }
        //The course information is incomplete and submission is not allowed
        String pic = courseBaseInfo.getPic();
        String name = courseBaseInfo.getName();
        if(StringUtils.isBlank(pic) || StringUtils.isBlank(name)){
            StudyOnlineException.cast("Course information is incomplete");
        }
        //Search course basic info, course marketing info, and course plan info
        // insert these info into course_publish_pre table
        CoursePublishPre coursePublishPre = new CoursePublishPre();
        BeanUtils.copyProperties(courseBaseInfo,coursePublishPre);
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        String jsonStringJson = JSON.toJSONString(courseMarket);

        coursePublishPre.setMarket(jsonStringJson);
        List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);
        String teachPlanJson = JSON.toJSONString(teachplanTree);
        coursePublishPre.setTeachplan(teachPlanJson);
        coursePublishPre.setStatus("202003");
        //Submit Time
        coursePublishPre.setCreateDate(LocalDateTime.now());
        coursePublishPre.setCompanyId(companyId);
        //Update course status -- submitted
        //If it exist, Update, Or Insert
        CoursePublishPre coursePublishPre1 = coursePublishPreMapper.selectById(courseId);
        if(coursePublishPre1 == null){
            coursePublishPreMapper.insert(coursePublishPre);
        }else{
            coursePublishPreMapper.updateById(coursePublishPre);
        }

        //Update the course info in the Course basic Info
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        courseBase.setAuditStatus("202003"); //update status
        courseBaseMapper.updateById(courseBase);
    }

    @Transactional
    @Override
    public void publish(Long companyId, Long courseId) {
        CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
        if(coursePublishPre == null){
            StudyOnlineException.cast("Course information is empty");
        }
        //status
        String status = coursePublishPre.getStatus();
        if(!"202004".equals(status)){
            StudyOnlineException.cast("The course has not been approved and is not allowed to be published.");
        }
        //Insert data to the course_publish_pre table
        CoursePublish coursePublish = new CoursePublish();
        BeanUtils.copyProperties(coursePublishPre,coursePublish);
        CoursePublish coursePublish1 = coursePublishMapper.selectById(courseId);
        if(coursePublish1 == null){
            int insert = coursePublishMapper.insert(coursePublish);
        }else{
            int i = coursePublishMapper.updateById(coursePublish1);
        }
        //Insert data to message table
        MqMessage coursePublish2 = mqMessageService.addMessage("course_publish", String.valueOf(courseId), null, null);
        if(coursePublish2 == null){
            StudyOnlineException.cast("Failed to insert course publishing data into message table");
        }
        //Update the status in the Course_base table  to published
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        courseBase.setStatus("203002");
        courseBaseMapper.updateById(courseBase);

        //Delete the data in course_publish_pre table
        int i = coursePublishPreMapper.deleteById(courseId);


    }

    @Override
    public File generateCourseHtml(Long courseId) {
        //Static files
        File htmlFile  = null;

        try {
            //Configure freemarker
            Configuration configuration = new Configuration(Configuration.getVersion());

            //Load template
            //Select the specified template path, under templates under classpath
            //String classpath = this.getClass().getResource("/").getPath();
            // configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates/"));
            configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass().getClassLoader(),"/templates"));

            //Set character encoding
            configuration.setDefaultEncoding("utf-8");

            //Specify template file name
            Template template = configuration.getTemplate("course_template.ftl");

            //Prepare data
            CoursePreviewDto coursePreviewInfo = this.getCoursePreviewInfo(courseId);

            Map<String, Object> map = new HashMap<>();
            map.put("model", coursePreviewInfo);

            //Statics
            //Parameter 1: template, parameter 2: data model
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            //Output static content to a file
            InputStream inputStream = IOUtils.toInputStream(content);
            //Create static files
            htmlFile = File.createTempFile("course",".html");
            log.debug("Make the course static and generate static files:{}",htmlFile.getAbsolutePath());
            //output stream
            FileOutputStream outputStream = new FileOutputStream(htmlFile);
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            log.error("Course static exception:{}",e.toString());
            StudyOnlineException.cast("Course static exception");
        }
        return htmlFile;
    }

    @Override
    public void uploadCourseHtml(Long courseId, File file) {
       try{
           MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(file);
           String course = mediaServiceClient.upload(multipartFile, "course/"+courseId+".html");
           if(course==null){
               log.error("Upload File is empty");
               StudyOnlineException.cast("Failed to upload static files");
           }
       }catch (Exception e){
           log.error("Upload static pages failed: {}",e.getMessage());
           StudyOnlineException.cast("Exception when uploading static files");
       }

    }

    @Override
    public CoursePublish getCoursePublish(Long courseId) {
        CoursePublish coursePublish = coursePublishMapper.selectById(courseId);
        return coursePublish ;
    }
}
