package org.studyonline.content.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *      Course Plan Media Info
 * </p>
 *
 * @author Chengguang Li
 */
@Data
@TableName("teachplan_media")
public class TeachplanMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  Primary Key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Media File Id
     */
    private String mediaId;

    /**
     * Course Plan Identification
     */
    private Long teachplanId;

    /**
     * Course ID
     */
    private Long courseId;

    /**
     * Original name of media asset file
     */
    @TableField("media_fileName")
    private String mediaFilename;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * Create User
     */
    private String createPeople;

    /**
     * Modify User
     */
    private String changePeople;


}