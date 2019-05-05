package com.abas.mobile;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class SprinBootAppConfiguration
{
	@Autowired
	private SpringBootAppProperties springBootAppProperties;
	
	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@PostConstruct
	public void init()
	{
		requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
		
		System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE,"________________________________________\n",AnsiColor.DEFAULT));
		System.out.println("\u001b[40m \u001B[38;5;220m @@@@@: SprinBootAppConfiguration->init()-> "+AnsiOutput.toString(AnsiColor.DEFAULT));
		LOGGER.info("abas.edp-password\t= "+"*****");
		LOGGER.debug("abas.edp-password\t= "+springBootAppProperties.getEdpPassword());
		LOGGER.info("abas.edp-port\t= "+springBootAppProperties.getEdpPort());
		LOGGER.info("abas.server-ip\t= "+springBootAppProperties.getServerIp());
		LOGGER.info("abas.s3-dir\t\t= "+springBootAppProperties.getS3Dir());
		LOGGER.info("abas.base-dir\t= "+springBootAppProperties.getBaseDir());
		LOGGER.info("abas.mandant\t\t= "+springBootAppProperties.getMandant());
		System.out.println("\u001b[40m \u001B[38;5;220m @@@@@: __________________________________ ");
		System.out.println(AnsiOutput.toString(AnsiColor.DEFAULT,AnsiColor.BRIGHT_BLUE,"________________________________________",AnsiColor.DEFAULT));
	}
}
