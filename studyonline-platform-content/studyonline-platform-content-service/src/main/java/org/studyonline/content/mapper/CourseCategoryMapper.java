package org.studyonline.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.studyonline.content.model.dto.CourseCategoryTreeDto;
import org.studyonline.content.model.po.CourseCategory;

import java.util.List;

/**
 * <p>
 *     Course Classification Mapper Interface
 * </p>
 *
 * @author Chengguang Li
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

     //Course classification tree structure query
     public List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
