package com.abas.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class HttpSessionConfig
{
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	private static final Map<String,HttpSession> sessions=new HashMap<>();
		
	public List<HttpSession> getActiveSessions()
	{
		return new ArrayList<>(sessions.values());
	}
			
	@Bean
	public HttpSessionListener httpSessionListener()
	{
		return new HttpSessionListener()
		{
			@Override
			public void sessionCreated(HttpSessionEvent hse)
			{
				LOGGER.info("CreatedSessionID: "+hse.getSession().getId());
				sessions.put(hse.getSession().getId(),hse.getSession());				
			}
			
			@Override
			public void sessionDestroyed(HttpSessionEvent hse)
			{
				LOGGER.info("DestroyedSessionID: "+hse.getSession().getId());
				sessions.remove(hse.getSession().getId());				
			}
		};
	}
}
