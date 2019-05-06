package com.abas.mobile;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.abas.mobile.service.AbasSessionRegistry;

@Component
@WebListener
public class AbasSessionListener implements HttpSessionListener
{
	
	@Autowired
	private AbasSessionRegistry abasSessionRegistry;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se)
	{
		synchronized(this)
		{
			LOGGER.info("@@@ +++ SESSION IS CREATED: "+se.getSession().getId()+"\t"+se.getSession());
			if(!abasSessionRegistry.getSessions().contains(se.getSession()))
			{
				// sessionRegistry.registerNewSession(se.getSession().getId(),se.getSession());
				abasSessionRegistry.addSession(se.getSession());
			}
		}
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se)
	{
		synchronized(this)
		{
			LOGGER.info("@@@ --- SESSION IS DESTROYED: "+se.getSession().getId()+"\t"+se.getSession());
			if(!abasSessionRegistry.getSessions().contains(se.getSession()))
			{
				// sessionRegistry.removeSessionInformation(se.getSession().getId());
				abasSessionRegistry.removeSession(se.getSession());
			}
		}
	}
	
}
