package api.app.travelroute.configuration;

import api.base.common.PasswordHashEncoder;
import api.app.travelroute.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static api.app.travelroute.entity.Role.ROLE_ADMIN;
import static api.app.travelroute.entity.Role.ROLE_GUIDE;

/**
 * Created by cc on 16/6/10.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Order(1)
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        UserAuthService userAuthService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            //这里只配置访问权限
            http.authorizeRequests()
                    .antMatchers("/api/v1/user/register").permitAll()//user api除了注册其他都需要认证
                    .antMatchers("/api/v1/user/**").authenticated()
                    .antMatchers("/api/v1/order/**").authenticated()//order api需要认证
                    .and()
                    .antMatcher("/api/v1/dest/**").authorizeRequests().antMatchers(HttpMethod.GET).permitAll().anyRequest().hasAnyRole(String.valueOf(ROLE_ADMIN), String.valueOf(ROLE_GUIDE))//旅游产品api
                    .and()
                    .antMatcher("/api/v1/route/**").authorizeRequests().antMatchers(HttpMethod.GET).permitAll().anyRequest().hasAnyRole(String.valueOf(ROLE_ADMIN), String.valueOf(ROLE_GUIDE))//路线 api
                    .and()
                    .antMatcher("/api/**").authorizeRequests().anyRequest().authenticated()//默认其他所有api均需认证
                    .and()
                    .csrf().disable()
                    .httpBasic();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userAuthService)
                    .passwordEncoder(new PasswordHashEncoder());
        }
    }

}
