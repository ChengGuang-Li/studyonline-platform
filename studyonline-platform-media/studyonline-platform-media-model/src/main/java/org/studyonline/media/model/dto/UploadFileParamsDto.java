package org.studyonline.media.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UploadFileParamsDto {
    /**
     * File Name
     */
    private String filename;


    /**
     * File type (document, audio, video)
     */
    private String fileType;
    /**
     * File size
     */
    private Long fileSize;

    /**
     * Label
     */
    private String tags;

    /**
     * Uploader
     */
    private String username;

    /**
     * Remark
     */
    private String remark;

}
