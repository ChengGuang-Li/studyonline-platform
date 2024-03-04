package org.studyonline.orders.service;

import org.studyonline.messagesdk.model.po.MqMessage;
import org.studyonline.orders.model.dto.AddOrderDto;
import org.studyonline.orders.model.dto.PayRecordDto;
import org.studyonline.orders.model.dto.PayStatusDto;
import org.studyonline.orders.model.po.PayRecord;

/**
 * @Description: Order-related service interface
 * @Author: Chengguang Li
 * @Date: 03/01/02024 2:43 pm
 */
public interface OrderService {

    /**
     * @description  Create product order
     * @param addOrderDto Orders Information
     * @return PayRecordDto  Payment records (including QR code)
     * @author Chengguang Li
     * @date 03/01/02024 2:43 pm
     */
    public PayRecordDto createOrder(String userId, AddOrderDto addOrderDto);

    /**
     * @description Check payment records
     * @param payNo  Transaction record number
     * @return org.studyonline.orders.model.po.PayRecord
     * @author Chengguang Li
     * @date 03/01/02024 2:43 pm
     */
    public PayRecord getPayRecordByPayno(String payNo);

    /**
     * Request Alipay to check payment results
     * @param payNo  Payment record id
     * @return Payment record information
     * @author Chengguang Li
     * @date 03/01/02024 2:43 pm
     */
    public PayRecordDto queryPayResult(String payNo);

    /**
     *  Save payment status
     * @param payStatusDto
     * @author Chengguang Li
     * @date 03/01/02024 2:43 pm
     */
    public void saveAliPayStatus(PayStatusDto payStatusDto);

    /**
     * Send notification results
     * @param message
     * @author Chengguang Li
     * @date 03/01/02024 2:43 pm
     */
    public void notifyPayResult(MqMessage message);

}
