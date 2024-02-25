package org.studyonline.auth.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studyonline.ucenter.mapper.UserMapper;
import org.studyonline.ucenter.model.po.XcUser;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 20/02/2024 2:21 pm
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserMapper userMapper;


    @RequestMapping("/login-success")
    public String loginSuccess() {

        return "login successful";
    }


    @RequestMapping("/user/{id}")
    public XcUser getuser(@PathVariable("id") String id) {
        XcUser xcUser = userMapper.selectById(id);
        return xcUser;
    }

    @RequestMapping("/r/r1")
    @ApiOperation("Only those with p1 permission can access")
    @PreAuthorize("hasAuthority('p1')")
    public String r1() {
        return "Access r1 resources";
    }

    @RequestMapping("/r/r2")
    @ApiOperation("Only accessible with p2 permissions")
    @PreAuthorize("hasAuthority('p2')")
    public String r2() {
        return "Access r2 resources";
    }



}
