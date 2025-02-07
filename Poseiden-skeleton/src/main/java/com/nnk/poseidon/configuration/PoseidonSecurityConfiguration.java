package com.nnk.poseidon.configuration;

import com.nnk.poseidon.enumerator.UserRole;
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
          "/home/admin", "/user/**" };

  private static final String[] AUTH_WHITELIST_USER = {
          "/home/user", "/bidList/**","/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**" };

  private static final String[] AUTH_WHITELIST_PUBLIC = {
          "/css/**","/error/**", "/img/**", "/js/**", "/fragments/**", "/login", "/app/register" };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(AUTH_WHITELIST_PUBLIC).permitAll()
                    .requestMatchers(AUTH_WHITELIST_ADMIN).hasAuthority(UserRole.ADMIN.name())
                    .requestMatchers(AUTH_WHITELIST_USER).hasAuthority(UserRole.USER.name())
                    .anyRequest().authenticated()
            );

    http
            .formLogin(a -> a.loginPage("/app/login")
                    .loginProcessingUrl("/app/login")
                    .successHandler(successHandler())
                    .failureUrl("/app/login?error=true")
                    .permitAll());

    http
            .logout()
            .logoutUrl("/app/logout")
            .logoutSuccessUrl("/app/login")
            .invalidateHttpSession(true);

    http
            .exceptionHandling()
            .accessDeniedPage("/app/error");

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
