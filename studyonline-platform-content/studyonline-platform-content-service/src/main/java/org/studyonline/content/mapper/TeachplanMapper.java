package org.studyonline.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.studyonline.content.model.dto.TeachplanDto;
import org.studyonline.content.model.po.Teachplan;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Course Plan Mapper Interface
 * </p>
 *
 * @author Chengguang Li
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    /**
     * @description Query the course plan of a course and form a tree structure
     * @param courseId
     * @return org.studyonline.content.model.dto.TeachplanDto
     * @author  Chengguagn Li
     * @date  23/01/2024
     */
    public List<TeachplanDto> selectTreeNodes(long courseId);

    public int countByCourseIdAndParentidLong(Map map);
}
