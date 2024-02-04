package org.studyonline.media.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("media_files")
public class MediaFiles implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * Institution ID
     */
    private Long companyId;

    /**
     * Institution Name
     */
    private String companyName;

    /**
     * File name
     */
    private String filename;

    /**
     * File type (document, audio, video)
     */
    private String fileType;

    /**
     * tag
     */
    private String tags;

    /**
     * storage directory
     */
    private String bucket;

    /**
     * Storage path
     */
    private String filePath;


    /**
     * File ID
     */
    private String fileId;

    /**
     * Media asset file access address
     */
    private String url;


    /**
     * Uploader
     */
    private String username;

    /**
     * Upload time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * Modify time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changeDate;

    /**
     * Status, 1: Not processed, updated to 2 after video processing is completed
     */
    private String status;

    /**
     * Remark
     */
    private String remark;

    /**
     * Approval Status
     */
    private String auditStatus;

    /**
     * Audit opinion
     */
    private String auditMind;

    /**
     * File size
     */
    private Long fileSize;
}
