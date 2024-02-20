package org.studyonline.ucenter.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 2:03 pm
 */
@Data
@TableName("xc_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * Menu encoding
     */
    private String code;

    /**
     * Parent menu ID
     */
    private String pId;

    /**
     * name
     */
    private String menuName;

    /**
     * Request address
     */
    private String url;

    /**
     * Is it a menu
     */
    private String isMenu;

    /**
     * menu hierarchy
     */
    private Integer level;

    /**
     * Menu sorting
     */
    private Integer sort;

    private String status;

    private String icon;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
