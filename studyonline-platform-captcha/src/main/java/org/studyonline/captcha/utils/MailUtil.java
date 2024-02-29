package org.studyonline.captcha.utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 * @Description: Send verification code to email
 * @Author: Chengguang Li
 * @Date: 29/02/2024 9:28 pm
 */
public class MailUtil {


    /**
     *
     * @param email
     * @param code
     * @throws MessagingException
     */
    public static void sendTestMail(String email, String code) throws MessagingException {
        // Create a Properties class to record some properties of the mailbox
        Properties props = new Properties();
        // Indicates that SMTP sends emails and must be authenticated.
        props.put("mail.smtp.auth", "true");
        // Enable STARTTLS
        props.put("mail.smtp.starttls.enable", "true");
        //Fill in the SMTP server here
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        // Writer's account
        props.put("mail.user", "cristianoronando@gmail.com");
        // 16-bit STMP password
        props.put("mail.password", "15d4d5d4d5d4d5d8");
        //Build authorization information for use with SMTP for authentication
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // username , password
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // Use environment properties and authorization information to create an email session
        Session mailSession = Session.getInstance(props, authenticator);

        // Create email message
        MimeMessage message = new MimeMessage(mailSession);
        // set sender
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        // Set the recipient's email address
        InternetAddress to = new InternetAddress(email);
        message.setRecipient(Message.RecipientType.TO, to);
        // Set email title
        message.setSubject("Study Online Platform verification code");
        // set email contents
        message.setContent("Hi Mate! \n Verification Code :" + code + "(Valid for one minute, please do not inform others)", "text/html;charset=UTF-8");
        // SEND EMAIL
        Transport.send(message);

    }

}
