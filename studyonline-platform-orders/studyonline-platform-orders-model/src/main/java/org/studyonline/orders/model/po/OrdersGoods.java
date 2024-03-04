package org.studyonline.orders.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 03/01/2024 2:25 pm
 */
@Data
@ToString
@TableName("xc_orders_goods")
public class OrdersGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * order number
     */
    private Long orderId;

    /**
     * Product id
     */
    private String goodsId;

    /**
     * Product Types
     */
    private String goodsType;

    /**
     * product name
     */
    private String goodsName;

    /**
     * Commodity transaction price, unit cent
     */
    private Float goodsPrice;

    /**
     * Product details json, can be empty
     */
    private String goodsDetail;


}
