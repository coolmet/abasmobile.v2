package com.abas.mobile;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import com.abas.mobile.model.AbasUserDetails;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true,jsr250Enabled=true)
@EnableConfigurationProperties(
{AbasConfigProperties.class,AbasMobileUsersProperties.class})
@EnableWebSecurity
public class SecurityConfiguration
{
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	// @Autowired
	// private SessionRegistry sessionRegistry;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	AbasMobileUsersProperties abasMobileUsers;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		for(AbasUserDetails user:abasMobileUsers.getUsers())
		{
			auth.inMemoryAuthentication().withUser(user.getUsername()).password(this.passwordEncoder().encode(user.getPassword())).roles(user.getRoles());
		}
		
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
			    .antMatchers("/",
			                 "/**/css/**",
			                 "/**/fonts/**",
			                 "/**/js/**",
			                 "/**/images/**",
			                 "/index")
			    .permitAll()
			    .antMatchers(// @formatter:off
			                 "/abasadmin",
			                 "/abasadmin/*",
			                 "/abasadmin/**"
			                 // @formatter:on
				)
			    .access("hasRole('ADMIN')").anyRequest()
			    .authenticated()
			    .antMatchers(// @formatter:off
			                 "/wh",
			                 "/wh/*",
			                 "/wh/**"
			                 // @formatter:on
				)
			    .access("hasRole('USER_WH')").anyRequest()
			    .authenticated()
			    .antMatchers(// @formatter:off
			                 "/pdc",
			                 "/pdc/*",
			                 "/pdc/**"
			                 // @formatter:on
				)
			    .access("hasRole('USER_PDC')").anyRequest()
			    .authenticated();
				
			http.formLogin().loginPage("/login")// .loginPage("/login")
			    .loginProcessingUrl("/login")// .loginProcessingUrl("/login")
			    .defaultSuccessUrl("/default",true)// .defaultSuccessUrl("/default",true)
			    .failureUrl("/login?loginFailed=true")// .failureUrl("/login?loginFailed=true")
			    .permitAll().and().logout().invalidateHttpSession(true).clearAuthentication(true)
			    .deleteCookies("remember_me_cookie").logoutRequestMatcher(new AntPathRequestMatcher("/**/logout"))
			    .logoutSuccessUrl("/?logout").permitAll().and().requestCache().and().exceptionHandling()
			    .accessDeniedPage("/403").and().csrf().disable();
			
			http.rememberMe().userDetailsService(userDetailsService).rememberMeServices(rememberMeServices());
			
			http.sessionManagement()
			    .maximumSessions(10)
			    .maxSessionsPreventsLogin(false)
			    .sessionRegistry(sessionRegistry())
			    .and()
			    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			    .sessionFixation().migrateSession();
			
			//
			CharacterEncodingFilter filter=new CharacterEncodingFilter();
			filter.setEncoding("UTF-8");
			filter.setForceEncoding(true);
			http.addFilterBefore(filter,CsrfFilter.class);
			
		}
	}
	
	@Bean
	public RememberMeServices rememberMeServices() throws Exception
	{
		// Key must be equal to rememberMe().key()
		TokenBasedRememberMeServices rememberMeServices=new TokenBasedRememberMeServices("your_key",userDetailsService);
		rememberMeServices.setCookieName("remember_me_cookie");
		rememberMeServices.setParameter("remember_me_checkbox");
		rememberMeServices.setTokenValiditySeconds(2678400); // 1month
		return rememberMeServices;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() throws Exception
	{
		Map encoders=new HashMap<>();
		encoders.put("bcrypt",new BCryptPasswordEncoder());
		encoders.put("noop",NoOpPasswordEncoder.getInstance());
		encoders.put("pbkdf2",new Pbkdf2PasswordEncoder());
		encoders.put("scrypt",new SCryptPasswordEncoder());
		encoders.put("sha256",new StandardPasswordEncoder());
		// return new BCryptPasswordEncoder();
		PasswordEncoder passwordEncoder=new DelegatingPasswordEncoder("bcrypt",encoders);
		return passwordEncoder;
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
		LOGGER.info("@@@ SecurityConfiguration class init completed: "+sessionRegistry().getAllPrincipals().size());
	}
	
	@Bean
	public SessionRegistry sessionRegistry()
	{
		return new SessionRegistryImpl();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher()
	{
		return new HttpSessionEventPublisher();
	}
	
	// @Bean
	// public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher()
	// {
	// return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	// }
}
