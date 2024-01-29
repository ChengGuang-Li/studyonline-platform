package org.studyonline.content.model.dto;


import lombok.Data;
import lombok.ToString;

/**
 * @author: Chengguagn Li
 * @Date:   29/01/2024
 */
@Data
@ToString
public class SaveTeachplanDto {

    /***
     * teaching plan id
     */
    private Long id;

    /**
     * Course plan name
     */
    private String pname;

    /**
     *  lesson plan parent Id
     */
    private Long parentid;

    /**
     * Levels, divided into levels 1, 2, and 3
     */
    private Integer grade;

    /**
     * Course type: 1 video, 2 documents
     */
    private String mediaType;


    /**
     * Course ID
     */
    private Long courseId;

    /**
     * Course release ID
     */
    private Long coursePubId;


    /**
     * Whether to support trial learning or preview (preview)
     */
    private String isPreview;

}
