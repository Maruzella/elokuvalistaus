package hh.palvelinohjelmointi.elokuvaprojekti;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import hh.palvelinohjelmointi.elokuvaprojekti.webcontroller.UserDetailServiceImpl;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
        http
        	.authorizeRequests().antMatchers("/css/**", "/", "/elokuvalista").permitAll()
        .and()
    		.authorizeRequests().anyRequest().authenticated()
    	.and()
        	.formLogin()
        	.loginPage("/login")
        	.defaultSuccessUrl("/", true)
        	.permitAll()
        .and()
        	.logout()
        	.invalidateHttpSession(true)
        	.clearAuthentication(true)
        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        	.logoutSuccessUrl("/")
        	.permitAll();
        
	}
	
	@Autowired
    private UserDetailServiceImpl userDetailsService;
	    
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	    }
}
