package org.studyonline.orders.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 03/01/2024 2:24 pm
 */
@Data
@ToString
@TableName("xc_orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * order number
     */
    private Long id;

    /**
     * total price
     */
    private Float totalPrice;

    /**
     * creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * trading status
     */
    private String status;

    /**
     * user id
     */
    private String userId;


    /**
     * Order Type
     */
    private String orderType;

    /**
     * Order name
     */
    private String orderName;

    /**
     * Order Description
     */
    private String orderDescrip;

    /**
     * Order details json
     */
    private String orderDetail;

    /**
     * External system business id
     */
    private String outBusinessId;


}

