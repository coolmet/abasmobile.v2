package com.abas.mobile;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.abas.mobile.service.SessionRegistry;

@Component
public class SessionListener implements HttpSessionListener
{
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Override
	public void sessionCreated(HttpSessionEvent se)
	{
		synchronized(this)
		{
			// sessionRegistry.registerNewSession(se.getSession().getId(),se.getSession());
			sessionRegistry.addSession(se.getSession());
		}
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se)
	{
		synchronized(this)
		{
			// sessionRegistry.removeSessionInformation(se.getSession().getId());
			sessionRegistry.removeSession(se.getSession());
		}
	}
	
}
