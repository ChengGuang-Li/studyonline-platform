package org.studyonline.content.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <P>
 *        Mybatis-Plus configuration
 * </p>
 */
@Configuration
@MapperScan(value = "org.studyonline.content.mapper")
public class MybatisPlusConfig {
    /**
     * Define paging interceptor
     *  Need to set MybatisConfiguration#useDeprecatedExecutor = false
     *  Avoid cache problems (this attribute will be removed after the old plug-in is removed)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


}
