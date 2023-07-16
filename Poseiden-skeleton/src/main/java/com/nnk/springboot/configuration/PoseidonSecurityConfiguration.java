package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class PoseidonSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .anyRequest().permitAll();

    http
            .csrf().disable()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/bidList/list");

//    http
//            .oauth2Login()
//            .loginPage("/login")
//            .defaultSuccessUrl("/bidList/list");

    http
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login");

  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
  }

}
