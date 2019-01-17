package ru.nsu.picaptcha.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.nsu.picaptcha.dao.UserRepository;

import java.util.Collections;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter implements UserDetailsService {

    private final LoginSuccessHandler loginSuccessHandler;
    private final UserRepository userRepository;

    @Autowired
    public WebSecurityConfiguration(LoginSuccessHandler loginSuccessHandler, UserRepository userRepository) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler)
                .and()
                .logout()
                .permitAll();

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ru.nsu.picaptcha.model.User byLogin = userRepository.findByLogin(s);
        return new User(byLogin.getLogin(), byLogin.getPassword(), Collections.emptyList());
    }
}
