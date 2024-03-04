package org.studyonline.orders.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 03/01/2024 2:26 pm
 */
@Data
@ToString
public class AddOrderDto  {

    /**
     * total price
     */
    private Float totalPrice;

    /**
     * Order Type
     */
    private String orderType;

    /**
     * Order name
     */
    private String orderName;
    /**
     * Order description
     */
    private String orderDescrip;

    /**
     * Order details json, cannot be empty
     * [{"goodsId":"","goodsType":"","goodsName":"","goodsPrice":"","goodsDetail":""},{...}]
     */
    private String orderDetail;

    /**
     * External system business id
     */
    private String outBusinessId;

}
