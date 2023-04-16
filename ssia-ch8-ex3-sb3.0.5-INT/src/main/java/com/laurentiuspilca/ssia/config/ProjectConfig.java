package com.laurentiuspilca.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("{bcrypt}$2a$10$i52AF2SwRAcQzYN9RmUO7.s3aZQoWeAhPGnVmOBx2pZq7oNBWn60i")//12345
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("jane")
                .password("{bcrypt}$2a$10$EphUjyTxPzzEUaZgrZzl/OvKKpxiIB4Qxbpfs4c.Kkle1pVRmLYZC")//12345
                .roles("MANAGER")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeHttpRequests()
                .requestMatchers( "/a/b/**").authenticated()
                .anyRequest().permitAll();

        http.csrf().disable();
    }
}
