package com.abas.mobile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;

public abstract class ReloadableProperties
{
	
	@Autowired
	protected StandardEnvironment environment;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	private long lastModTime=0L;
	private PropertySource<?> appConfigPropertySource=null;
	private Path configPath;
	
	@PostConstruct
	private void stopIfProblemsCreatingContext()
	{
		LOGGER.info("@@@ Releadeble properties is starting");
		System.out.println("###starting###");
		MutablePropertySources propertySources=environment.getPropertySources();
		Optional<PropertySource<?>> appConfigPsOp=
		StreamSupport.stream(propertySources.spliterator(),false)
		             .filter(ps->ps.getName().contains("abasconfig.properties"))
		             .findFirst();
		if(!appConfigPsOp.isPresent())
		{
			// this will stop context initialization
			// (i.e. kill the spring boot program before it initializes)
			throw new RuntimeException("Unable to find property Source as file");
		}
		appConfigPropertySource=appConfigPsOp.get();
		try
		{
			configPath=Paths.get(resourceLoader.getResource("classpath:/config/abasconfig.properties").getFile().getPath());
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Scheduled(fixedRate=5000)
	private void reload() throws IOException
	{
		LOGGER.info("@@@ checking abasconfig.properties");
		// Path configPath=Paths.get("./src/main/resources/config/abasconfig.properties");
		// Path configPath=Paths.get(new ClassPathResource("./config/abasconfig.properties").getPath());
		long currentModTs=Files.getLastModifiedTime(configPath).toMillis();
		if(currentModTs>lastModTime)
		{
			LOGGER.info("@@@ reloading abasconfig.properties \t>"+lastModTime+":"+currentModTs);
			boolean isFirst=lastModTime==0;
			lastModTime=currentModTs;
			Properties properties=new Properties();
			InputStream inputStream=Files.newInputStream(configPath);
			properties.load(inputStream);
			environment.getPropertySources()
			           .replace(
			                    appConfigPropertySource.getName(),
			                    new PropertiesPropertySource(
			                                                 appConfigPropertySource.getName(),
			                                                 properties));
			LOGGER.info("@@@ reloaded abasconfig.properties");
			propertiesReloaded();
		}
	}
	
	protected abstract void propertiesReloaded();
	
	@Autowired
	private RestartEndpoint restartEndpoint;
}
