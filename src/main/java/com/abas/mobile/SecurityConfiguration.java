package com.abas.mobile;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	
	// @Autowired
	// private SessionRegistry sessionRegistry;
	//
	// @Autowired
	// private com.abas.mobile.SessionListener sessionListener;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
		    .antMatchers("/")
		    .permitAll();
		http.sessionManagement()		   
		    .maximumSessions(100)
		    .maxSessionsPreventsLogin(false)
		    .and()
		    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
		    .sessionFixation().migrateSession();
		// .sessionRegistry(sessionRegistry());
		
	}
	
	// @Bean
	// public ServletListenerRegistrationBean<HttpSessionListener> sessionListener1()
	// {
	// return new ServletListenerRegistrationBean<HttpSessionListener>(new SessionListener());
	// }
	
	//
	// @Bean
	// public SessionRegistry sessionRegistry()
	// {
	// return new SessionRegistryImpl();
	// }
	
}
