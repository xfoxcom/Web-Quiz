package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerImp extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Override
protected void configure (AuthenticationManagerBuilder auth) throws Exception {

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT email, password, 'true' as enable FROM players WHERE EMAIL = ?")
                .authoritiesByUsernameQuery("select user_email, auth from auth where user_email = ?")
                .passwordEncoder(encoder());
    }
    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .mvcMatchers("/actuator/shutdown").permitAll()
                .mvcMatchers("/api/register").permitAll()
                .mvcMatchers("/api/**").authenticated()
                .and().formLogin()
                .and().csrf().disable().httpBasic();
        http.headers().frameOptions().disable();
    }
    @Bean
public PasswordEncoder encoder () {
        return new BCryptPasswordEncoder();
    }
}
