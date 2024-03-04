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
 * @Date: 03/01/2024 2:25 pm
 */
@Data
@ToString
@TableName("xc_pay_record")
public class PayRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Payment record number
     */
    private Long id;

    /**
     * Payment transaction number of this system
     */
    private Long payNo;

    /**
     * Third-party payment transaction serial number
     */
    private String outPayNo;

    /**
     * Third-party payment channel number
     */
    private String outPayChannel;

    /**
     * Product order number
     */
    private Long orderId;

    /**
     * Order name
     */
    private String orderName;
    /**
     * Total order price in yuan
     */
    private Float totalPrice;

    /**
     * CurrencyCNY
     */
    private String currency;

    /**
     * creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * Payment status
     */
    private String status;

    /**
     * Payment success time
     */
    private LocalDateTime paySuccessTime;

    /**
     * user id
     */
    private String userId;



}

