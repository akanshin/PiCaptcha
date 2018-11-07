package ru.nsu.picaptcha.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@SuppressWarnings("unused")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;

    AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Autowired
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        // TODO: Move to database
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles("USER").and()
                .withUser("admin").password("admin").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/info/special")
                .hasRole("USER")
                .and()
                .authorizeRequests()
                .antMatchers("/info/secret")
                .hasRole("ADMIN")
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(failureHandler)
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler);
    }
}