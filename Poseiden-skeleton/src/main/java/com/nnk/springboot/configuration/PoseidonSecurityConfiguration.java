package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class PoseidonSecurityConfiguration {

//  private static final String[] AUTH_WHITELIST = {
//          "/registrer/**","/home", "/transfer", "/profile", "/contact"  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .authorizeRequests()
            .anyRequest().permitAll();

    http
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/bidList/list",true);


    http
            .logout().logoutUrl("/logout").logoutSuccessUrl("/login");


    return http.build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
  }

}
