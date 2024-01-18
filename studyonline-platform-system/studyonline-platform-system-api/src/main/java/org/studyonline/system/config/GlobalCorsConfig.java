package org.studyonline.system.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * @description CORS configuration
 * @author ChengGuang
 * @date 06/01/2024 17:01
 * @version 1.0
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //Allow whitelist domain names to make cross-domain calls
        config.addAllowedOrigin("*");
        //Allow cookies to be sent across
        config.setAllowCredentials(true);
        //Release all original header information
        config.addAllowedHeader("*");
        //Allow all request methods to be called across domains
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
