package org.studyonline.ucenter.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.studyonline.ucenter.mapper.MenuMapper;
import org.studyonline.ucenter.mapper.UserMapper;
import org.studyonline.ucenter.model.dto.AuthParamsDto;
import org.studyonline.ucenter.model.dto.XcUserExt;
import org.studyonline.ucenter.model.po.Menu;
import org.studyonline.ucenter.service.AuthService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Customize UserDetailsService to interface with Spring Security
 * @Author: Chengguang Li
 * @Date: 22/02/2024 4:45 pm
 */

@Slf4j
@Component
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //convert Json to AuthParamsDto
        AuthParamsDto authParamsDto = null;
        try{
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        }catch (Exception e){
            log.error("The parameters for requesting authentication do not meet the requirements; {}",e.getMessage());
        }

        String authType = authParamsDto.getAuthType();//get the auth type
        if(StringUtils.isBlank(authType)){
            log.error("the XcUser: {} does not have the authentication type",authParamsDto);
            return null;
        }
        String beanName = authType + "_authservice";
        AuthService authService = applicationContext.getBean(beanName, AuthService.class);//unified authentication service
        XcUserExt userExt = authService.execute(authParamsDto);

        UserDetails userDetails = getUserPrincipal(userExt);
        return userDetails;
    }


    /**
     * @description get user details
     * @param user  user id
     * @return org.studyonline.ucenter.model.po.XcUser ; XcUser Information
     * @Author: Chengguang Li
     * @Date: 24/02/2024 11:45 pm
     */
    public UserDetails getUserPrincipal(XcUserExt user){
        //XcUser authority, if it is empty, an error will be reported: Cannot pass a null GrantedAuthority collection
        String[] authorities = {"empty"};
        List<Menu> menus = menuMapper.selectPermissionByUserId(user.getId());
        if(menus.size() > 0){
            List<String> permissions = new ArrayList<>();
            menus.forEach(item ->{
                permissions.add(item.getCode());
            });
            authorities = permissions.toArray(new String[permissions.size()]);
        }
        String password = user.getPassword();
        user.setPassword(null);
        //convert XcUser object to Json
        String userString = JSON.toJSONString(user);
        //Create UserDetails object
        UserDetails userDetails = User.withUsername(userString).password(password ).authorities(authorities).build();
        return userDetails;
    }

}
