package com.example.Traceability.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http.authorizeHttpRequests( (request) ->{
            request.requestMatchers("/").permitAll();

            request.requestMatchers("/css/**","/static/**").permitAll();
            request.anyRequest().authenticated();

        }).formLogin(form ->{
            form.loginPage("/").permitAll();
            form.usernameParameter("username");
            form.passwordParameter("password");
            form.defaultSuccessUrl("/nav",true);
        }).logout( (logout)->{
            logout.logoutSuccessUrl("/");
        });


        return http.build();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
