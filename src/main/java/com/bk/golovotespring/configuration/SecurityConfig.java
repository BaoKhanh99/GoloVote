package com.bk.golovotespring.configuration;

import com.bk.golovotespring.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    private UserDetailsServiceImpl userDetailsService;



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
        http.authorizeRequests().antMatchers("/admin","/delete","/update").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/posting").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')");
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests().and().formLogin()//
                .loginProcessingUrl("/permission-check")
                .loginPage("/home-page")//
                .defaultSuccessUrl("/position-list")//????y Khi ????ng nh???p th??nh c??ng th?? v??o trang n??y. userAccountInfo s??? ???????c khai b??o trong controller ????? hi???n th??? trang view t????ng ???ng
                .failureUrl("/login?error=true")// Khi ????ng nh???t sai username v?? password th?? nh???p l???i
                .usernameParameter("username")// tham s??? n??y nh???n t??? form login ??? b?????c 3 c?? input  name='username'
                .passwordParameter("password")// tham s??? n??y nh???n t??? form login ??? b?????c 3 c?? input  name='password'
                // C???u h??nh cho Logout Page. Khi logout m??nh tr??? v??? trang
                .and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .deleteCookies("SESSION")
                .logoutSuccessUrl("/").permitAll();

    }
}
