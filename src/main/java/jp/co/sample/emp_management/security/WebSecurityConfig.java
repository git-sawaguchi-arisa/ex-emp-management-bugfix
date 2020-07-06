package jp.co.sample.emp_management.security;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdministratorService administratorService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //指定したパターンごとに制限をかける
                .antMatchers("/js/**", "/css/**", "/img/**","/**").permitAll()//制限なし
        //アクセスの許可
            .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/login/success", true)
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/insert").permitAll()
                .antMatchers("/toInsert").permitAll()
                .anyRequest().authenticated();




        http.logout()
        //  ログアウト時の遷移先URL
                .logoutSuccessUrl("/")
        //  ログアウトするとCookieのJSESSIONIDを削除
                .deleteCookies();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(administratorService)
                .passwordEncoder(passwordEncoder());
        //adminの初期設定
        Administrator admin = new Administrator("admin","admin@localhost","konnichihanegishi");
        administratorService.insert(admin);
    }

    @Autowired
    void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(administratorService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}