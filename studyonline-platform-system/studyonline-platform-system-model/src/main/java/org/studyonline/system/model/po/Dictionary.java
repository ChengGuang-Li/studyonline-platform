package org.studyonline.system.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *  Dictionary Entity
 *
 * @author : Chengguang Li
 * @date : 06/01/2024
 */
@Data
@TableName("dictionary")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id identification
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Data dictionary name
     */
    private String name;

    /**
     * data dictionary code
     */
    private String code;

    /**
     * Data dictionary item--json format
     [{
     "sd_name": "低级",
     "sd_id": "200001",
     "sd_status": "1"
     }, {
     "sd_name": "中级",
     "sd_id": "200002",
     "sd_status": "1"
     }, {
     "sd_name": "高级",
     "sd_id": "200003",
     "sd_status": "1"
     }]
     */
    private String itemValues;
}
