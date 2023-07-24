package com.nnk.springboot.configuration;

import com.nnk.springboot.domain.Role;
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

  private static final String[] AUTH_WHITELIST_ADMIN = {
//          "/home", "/user/**" };
          "/home", "/user" };

  private static final String[] AUTH_WHITELIST_USER = {
//          "/bidList/**","/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**" };
          "/bidList","/curvePoint", "/rating", "/ruleName", "/trade" };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(AUTH_WHITELIST_ADMIN).hasAuthority(Role.ADMIN.name())
                    .requestMatchers(AUTH_WHITELIST_USER).hasAuthority(Role.USER.name())
                    .anyRequest().permitAll()
            );

    http
            .formLogin(a -> a.loginPage("/login")
                    .loginProcessingUrl("/login")
                    .successHandler(successHandler())
                    .failureUrl("/login?error=true")
                    .permitAll());

    http
            .logout(withDefaults());

    http
            .rememberMe()
            .key("LUJDU_DYJNF8FLFOI_Foifkqsi_432824412");

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder();
  }


  /**
   * Custom success handler
   */
  @Bean
  public PoseidonAuthenticationSuccessHandler successHandler() {
    return new PoseidonAuthenticationSuccessHandler();
  }
}
