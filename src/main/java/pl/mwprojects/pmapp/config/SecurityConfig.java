package pl.mwprojects.pmapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.mwprojects.pmapp.user.PmappUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PmappUserDetailsService pmappUserDetailsService;


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(pmappUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/confirm-account").permitAll()
                .antMatchers("/project/register", "/project/edit/**", "/project/delete/**").hasAuthority("SUPERADMIN")
                .antMatchers("/team/register", "/team/edit/**", "/team/delete/**").hasAuthority("SUPERADMIN")
                .antMatchers("/user/register", "/user/edit/**", "/user/editPass/**", "/user/delete/**").hasAuthority("SUPERADMIN")
                .antMatchers("/person/register", "/person/edit/**", "/person/delete/**").hasAuthority("SUPERADMIN")
                .antMatchers("/assignment/register", "/assignment/edit/**", "/assignment/delete/**").hasAnyAuthority("SUPERADMIN", "ADMIN")
                .antMatchers("/**").authenticated()
                .and().csrf().disable().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/loginError")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/css/**", "/loginError");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PmappUserDetailsService customUserDetailsService() {
        return new PmappUserDetailsService();
    }

}
