package com.abas.mobile;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

// @EnableJpaAuditing(auditorAwareRef="myWebAppAuditorAware")
@SpringBootApplication
@EnableAutoConfiguration
@ServletComponentScan
@EnableScheduling
public class SpringBootAppStarter
{
	private static ConfigurableApplicationContext context;
	
	public static void main(String[] args) throws Exception
	{
		// SpringApplication.run(SpringBootAppStarter.class,args);
		context=new SpringApplicationBuilder(SpringBootAppStarter.class)
		                                                                .properties("spring.config.name:application,abasconfig,abaslogconfig,abasmobileusers,webserverconfig",
		                                                                            "spring.config.location:classpath:/config/,classpath:/")
		                                                                .build().run(args);
	}
	
	public static void restart()
	{
		ApplicationArguments args=context.getBean(ApplicationArguments.class);
		
		Thread thread=new Thread(()->
		{
			context.close();
			// context=SpringApplication.run(SpringBootAppStarter.class,args.getSourceArgs());
			context=new SpringApplicationBuilder(SpringBootAppStarter.class).properties("spring.config.name:application,abasconfig,abaslogconfig,abasmobileusers,webserverconfig",
			                                                                            "spring.config.location:classpath:/config/,classpath:/")
			                                                                .build().run(args.getSourceArgs());
		});
		
		thread.setDaemon(false);
		thread.start();
	}
}
