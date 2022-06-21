package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfigurerImp extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Override
protected void configure (AuthenticationManagerBuilder auth) throws Exception {

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT name, password FROM players WHERE EMAIL = ?")
                .authoritiesByUsernameQuery("SELECT role FROM players WHERE EMAIL = ?")
                .passwordEncoder(encoder());
        auth
                .inMemoryAuthentication()
                .withUser("aaa").password("123456").roles("USER")
                .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .mvcMatchers("/actuator/shutdown").permitAll()
                .mvcMatchers("/api/register").permitAll()
                .mvcMatchers("/api/**").authenticated()
                .and().csrf().disable().httpBasic();
        http.headers().frameOptions().disable();
    }
    @Bean
public PasswordEncoder encoder () {
        return new BCryptPasswordEncoder();
    }
}
