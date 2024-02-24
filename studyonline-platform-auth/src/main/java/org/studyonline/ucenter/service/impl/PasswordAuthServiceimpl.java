package org.studyonline.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studyonline.ucenter.mapper.UserMapper;
import org.studyonline.ucenter.model.dto.AuthParamsDto;
import org.studyonline.ucenter.model.dto.UserExt;
import org.studyonline.ucenter.model.po.User;
import org.studyonline.ucenter.service.AuthService;

/**
 * @Description: Authentication service based on username and password
 * @Author: Chengguang Li
 * @Date: 24/02/2024 11:25 pm
 */
@Service("password_authservice")
public class PasswordAuthServiceimpl implements AuthService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserExt execute(AuthParamsDto authParamsDto) {
        //TODO: 1. check the Verification code

        //2. check the Account exist or not
        String userName = authParamsDto.getUsername();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
        if(user == null){
            throw new RuntimeException("This User is not exist ");
        }
        //3. check the Password
        String originalPassword = user.getPassword();
        String inputPassword = authParamsDto.getPassword();
        boolean matches = passwordEncoder.matches(inputPassword, originalPassword);
        if(!matches){
            throw new RuntimeException("Account password does not match");
        }
        UserExt userExt = new UserExt();
        BeanUtils.copyProperties(user,userExt);
        return userExt;
    }
}
