package org.studyonline.learning.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: Get the current user identity tool class
 * @Author: Chengguang Li
 * @Date: 24/02/2024 10:10 pm
 */
@Slf4j
public class SecurityUtil {

    public static User getUser() {
        try {
            Object principalObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principalObj instanceof String) {
                //Retrieve user identity information
                String principal = principalObj.toString();
                //Convert json to object
                User user = JSON.parseObject(principal, User.class);
                return user;
            }
        } catch (Exception e) {
            log.error("Error in getting current logged in user identity:{}", e.getMessage());
        }

        return null;
    }
    @Data
    public static class User implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id;

        private String username;

        private String password;

        private String salt;

        private String name;
        private String nickname;
        private String wxUnionid;
        private String companyId;
        /**
         * avatar
         */
        private String userpic;

        private String utype;

        private LocalDateTime birthday;

        private String sex;

        private String email;

        private String cellphone;

        private String qq;

        /**
         * User Status
         */
        private String status;

        private LocalDateTime createTime;

        private LocalDateTime updateTime;


    }
    
}

