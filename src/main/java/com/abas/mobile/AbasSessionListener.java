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
			//while(true)
			{
				try
				{
					Thread.sleep(50);
					abasSessionRegistry.getSessions();
				}
				catch(Exception e)
				{
				}
			}
			// sessionRegistry.registerNewSession(se.getSession().getId(),se.getSession());
			LOGGER.info("@@@ +++ SESSION IS CREATED: "+se.getSession());
			LOGGER.info("@@@ +++ SESSION IS CREATED: "+se.getSession().getId());
			LOGGER.info("@@@ +++ SESSION IS CREATED: "+abasSessionRegistry);
			
			abasSessionRegistry.addSession(se.getSession());
		}
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se)
	{
		synchronized(this)
		{
			// sessionRegistry.removeSessionInformation(se.getSession().getId());
			LOGGER.info("@@@ --- SESSION IS DESTROYED: "+se.getSession());
			LOGGER.info("@@@ --- SESSION IS DESTROYED: "+se.getSession().getId());
			abasSessionRegistry.removeSession(se.getSession());
			
		}
	}
	
}
