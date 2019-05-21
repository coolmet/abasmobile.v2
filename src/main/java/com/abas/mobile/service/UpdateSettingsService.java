package com.abas.mobile.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeSet;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.util.DefaultPropertiesPersister;
import com.abas.mobile.SecurityConfiguration;
import com.abas.mobile.SprinBootAppConfiguration;
import com.abas.mobile.SpringBootAppStarter;
import com.abas.mobile.model.MessageInfo;

@Service
public class UpdateSettingsService
{
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private SecurityConfiguration securityConfiguration;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	protected StandardEnvironment environment;
	private PropertySource<?> appConfigPropertySource=null;
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	public MessageInfo update(String abas_edp_password,
	                          String abas_edp_port,
	                          String abas_edp_lang,
	                          String abas_edp_fopmode,
	                          String abas_edp_serverip,
	                          String abas_s3_dir,
	                          String abas_s3_basedir,
	                          String abas_s3_mandant,
	                          String spring_mvc_locale,
	                          String server_port,
	                          String server_connection_timeout,
	                          String server_servlet_session_timeout)
	{
		MessageInfo result=new MessageInfo();
		if(abas_edp_password.equals(null)||abas_edp_password.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.edppassword.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_edp_port.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.edpport.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_edp_lang.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.lang.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_edp_fopmode.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.fopmode.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_edp_serverip.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.serverip.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_s3_dir.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.s3dir.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_s3_basedir.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.basedir.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_s3_mandant.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.mandant.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(spring_mvc_locale.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.locale.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(server_port.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.serverport.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(server_connection_timeout.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.connectiontimeout.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(server_servlet_session_timeout.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.sessiontimeout.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else
		{
			try
			{
				
				Properties props=new Properties()
				{
					@Override
					public synchronized Enumeration<Object> keys()
					{
						return Collections.enumeration(new TreeSet<Object>(super.keySet()));
					}
				};
				props.setProperty("abas.edp.password",abas_edp_password);
				props.setProperty("abas.edp.port",abas_edp_port);
				props.setProperty("abas.edp.serverip",abas_edp_serverip);
				props.setProperty("abas.edp.lang",abas_edp_lang);
				props.setProperty("abas.edp.fopmode",abas_edp_fopmode);
				props.setProperty("abas.s3.dir",abas_s3_dir);
				props.setProperty("abas.s3.basedir",abas_s3_basedir);
				props.setProperty("abas.s3.mandant",abas_s3_mandant);
				props.setProperty("#############","---");
				props.setProperty("spring.mvc.locale",spring_mvc_locale);
				props.setProperty("server.port",server_port);
				props.setProperty("server.connection-timeout",server_connection_timeout);
				props.setProperty("server.servlet.session.timeout",server_servlet_session_timeout);
				File f=null;
				
				try
				{
					f=resourceLoader.getResource("classpath:./config/abasconfig.properties").getFile();// springtools
				}
				catch(Exception rt)
				{
					try
					{
						f=new File("./config/abasconfig.properties");// jar
					}
					catch(Exception rtt)
					{
					}
				}
				
				OutputStream out=new FileOutputStream(f);
				DefaultPropertiesPersister p=new DefaultPropertiesPersister();
				p.store(props,out,"Abas Mobile Settings");				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(e.getLocalizedMessage());
			}
			result.setMessage(messageSource.getMessage("admin.settings.message.updated",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(true);
			
		}
		return result;
	}
	
	@Autowired
	private RestartEndpoint restartEndpoint;
	
	@Autowired
	private RefreshEndpoint refreshEndpoint;
	
}
