package org.studyonline.orders.model.dto;

import lombok.Data;
import lombok.ToString;
import org.studyonline.orders.model.po.PayRecord;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 01/03/2024 2:26 pm
 */
@Data
@ToString
public class PayRecordDto extends PayRecord {

    //QR code
    private String qrcode;

}
