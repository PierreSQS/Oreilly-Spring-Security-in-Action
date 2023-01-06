package com.laurentiuspilca.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {

        var inMemUserDetailsManager = new InMemoryUserDetailsManager();

        var user = User.withDefaultPasswordEncoder()
                .username("john")
                .password("12345")
                .authorities("read")
                .build();

        inMemUserDetailsManager.createUser(user);

        return inMemUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeHttpRequests().anyRequest().authenticated();
        return http.build();
    }
}
