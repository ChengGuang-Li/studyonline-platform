package org.studyonline.ucenter.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 2:03 pm
 */
@Data
@TableName("xc_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * user id
     */
    private String userId;

    /**
     * name
     */
    private String name;

    /**
     * introduction
     */
    private String intro;

    /**
     * resume
     */
    private String resume;

    /**
     * pictures
     */
    private String pic;


}
