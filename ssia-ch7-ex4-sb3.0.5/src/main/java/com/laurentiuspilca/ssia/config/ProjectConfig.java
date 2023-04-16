package com.laurentiuspilca.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("{bcrypt}$2a$10$zFGpB9Dbe0YQDH2RrO8Zwe7qSNgIsxAgEmSkLoSPhGuxvvukXetbi") // john
                .authorities("read")
                .build();

        var user2 = User.withUsername("jane")
                .password("{bcrypt}$2a$10$IDEv270BUObCpLeofo8BIu2xEqws.klCW7S6xBjADyZLZpBXenw0.") // jane
                .authorities("read", "write", "delete")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic();


        http.authorizeHttpRequests()
                .anyRequest()
                .access(new WebExpressionAuthorizationManager("T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(12, 0))"));

        return http.build();
    }
}
