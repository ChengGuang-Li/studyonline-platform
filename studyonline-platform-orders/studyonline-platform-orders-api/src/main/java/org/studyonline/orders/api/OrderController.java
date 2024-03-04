package org.studyonline.orders.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studyonline.base.exception.StudyOnlineException;
import org.studyonline.orders.config.AlipayConfig;
import org.studyonline.orders.model.dto.AddOrderDto;
import org.studyonline.orders.model.dto.PayRecordDto;
import org.studyonline.orders.model.dto.PayStatusDto;
import org.studyonline.orders.model.po.PayRecord;
import org.studyonline.orders.service.OrderService;
import org.studyonline.orders.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 03/01/2024 4:01 pm
 */

@Api(value = "Order payment interface", tags = "Order payment interface")
@Slf4j
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Value("${pay.alipay.APP_ID}")
    String APP_ID;
    @Value("${pay.alipay.APP_PRIVATE_KEY}")
    String APP_PRIVATE_KEY;

    @Value("${pay.alipay.ALIPAY_PUBLIC_KEY}")
    String ALIPAY_PUBLIC_KEY;

    @ApiOperation("Generate payment QR code")
    @PostMapping("/generatepaycode")
    @ResponseBody
    public PayRecordDto generatePayCode(@RequestBody AddOrderDto addOrderDto){

        SecurityUtil.User user = SecurityUtil.getUser();
        String userId = user.getId();

        PayRecordDto order = orderService.createOrder(userId, addOrderDto);
        return order;
    }

    @ApiOperation("Scan code to place order")
    @GetMapping("/requestpay")
    public void requestpay(String payNo, HttpServletResponse httpResponse) throws IOException, AlipayApiException {
        PayRecord payRecordByPayno = orderService.getPayRecordByPayno(payNo);
        if(payRecordByPayno == null){
            StudyOnlineException.cast("Payment is not exist!");
        }
        if("601002".equals(payRecordByPayno.getStatus())){
            StudyOnlineException.cast("Don't need to pay it again");
        }

        //Request Alipay to place an order
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, APP_ID, APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//Create a request corresponding to the API
        alipayRequest.setNotifyUrl("http://tjxt-user-t.itheima.net/studyonline/orders/paynotify");//Set bounce and notification addresses in public parameters
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + payNo + "\"," +
                "    \"total_amount\":" + payRecordByPayno.getTotalPrice()+"," +
                "    \"subject\":\"" +payRecordByPayno.getOrderName() + " \"," +
                "    \"product_code\":\"QUICK_WAP_WAY\"" +
                "  }");//Fill in business parameters
        String form = alipayClient.pageExecute(alipayRequest).getBody(); //Call SDK to generate form
        httpResponse.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        httpResponse.getWriter().write(form);//Directly output the complete form html to the page
        httpResponse.getWriter().flush();
    }

    @ApiOperation("Check payment results")
    @GetMapping("/payresult")
    @ResponseBody
    public PayRecordDto payresult(String payNo) throws IOException {
        //Check payment results
        PayRecordDto payRecordDto = orderService.queryPayResult(payNo);
        return payRecordDto;

    }

    @ApiOperation("Receive Alipay notifications")
    @PostMapping("/paynotify")
    public void paynotify(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {

        //Get feedback information via Alipay POST
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean verify_result = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

        if(verify_result){
            //Merchant order number
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //Alipay transaction number
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //Amount of the transaction
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            //trading status
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            if (trade_status.equals("TRADE_SUCCESS")){
                //Update the payment status of the payment record table to success and the status of the order table to success.
                PayStatusDto payStatusDto = new PayStatusDto();
                payStatusDto.setTrade_status(trade_status);
                payStatusDto.setTrade_no(trade_no);
                payStatusDto.setOut_trade_no(out_trade_no);
                payStatusDto.setTotal_amount(total_amount);
                payStatusDto.setApp_id(APP_ID);
                orderService.saveAliPayStatus(payStatusDto);
            }

            response.getWriter().write("success");

        }else{//verification failed
            response.getWriter().write("fail");
        }
    }

}
