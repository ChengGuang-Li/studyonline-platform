package org.studyonline.orders.config;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 01/03/2024 2:45 pm
 */
public class AlipayConfig {
    // Merchant appid
    //public static String APPID = "";
    // Private key in pkcs8 format
    //public static String RSA_PRIVATE_KEY = "";
    public static String notify_url = "http://192.168.101.65:63010/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
    public static String return_url = "http://192.168.101.65:63010/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
    //Request gateway address
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // Coding
    public static String CHARSET = "UTF-8";
    // Return Format
    public static String FORMAT = "json";
    // Alipay Public Key
    //public static String ALIPAY_PUBLIC_KEY = "";
    // logging directory
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
