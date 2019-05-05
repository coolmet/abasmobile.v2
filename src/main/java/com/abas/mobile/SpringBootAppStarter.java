package com.abas.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing(auditorAwareRef="myWebAppAuditorAware")
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties(value=SpringBootAppProperties.class)
@ServletComponentScan
public class SpringBootAppStarter
{
	public static void main(String[] args) throws Exception
	{
		// SpringApplication.run(SpringBootAppStarter.class,args);
		new SpringApplicationBuilder(SpringBootAppStarter.class)
		                                                        .properties("spring.config.name:application,abasconfig,abaslogconfig,webserverconfig",
		                                                                    "spring.config.location:classpath:/config/,classpath:/")
		                                                        .build().run(args);
	}
}
 