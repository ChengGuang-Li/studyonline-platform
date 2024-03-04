package org.studyonline.orders.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.base.utils.IdWorkerUtils;
import org.studyonline.base.utils.QRCodeUtil;
import org.studyonline.messagesdk.model.po.MqMessage;
import org.studyonline.messagesdk.service.MqMessageService;
import org.studyonline.orders.config.AlipayConfig;
import org.studyonline.orders.config.PayNotifyConfig;
import org.studyonline.orders.mapper.OrdersGoodsMapper;
import org.studyonline.orders.mapper.OrdersMapper;
import org.studyonline.orders.mapper.PayRecordMapper;
import org.studyonline.orders.model.dto.AddOrderDto;
import org.studyonline.orders.model.dto.PayRecordDto;
import org.studyonline.orders.model.dto.PayStatusDto;
import org.studyonline.orders.model.po.Orders;
import org.studyonline.orders.model.po.OrdersGoods;
import org.studyonline.orders.model.po.PayRecord;
import org.studyonline.orders.service.OrderService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 01/03/2024 2:44 pm
 */

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    OrdersGoodsMapper ordersGoodsMapper;

    @Autowired
    MqMessageService mqMessageService;

    @Autowired
    PayRecordMapper payRecordMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OrderServiceImpl currentProxy;

    @Value("${pay.qrcodeurl}")
    String qrcodeurl;

    @Value("${pay.alipay.APP_ID}")
    String APP_ID;
    @Value("${pay.alipay.APP_PRIVATE_KEY}")
    String APP_PRIVATE_KEY;

    @Value("${pay.alipay.ALIPAY_PUBLIC_KEY}")
    String ALIPAY_PUBLIC_KEY;

    @Transactional
    @Override
    public PayRecordDto createOrder(String userId, AddOrderDto addOrderDto) {

        //Insert order table, order master table, order details table
        Orders orders = saveXcOrders(userId, addOrderDto);

        //Insert payment record
        PayRecord payRecord = createPayRecord(orders);
        Long payNo = payRecord.getPayNo();

        //Generate QR code
        QRCodeUtil qrCodeUtil = new QRCodeUtil();
        //Payment QR code url
        String url = String.format(qrcodeurl, payNo);
        //QR code image
        String qrCode = null;
        try {
            qrCode = qrCodeUtil.createQRCode(url, 200, 200);
        } catch (IOException e) {
            StudyOnlineException.cast("Error generating QR code");
        }

        PayRecordDto payRecordDto = new PayRecordDto();
        BeanUtils.copyProperties(payRecord,payRecordDto);
        payRecordDto.setQrcode(qrCode);

        return payRecordDto;
    }

    @Override
    public PayRecord getPayRecordByPayno(String payNo) {
        PayRecord payRecord = payRecordMapper.selectOne(new LambdaQueryWrapper<PayRecord>().eq(PayRecord::getPayNo, payNo));
        return payRecord;
    }

    @Override
    public PayRecordDto queryPayResult(String payNo) {

        //Call Alipay’s interface to query payment results
        PayStatusDto payStatusDto = queryPayResultFromAlipay(payNo);

        //Get the payment results and update the payment status of the payment record table and order table.
        currentProxy.saveAliPayStatus(payStatusDto);
        //To return the latest payment record information
        PayRecord payRecordByPayno = getPayRecordByPayno(payNo);
        PayRecordDto payRecordDto = new PayRecordDto();
        BeanUtils.copyProperties(payRecordByPayno,payRecordDto);

        return payRecordDto;
    }

    /**
     * Request Alipay to check payment results
     * @param payNo Payment transaction number
     * @return payment result
     */
    public PayStatusDto queryPayResultFromAlipay(String payNo){

        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, APP_ID, APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", payNo);
        //bizContent.put("trade_no", "2014112611001004680073956707");
        request.setBizContent(bizContent.toString());
        //Information returned by Alipay
        String body = null;
        try {
            AlipayTradeQueryResponse response = alipayClient.execute ( request ); //Call the API through alipayClient to obtain the corresponding response class
            if(!response.isSuccess()){//Transaction unsuccessful
                StudyOnlineException.cast("Requesting Alipay to check payment results failed");
            }
            body = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            StudyOnlineException.cast("Request payment query payment result is abnormal");
        }
        Map bodyMap = JSON.parseObject(body, Map.class);
        Map alipay_trade_query_response = (Map) bodyMap.get("alipay_trade_query_response");

        //Parse payment results
        String trade_no = (String) alipay_trade_query_response.get("trade_no");
        String trade_status = (String) alipay_trade_query_response.get("trade_status");
        String total_amount = (String) alipay_trade_query_response.get("total_amount");
        PayStatusDto payStatusDto = new PayStatusDto();
        payStatusDto.setOut_trade_no(payNo);
        payStatusDto.setTrade_no(trade_no);//Alipay transaction number
        payStatusDto.setTrade_status(trade_status);//Trading status
        payStatusDto.setApp_id(APP_ID);
        payStatusDto.setTotal_amount(total_amount);//Total Price


        return payStatusDto;
    }

    /**
     * @description Save Alipay payment results
     * @param payStatusDto  Payment result information Information retrieved from Alipay
     * @return void
     * @author Chengguang Li
     * @date 03/04/2024 16:52
     */
    @Transactional
    public void saveAliPayStatus(PayStatusDto payStatusDto){
        //Payment record number
        String payNO = payStatusDto.getOut_trade_no();
        PayRecord payRecordByPayno = getPayRecordByPayno(payNO);
        if(payRecordByPayno == null){
            StudyOnlineException.cast("No relevant payment record found");
        }
        //Get the associated order id
        Long orderId = payRecordByPayno.getOrderId();
        Orders orders = ordersMapper.selectById(orderId);
        if(orders == null){
            StudyOnlineException.cast("No associated order found");
        }
        //Payment status
        String statusFromDb = payRecordByPayno.getStatus();
        //If the database payment status is already successful, it will no longer be processed.
        if("601002".equals(statusFromDb)){
            return ;
        }

        //If the payment is successful
        String trade_status = payStatusDto.getTrade_status();//Payment results queried from Alipay
        if(trade_status.equals("TRADE_SUCCESS")){//The information returned by Alipay is that the payment was successful.
            //Update the status of the payment record table to payment successful
            payRecordByPayno.setStatus("601002");
            //Alipay order number
            payRecordByPayno.setOutPayNo(payStatusDto.getTrade_no());
            //Third-party payment channel number
            payRecordByPayno.setOutPayChannel("Alipay");
            //Payment success time
            payRecordByPayno.setPaySuccessTime(LocalDateTime.now());
            payRecordMapper.updateById(payRecordByPayno);

            //Update the status of the order table to payment successful
            orders.setStatus("600002");//The order status is transaction successful
            ordersMapper.updateById(orders);

            //Write message to database
            MqMessage mqMessage = mqMessageService.addMessage("payresult_notify", orders.getOutBusinessId(), orders.getOrderType(), null);
            //Send a message
            notifyPayResult(mqMessage);

        }




    }



    @Override
    public void notifyPayResult(MqMessage message) {

        //Message Content
        String jsonString = JSON.toJSONString(message);
        //Create a persistent message
        Message messageObj = MessageBuilder.withBody(jsonString.getBytes(StandardCharsets.UTF_8)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();

        //Message Id
        Long id = message.getId();

        //global message id
        CorrelationData correlationData = new CorrelationData(id.toString());

        //Use correlationData to specify the callback method
        correlationData.getFuture().addCallback(result->{
            if(result.isAck()){
                //The message was successfully sent to the switch
                log.debug("Message sent successfully:{}",jsonString);
                //Delete the message from the database table mq_message
                mqMessageService.completed(id);

            }else{
                //Message sending failed
                log.debug("Failed to send message:{}",jsonString);
            }

        },ex->{
            //An exception occurred
            log.error("Exception when sending message:{}",jsonString);
        });
        //Send Message
        rabbitTemplate.convertAndSend(PayNotifyConfig.PAYNOTIFY_EXCHANGE_FANOUT,"",messageObj,correlationData);
    }

    /**
     * Save payment history
     * @param orders
     * @return
     */
    public PayRecord createPayRecord(Orders orders){
        // Order Id
        Long orderId = orders.getId();
        Orders xcOrders = ordersMapper.selectById(orderId);
        // If this order does not exist, payment records cannot be added.
        if(xcOrders == null){
            StudyOnlineException.cast("Order does not exist");
        }
        //Order Status
        String status = xcOrders.getStatus();
        //If the payment result of this order is successful, no payment record will be added to avoid repeated payments.
        if("601002".equals(status)){//payment successfully
            StudyOnlineException.cast("This order has been paid");
        }
        //Add payment record
        PayRecord payRecord = new PayRecord();
        payRecord.setPayNo(IdWorkerUtils.getInstance().nextId());//The payment record number will be passed to Alipay in the future.
        payRecord.setOrderId(orderId);
        payRecord.setOrderName(xcOrders.getOrderName());
        payRecord.setTotalPrice(xcOrders.getTotalPrice());
        payRecord.setCurrency("CNY");
        payRecord.setCreateDate(LocalDateTime.now());
        payRecord.setStatus("601001");//unpaid
        payRecord.setUserId(xcOrders.getUserId());
        int insert = payRecordMapper.insert(payRecord);
        if(insert<=0){
            StudyOnlineException.cast(""); //Failed to insert payment record
        }
        return payRecord;
    }

    /**
     * Save order information
     * @param userId
     * @param addOrderDto
     * @return
     */
    public Orders saveXcOrders(String userId, AddOrderDto addOrderDto){
        //Insert order table, order master table, order details table
        //Make an idempotent judgment. There can only be one order for the same course selection record.
        Orders orders = getOrderByBusinessId(addOrderDto.getOutBusinessId());
        if(orders !=null){
            return orders;
        }

        //Insert into order main table
        orders = new Orders();
        orders.setId(IdWorkerUtils.getInstance().nextId());//Use snowflake algorithm to generate order number
        orders.setTotalPrice(addOrderDto.getTotalPrice());
        orders.setCreateDate(LocalDateTime.now());
        orders.setStatus("600001");//Pending Payment
        orders.setUserId(userId);
        orders.setOrderType("60201");//Order Type
        orders.setOrderName(addOrderDto.getOrderName());
        orders.setOrderDescrip(addOrderDto.getOrderDescrip());
        orders.setOrderDetail(addOrderDto.getOrderDetail());
        orders.setOutBusinessId(addOrderDto.getOutBusinessId());//If it is course selection, record the ID of the course selection form here.
        int insert = ordersMapper.insert(orders);
        if(insert<=0){
            StudyOnlineException.cast("Failed to add order");
        }
        //Order Id
        Long orderId = orders.getId();
        //Insert order details table
        //Convert the detailed json string passed in by the front end into a List
        String orderDetailJson = addOrderDto.getOrderDetail();
        List<OrdersGoods> ordersGoods = JSON.parseArray(orderDetailJson, OrdersGoods.class);
        //Traverse ordersGoods and insert order details table
        ordersGoods.forEach(goods->{

            goods.setOrderId(orderId);
            //Insert order details table
            int insert1 = ordersGoodsMapper.insert(goods);

        });
        return orders;

    }


    /**
     * Query orders based on business id. Business id is the primary key in the course selection record table.
     *
     * @param businessId
     * @return
     */
    public Orders getOrderByBusinessId(String businessId) {
        Orders orders = ordersMapper.selectOne(new LambdaQueryWrapper<Orders>().eq(Orders::getOutBusinessId, businessId));
        return orders;
    }

}
