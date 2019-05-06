package com.abas.mobile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.abas.mobile.service.AbasSessionRegistry;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true,jsr250Enabled=true)
@EnableWebSecurity
public class SecurityConfiguration
{
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@Autowired
	private com.abas.mobile.AbasSessionListener abasSessionListener;
	
	@Autowired
	private AbasSessionRegistry abasSessionRegistry;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		// auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("user1")).roles("USER").and()
		// .withUser("user2").password(passwordEncoder().encode("user2")).roles("USER").and().withUser("admin")
		// .password(passwordEncoder().encode("admin")).roles("ADMIN");
		//
		// auth.jdbcAuthentication().dataSource(dataSource);
		//
		auth.jdbcAuthentication().dataSource(dataSource);
		
	}
	
	@Configuration
	@Order(99)
	class THSecurity extends WebSecurityConfigurerAdapter
	{
		public THSecurity()
		{
			super();
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception
		{
			http.authorizeRequests()
			    .antMatchers("/")
			    .permitAll();
			
			// http.authorizeRequests()
//			    .antMatchers(// @formatter:off
//			                 "/abasadmin",
//			                 "/abasadmin/*",
//			                 "/abasadmin/**"
//			                 // @formatter:on
			// )
			// .access("hasRole('ADMIN')").anyRequest()
			// .authenticated();
			//
			// http.formLogin().loginPage("/login")// .loginPage("/login")
			// .loginProcessingUrl("/login")// .loginProcessingUrl("/login")
			// .defaultSuccessUrl("/default",true)// .defaultSuccessUrl("/default",true)
			// .failureUrl("/login?loginFailed=true")// .failureUrl("/login?loginFailed=true")
			// .permitAll().and().logout().invalidateHttpSession(true).clearAuthentication(true)
			// .deleteCookies("remember_me_cookie").logoutRequestMatcher(new AntPathRequestMatcher("/**/logout"))
			// .logoutSuccessUrl("/login?logout").permitAll().and().requestCache().and().exceptionHandling()
			// .accessDeniedPage("/403").and().csrf().disable();
				
			http.sessionManagement()
			    .maximumSessions(100)
			    .maxSessionsPreventsLogin(false)
			    .and()
			    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			    .sessionFixation().migrateSession();
			// .sessionRegistry(sessionRegistry());
			
		}
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher()
	{
		return new HttpSessionEventPublisher();
	}
	
	@Bean
	public SmartInitializingSingleton importProcessor()
	{
		return ()->
		{
			LOGGER.info("@@@ application initialization completed");
		};
	}
	
	@PostConstruct
	public void init()
	{
		LOGGER.info("@@@ SecurityConfiguration class init completed: "+abasSessionRegistry.getSessions().size());
	}
	
}
