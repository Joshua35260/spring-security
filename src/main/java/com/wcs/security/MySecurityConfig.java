package com.wcs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class MySecurityConfig {

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    UserDetails user = User
        .withUsername("Steve")
        .password(encoder.encode("motdepasse"))
        .roles("CHAMPION")
        .build();

    UserDetails admin = User
        .withUsername("Nick")
        .password(encoder.encode("flerken"))
        .roles("DIRECTOR")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    return http
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/").permitAll();
                auth.requestMatchers("/avengers/assemble").hasAnyRole("CHAMPION", "DIRECTOR");
                auth.requestMatchers("/secret-bases").hasRole("DIRECTOR");
                auth.anyRequest().authenticated();
            })
            .formLogin(Customizer.withDefaults())
            .build();

}
}