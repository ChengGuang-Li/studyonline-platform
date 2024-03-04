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
public class PayStatusDto {

    //Merchant order number
    String out_trade_no;
    //Alipay transaction number
    String trade_no;
    //Trading status
    String trade_status;
    //appid
    String app_id;
    //total_amount
    String total_amount;
}
