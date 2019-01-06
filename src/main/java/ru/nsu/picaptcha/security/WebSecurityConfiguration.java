package ru.nsu.picaptcha.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final AuthenticationFailureHandler FAILURE_HANDLER = new SimpleUrlAuthenticationFailureHandler();

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfiguration(RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                                    LoginSuccessHandler loginSuccessHandler,
                                    LogoutSuccessHandler logoutSuccessHandler,
                                    DataSource dataSource) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.loginSuccessHandler = loginSuccessHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.dataSource = dataSource;
    }

    @Autowired
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select login, password, enabled from users where login=?")
                .authoritiesByUsernameQuery("select login, role from user_roles where login=?");
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
                .failureHandler(FAILURE_HANDLER)
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler);
    }
}
