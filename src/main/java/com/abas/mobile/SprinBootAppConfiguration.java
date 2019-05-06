package com.abas.mobile;

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
		LOGGER.debug("@@@ abas.edp.password\t\t= "+springBootAppProperties.getEdpPassword());
		LOGGER.debug("@@@ abas.admin.user\t\t= "+springBootAppProperties.getAdminUser());
		LOGGER.debug("@@@ abas.admin.pass\t\t= "+springBootAppProperties.getAdminPass());
		LOGGER.debug("@@@ abas.admin.role\t\t= "+springBootAppProperties.getAdminRole());
		LOGGER.debug("@@@ abas.wh.user\t\t= "+springBootAppProperties.getWhUser());
		LOGGER.debug("@@@ abas.wh.pass\t\t= "+springBootAppProperties.getWhPass());
		LOGGER.debug("@@@ abas.wh.role\t\t= "+springBootAppProperties.getWhRole());
		LOGGER.debug("@@@ abas.pdc.user\t\t= "+springBootAppProperties.getPdcUser());
		LOGGER.debug("@@@ abas.pdc.pass\t\t= "+springBootAppProperties.getPdcPass());
		LOGGER.debug("@@@ abas.pdc.role\t\t= "+springBootAppProperties.getPdcRole());
		//
		LOGGER.info("@@@ abas.edp.password\t= "+"*****");
		LOGGER.info("@@@ abas.edp.port\t\t= "+springBootAppProperties.getEdpPort());
		LOGGER.info("@@@ abas.server.ip\t\t= "+springBootAppProperties.getServerIp());
		LOGGER.info("@@@ abas.s3.dir\t\t= "+springBootAppProperties.getS3Dir());
		LOGGER.info("@@@ abas.base.dir\t\t= "+springBootAppProperties.getBaseDir());
		LOGGER.info("@@@ abas.mandant\t\t= "+springBootAppProperties.getMandant());
		System.out.println("\u001b[40m \u001B[38;5;220m @@@@@: __________________________________ ");
		System.out.println(AnsiOutput.toString(AnsiColor.DEFAULT,AnsiColor.BRIGHT_BLUE,"________________________________________",AnsiColor.DEFAULT));
	}
}
