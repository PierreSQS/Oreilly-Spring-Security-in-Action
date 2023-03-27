package com.laurentiuspilca.ssia.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class ProjectConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    PathRequest.H2ConsoleRequestMatcher h2ConsoleRequestMatcher = PathRequest.toH2Console();
    httpSecurity.csrf().ignoringRequestMatchers(h2ConsoleRequestMatcher)
            .and()
              .authorizeHttpRequests().requestMatchers(h2ConsoleRequestMatcher).permitAll()
            .and()
              .headers().frameOptions().sameOrigin()
            .and()
              .authorizeHttpRequests().anyRequest().authenticated()
            .and()
              .httpBasic();
    return httpSecurity.build();
  }
  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    String usersByUsernameQuery = "select username, password, enabled from spring.users where username = ?";
    String authsByUserQuery = "select username, authority from spring.authorities where username = ?";
    var userDetailsManager = new JdbcUserDetailsManager(dataSource);
    userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
    userDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);
    return userDetailsManager;

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
