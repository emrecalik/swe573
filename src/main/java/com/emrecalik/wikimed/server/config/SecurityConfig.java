package com.emrecalik.wikimed.server.config;

import com.emrecalik.wikimed.server.controller.ActivityController;
import com.emrecalik.wikimed.server.controller.ArticleController;
import com.emrecalik.wikimed.server.controller.AuthController;
import com.emrecalik.wikimed.server.controller.PureArticleController;
import com.emrecalik.wikimed.server.controller.UserController;
import com.emrecalik.wikimed.server.controller.WikiApiController;
import com.emrecalik.wikimed.server.controller.entrez.EntrezApiController;
import com.emrecalik.wikimed.server.security.CustomUserDetailsService;
import com.emrecalik.wikimed.server.security.JwtAuthFilter;
import com.emrecalik.wikimed.server.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(AuthController.BASE_URL + "/**").permitAll()
                .antMatchers(EntrezApiController.BASE_URL + "/**").permitAll()
                .antMatchers(WikiApiController.BASE_URL + "/**").permitAll()
                .antMatchers(ArticleController.BASE_URL + "/**").permitAll()
                .antMatchers(UserController.BASE_URL + "/**").permitAll()
                .antMatchers(ActivityController.BASE_URL + "/**").permitAll()
                .antMatchers(PureArticleController.BASE_URL + "/**").permitAll()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();

        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }
}
