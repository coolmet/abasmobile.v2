package com.abas.mobile.service;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abas.mobile.ConfigPropertiesAbas;
import com.abas.mobile.SprinBootAppConfiguration;
import de.abas.ceks.jedp.EDPFactory;
import de.abas.ceks.jedp.EDPMessage;
import de.abas.ceks.jedp.EDPMessageListener;
import de.abas.ceks.jedp.EDPSession;

@Service
// @Scope(value="session")
public class EDPSessionService
{
	@Autowired
	AbasMobileUtils abasMobileUtils;
	
	@Autowired
	private ConfigPropertiesAbas configAbas;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	EDPSession SESSION=null;
	
	ArrayList<EDPMessage> edpMessages;
	
	public int getAbasVersion()
	{
		try
		{
			return Integer.parseInt(SESSION.getABASVersionNumber().substring(0,4));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.info(abasMobileUtils.exceptionMessage(e));
			LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
		}
		return 0;
	}
	
	public boolean EDPSESSION_START()
	{
		edpMessages=new ArrayList<EDPMessage>();
		int count=0;
		boolean status=false;
		while(count<5)
		{
			try
			{
				if(SESSION==null)
				{
					SESSION=EDPFactory.createEDPSession();
					SESSION.setConnectTimeout(200);
				}
				if(!SESSION.isConnected())
				{
					SESSION.beginSession(configAbas.getEdp().getServerip(),configAbas.getEdp().getPort(),configAbas.getS3().getMandant(),configAbas.getEdp().getPassword(),"AbasMobile",true);
					SESSION.setEKSLanguage(configAbas.getEdp().getLang());
					SESSION.resetErrorMessageListener();
					SESSION.setErrorMessageListener(this.edpMessageListener(edpMessages));
					SESSION.setStatusMessageListener(this.edpMessageListener(edpMessages));
					SESSION.setBoolMode(EDPSession.BOOLMODE_NUM);
				}
				status=true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				LOGGER.info(abasMobileUtils.exceptionMessage(e));
				LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
			}
			count++;
		}
		return status;
	}
	
	public boolean EDPSESSION_END()
	{
		boolean status=false;
		try
		{
			while(SESSION!=null&&SESSION.isConnected())
			{
				SESSION.endSession();
			}
			status=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.info(abasMobileUtils.exceptionMessage(e));
			LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
		}
		return status;
	}
	
	public EDPMessageListener edpMessageListener(ArrayList<EDPMessage> edpMessage)
	{
		return new EDPMessageListener()
		{
			@Override
			public void receivedMessage(EDPMessage em)
			{
				if(!em.getMessageText().contains("ymapass"))
				{
					edpMessage.add(em);
					try
					{
						throw new Exception("message");
					}
					catch(Exception e)
					{
						e.printStackTrace();
						LOGGER.info(abasMobileUtils.exceptionMessage(e));
						LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
					}
				}
			}
		};
	}
}
