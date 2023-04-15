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
                        .password("{bcrypt}$2a$10$f73apJVt1QFI49zWnZD2Q.JrnEZ5C8lgwrlLbSmlLtn18iuAEP70W")//12345
                        .authorities("READ")
                        .build();

        var user2 = User.withUsername("jane")
                        .password("{bcrypt}$2a$10$ReIgPzOeIn8Y1HyR3kojpeiWSyylAAQ04AqDCyTl8dFjsWqOQNrpW")//12345
                        .authorities("WRITE")
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
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeHttpRequests()
                .anyRequest().access((new WebExpressionAuthorizationManager("hasAuthority('WRITE')")));

        return http.build();
    }
}
