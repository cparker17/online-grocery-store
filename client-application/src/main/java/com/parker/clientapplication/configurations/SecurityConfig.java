package com.parker.clientapplication.configurations;

import com.parker.clientapplication.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/items").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/items/update/{id}", "/items/item-form/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/items/delete/{id}").hasRole("ADMIN")
                .antMatchers("/cart", "/cart-add/{itemId}", "/cart-remove/{cartItemId}",
                        "/cart-update/{cartItemId}/{amount}").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/", "/register", "/items", "/sign-in", "/items/{id}").permitAll()
                .antMatchers("/new-user").permitAll()
                .anyRequest().authenticated()

                .and()

                .formLogin()
                    .loginPage("/login").permitAll()
                    .failureUrl("/login-error").permitAll()

                .and()

                .logout()
                    .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
