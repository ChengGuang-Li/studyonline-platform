package org.studyonline.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.annotation.Resource;

/**
 * @Description: Authorization server configuration
 * @Author: Chengguang Li
 * @Date: 20/02/2024 3:20 pm
 */
 @Configuration
 @EnableAuthorizationServer
 public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

  @Resource(name="authorizationServerTokenServicesCustom")
  private AuthorizationServerTokenServices authorizationServerTokenServices;

 @Autowired
 private AuthenticationManager authenticationManager;

  //Client details service
  @Override
  public void configure(ClientDetailsServiceConfigurer clients)
          throws Exception {
        clients.inMemory()//Use in-memory storage
                .withClient("XcWebApp")// client_id
                //.secret("XcWebApp")//client key
                .secret(new BCryptPasswordEncoder().encode("XcWebApp"))//client key
                .resourceIds("studyonline-platform")//Resource list
                .authorizedGrantTypes("authorization_code", "password","client_credentials","implicit","refresh_token")// Authorization types allowed by this client authorization_code, password, refresh_token, implicit, client_credentials
                .scopes("all")// Allowed authorization scope
                .autoApprove(false)//false jump to authorization page
                //The redirect address where the client receives the authorization code
                .redirectUris("http://www.studyonline.cn");
  }


  //Access configuration for token endpoint
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
   endpoints
           .authenticationManager(authenticationManager)//Authentication Manager
           .tokenServices(authorizationServerTokenServices)//Token Management Service
           .allowedTokenEndpointRequestMethods(HttpMethod.POST);
  }

  //Security configuration of the token endpoint
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security){
   security
           .tokenKeyAccess("permitAll()")                    //oauth/token_key is open
           .checkTokenAccess("permitAll()")                  //oauth/check_token is open
           .allowFormAuthenticationForClients();			//Form authentication (request token)

  }



 }
