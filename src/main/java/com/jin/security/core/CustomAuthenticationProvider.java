package com.jin.security.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jin.security.domain.UserAccount;
import com.jin.security.service.UserAccountService;

@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private UserAccountService userAccountService;
	

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1) throws AuthenticationException {
		// TODO Auto-generated method stub

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
		String password = (String) authentication.getCredentials();
		if (!StringUtils.hasText(password)) {
			logger.warn("Username {}: no password provided", username);
		    throw new BadCredentialsException("Please enter password");
		}
		
		UserAccount userAccount = userAccountService.findByUsername(username);
		if (userAccount == null) {
        	logger.warn("Username {} password {}: user not found", username, password);
            throw new UsernameNotFoundException("Invalid Login");
        }
		
				
		if (!passwordEncoder().matches(password, userAccount.getPassword())) {
        	logger.warn("Username {} password {}: invalid password", username, password);
            throw new BadCredentialsException("Invalid Login");
        }
		
/*

		if (!(UserAccountStatus.STATUS_APPROVED.name().equals(user.getStatus()))) {
        	logger.warn("Username {}: not approved", username);
            throw new BadCredentialsException("User has not been approved");
        }
        
        */
        if (!userAccount.isEnabled()) {
        	logger.warn("Username {}: disabled", username);
            throw new BadCredentialsException("User disabled");
        }
		
		
		String roles = StringUtils.arrayToCommaDelimitedString(userAccount.getRoles().toArray());
		List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
		
		
		//auths = AuthorityUtils.NO_AUTHORITIES;
		return new CustomUser(username, password, 
				true, // enabled
                true, // account not expired
                true, // credentials not expired
                true, // account not locked
                auths,
                userAccount.getUserSource());

	}

}
