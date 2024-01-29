package org.studyonline.content.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 *      Technology Course Plan
 * </p>
 *
 * @author Chengguang Li
 */
@Data
@TableName("teachplan")
public class Teachplan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     *  Course Plan Id
     */
    private String pname;

    /**
     *  Course Plan Parent Id
     */
    private Long parentid;

    /**
     *  Level  1, 2, 3
     */
    private Integer grade;

    /**
     * Course Category:1 Video、2 Document
     */
    private String mediaType;

    /**
     * Start live broadcast time
     */
    private LocalDateTime startTime;

    /**
     *  Live end time
     */
    private LocalDateTime endTime;

    /**
     * Chapter and course introduction
     */
    private String description;

    /**
     * Duration in hours:minutes:seconds
     */
    private String timelength;

    /**
     * sort field
     */
    private Integer orderby;

    /**
     * Course ID
     */
    private Long courseId;

    /**
     * Course release ID
     */
    private Long coursePubId;

    /**
     * Status (1 normal 0 deleted)
     */
    private Integer status;

    /**
     * Whether to support trial learning or preview (preview)
     */
    private String isPreview;

    /**
     * Create Time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     *  Modify Time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changeDate;


}