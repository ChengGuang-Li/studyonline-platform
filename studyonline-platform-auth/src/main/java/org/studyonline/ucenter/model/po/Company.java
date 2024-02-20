package org.studyonline.ucenter.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 1:45 pm
 */
@Data
@TableName("xc_company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * Contact name
     */
    private String linkname;

    /**
     * name
     */
    private String name;

    private String mobile;

    private String email;

    /**
     * Introduction
     */
    private String intro;

    /**
     * logo
     */
    private String logo;

    /**
     * ID photo
     */
    private String identitypic;

    /**
     * Tool nature
     */
    private String worktype;

    /**
     * business license
     */
    private String businesspic;

    /**
     * Enterprise status
     */
    private String status;


}
