package com.pany.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.user.name}")
    private String secureUser;

    @Value("${spring.security.user.password}")
    private String securePassword;

    @Value("${spring.security.user.role}")
    private String secureRole;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(secureUser).password(securePassword).roles(secureRole);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http
                .csrf().disable();

//		http
//			.antMatcher("/v1/claims").authorizeRequests()
//			.anyRequest().authenticated()
//		.and()
//			.addFilterBefore(new JwtAuthenticationFilter(), BasicAuthenticationFilter.class);

        //for http only invoke these configurations on requests matching the ant pattern /v1/temp/**
        //authorizing any request to authenticated users
        //and add filter JwtAuthenticationFilter() before BasicAuthenticationFilter
        // /v1/s/**
        http
                .antMatcher("/**/s/**").authorizeRequests()
                .anyRequest().authenticated();
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtType, jwkSecret), BasicAuthenticationFilter.class);
//			.authorizeRequests()
//				.antMatchers("/v1/simple/**").permitAll()
//		.and()
//        	.antMatcher("/v1/simple/**")
//			.requestMatchers()
//			.antMatchers("/v1/temp/**", "/v1/simple/**");
//		    .authorizeRequests()
//				.antMatchers("/temp/**")
//					.authenticated()
//					.hasAnyRole("ROLE_CONSUMER")
//				.antMatchers("/simple/**")
//					.hasAnyRole("ROLE_ANONYMOUS", "ROLE_CONSUMER")
//				.and()
//				.anyRequest().authenticated();
    }
}
