package org.studyonline.messagesdk.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: Chengguang Li
 * @Date: 17/02/2024 10:57 pm
 */
@Configuration("messagesdk_mpconfig")
@MapperScan("org.studyonline.messagesdk.mapper")
public class MybatisPlusConfig {

}
