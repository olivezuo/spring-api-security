package com.jin.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.jin.security.core.CustomAuthenticationProvider;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
	private CustomAuthenticationProvider customerAuthenticationProvider;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().
		httpBasic().and().
		csrf().
		and().
		rememberMe().tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(1209600);
		
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		///JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		//db.setDataSource(dataSource);
		//return db;
		return null;
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customerAuthenticationProvider);
    }
}
