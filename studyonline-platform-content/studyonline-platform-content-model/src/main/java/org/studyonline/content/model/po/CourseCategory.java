package org.studyonline.content.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>
 *  Course Category
 * </p>
 *
 * @author Chengguang Li
 */
@Data
@TableName("course_category")
public class CourseCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    private String id;

    /**
     * category name
     */
    private String name;

    /**
     * The category label defaults to the same as the name
     */
    private String label;

    /**
     *  parent id（The parent node of the first level is 0, self-related field id）
     */
    private String parentid;

    /**
     * visible or not
     */
    private Integer isShow;

    /**
     * sort field
     */
    private Integer orderby;

    /**
     * is leaf or not
     */
    private Integer isLeaf;
}
