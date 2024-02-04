package org.studyonline.media.model.po;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("media_process_history")
public class MediaProcessHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * File ID
     */
    private String fileId;

    /**
     * File Name
     */
    private String filename;

    /**
     * storage source
     */
    private String bucket;

    private String filePath;

    /**
     * Status, 1: Not processed, updated to 2 after video processing is completed
     */
    private String status;

    /**
     * Upload time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * Complete time
     */
    private LocalDateTime finishDate;

    /**
     * Media asset file access address
     */
    private String url;
    /**
     * Reason for failure
     */
    private String errormsg;

    /**
     * number of failures
     */
    private int failCount;
}
