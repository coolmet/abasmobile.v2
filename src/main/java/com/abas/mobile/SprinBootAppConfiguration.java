package com.abas.mobile;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import com.abas.mobile.model.AbasUserDetails;

@Configuration
public class SprinBootAppConfiguration
{
	@Autowired
	private AbasConfigProperties abasConfig;
	
	@Autowired
	private AbasMobileUsersProperties abasMobileUsers;
	
	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@PostConstruct
	public void init()
	{
		requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
		System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE,"________________________________________\n",AnsiColor.DEFAULT));
		System.out.println("\u001b[40m \u001B[38;5;220m @@@@@: SprinBootAppConfiguration->init()-> "+AnsiOutput.toString(AnsiColor.DEFAULT));
		LOGGER.debug("@@@ abas.edp.password\t\t= "+abasConfig.getEdpPassword());
		for(AbasUserDetails user:abasMobileUsers.getUsers())
		{
			LOGGER.debug("@@@ abas.users\t\t= "+user.getUsername()+":"+user.getPassword()+":"+Arrays.toString(user.getRoles()));
		}
		//
		LOGGER.info("@@@ abas.edp.password\t= "+"*****");
		LOGGER.info("@@@ abas.edp.port\t\t= "+abasConfig.getEdpPort());
		LOGGER.info("@@@ abas.server.ip\t\t= "+abasConfig.getServerIp());
		LOGGER.info("@@@ abas.s3.dir\t\t= "+abasConfig.getS3Dir());
		LOGGER.info("@@@ abas.base.dir\t\t= "+abasConfig.getBaseDir());
		LOGGER.info("@@@ abas.mandant\t\t= "+abasConfig.getMandant());
		System.out.println("\u001b[40m \u001B[38;5;220m @@@@@: __________________________________ ");
		System.out.println(AnsiOutput.toString(AnsiColor.DEFAULT,AnsiColor.BRIGHT_BLUE,"________________________________________",AnsiColor.DEFAULT));
	}
}
