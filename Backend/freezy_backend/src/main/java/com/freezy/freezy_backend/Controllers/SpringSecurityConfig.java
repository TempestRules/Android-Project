package com.freezy.freezy_backend.Controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/Create/Storage").permitAll()
                .antMatchers(HttpMethod.GET, "/Read/AccountDetails").permitAll()
                .antMatchers(HttpMethod.POST, "/Create/Signup").permitAll()
                .antMatchers(HttpMethod.POST, "/Read/Authenticate").permitAll()
                .antMatchers(HttpMethod.PUT, "/Update/Storage").permitAll()
                .antMatchers(HttpMethod.DELETE, "/Delete/Storage").permitAll()
                .antMatchers(HttpMethod.PUT, "/Update/Category").permitAll()
                .antMatchers(HttpMethod.DELETE, "/Delete/Category").permitAll()
                .anyRequest().authenticated();
    }
}
