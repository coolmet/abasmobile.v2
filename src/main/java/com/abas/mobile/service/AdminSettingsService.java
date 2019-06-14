package com.abas.mobile.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.DefaultPropertiesPersister;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import com.abas.mobile.AbasUserProperties;
import com.abas.mobile.SprinBootAppConfiguration;
import com.abas.mobile.model.AbasUserDetailsModel;
import com.abas.mobile.model.AbasUserPropertiesModel;
import com.abas.mobile.model.MessageInfo;
import de.abas.ceks.jedp.EDPFactory;
import de.abas.ceks.jedp.EDPMessage;
import de.abas.ceks.jedp.EDPMessageListener;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPSession;

@Service
public class AdminSettingsService
{
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	protected StandardEnvironment environment;
	
	@Autowired
	protected LanguageService languageService;
	
	@Autowired
	AbasUserProperties abasUserProperties;
	
	@Autowired
	AbasMobileUtils abasMobileUtils;
	
	@Autowired
	EdpSessionService edpSessionService;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public MessageInfo updateSettings(String abas_edp_password,
	                                  String abas_edp_port,
	                                  String abas_edp_lang,
	                                  String abas_edp_fopmode,
	                                  String abas_edp_fl,
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
			result.setMessage(messageSource.getMessage("admin.settings.message.edplang.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_edp_fopmode.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.edpfopmode.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_edp_fl.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.edpfl.empty",new Object[0],LocaleContextHolder.getLocale()));
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
				props.setProperty("abas.edp.fl",abas_edp_fl);
				props.setProperty("abas.s3.dir",abas_s3_dir);
				props.setProperty("abas.s3.basedir",abas_s3_basedir);
				props.setProperty("abas.s3.mandant",abas_s3_mandant);
				props.setProperty("#############","---");
				props.setProperty("spring.mvc.locale",spring_mvc_locale);
				props.setProperty("server.port",server_port);
				props.setProperty("server.connection-timeout",server_connection_timeout);
				props.setProperty("server.servlet.session.timeout",server_servlet_session_timeout);
				File f=Arrays.stream(environment.getActiveProfiles()).anyMatch(env->(env.equalsIgnoreCase("prod")))
				?new File("./config/abasconfig.properties")// jar
				:new File("./src/main/resources/config/abasconfig.properties");// springtools
				
				// LOGGER.info("@@>>>>>>"+resourceLoader.getResource("classpath:./config/abasconfig.properties").getFile().getAbsolutePath());
				// LOGGER.info("@@>>>>>>"+resourceLoader.getResource("./config/abasconfig.properties").getFile().getAbsolutePath());
				// LOGGER.info("@@>>>>>>"+resourceLoader.getResource("file:./config/abasconfig.properties").getFile().getAbsolutePath());
				// LOGGER.info("@@>>>>>>"+getClass().getClassLoader().getResource("./config/abasconfig.properties").getFile().toString());
				// LOGGER.info("@@>>>>>>"+new File("./config/abasconfig.properties").getAbsolutePath());
				// LOGGER.info("@@>>>>>>"+new File("./src/main/resources/config/abasconfig.properties").getAbsolutePath());
				
				OutputStream out=new FileOutputStream(f);
				DefaultPropertiesPersister p=new DefaultPropertiesPersister();
				p.store(props,out,"Abas Mobile Settings");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				LOGGER.info(abasMobileUtils.exceptionMessage(e));
				LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
			}
			result.setMessage(messageSource.getMessage("admin.settings.message.updated",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(true);
			
		}
		return result;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public MessageInfo testConnection(String abas_edp_password,
	                                  String abas_edp_port,
	                                  String abas_edp_lang,
	                                  String abas_edp_fopmode,
	                                  String abas_edp_fl,
	                                  String abas_edp_serverip,
	                                  String abas_s3_mandant)
	{
		MessageInfo result=new MessageInfo();
		//
		ArrayList<EDPMessage> edpMessages=new ArrayList<EDPMessage>();
		//
		EDPSession session=null;
		try
		{
			if(session==null)
			{
				
				session=EDPFactory.createEDPSession();
				session.setConnectTimeout(5000);
			}
			if(!session.isConnected())
			{
				// String host, int port, String mandant, String username, String passwort, boolean forceLicence, String appName
				// session.beginSessionWebUser(abas_edp_serverip,Integer.parseInt(abas_edp_port),abas_s3_mandant,"admin2","admin2",Boolean.parseBoolean(abas_edp_fl),"AbasMobile_TestConnection");
				// String host, int port, String mandant, String passwort, String appName, boolean readOnly
				session.beginSession(abas_edp_serverip,Integer.parseInt(abas_edp_port),abas_s3_mandant,abas_edp_password,"AbasMobile_TestConnection",true);
				//
				session.setEKSLanguage(abas_edp_lang);
				session.resetErrorMessageListener();
				session.setErrorMessageListener(edpSessionService.edpMessageListener(edpMessages));
				session.setStatusMessageListener(edpSessionService.edpMessageListener(edpMessages));
			}
			if(session.isConnected())
			{
				result.setStatus(true);
				result.setMessage(messageSource.getMessage("admin.settings.message.connectionsuccessfully",new Object[0],LocaleContextHolder.getLocale())+" "+session.getABASVersion()+" "+session.getOperatorCode());
				session.endSession();
			}
			else
			{
				result.setStatus(false);
				result.setMessage(messageSource.getMessage("admin.settings.message.connectionfailed",new Object[0],LocaleContextHolder.getLocale()));
			}
		}
		catch(Exception rt)
		{
			result.setStatus(false);
			result.setMessage(rt.getLocalizedMessage());
		}
		finally
		{
			if(session!=null&&session.isConnected())
			{
				session.endSession();
			}
		}
		for(EDPMessage edpMessage:edpMessages)
		{
			LOGGER.info("@@@ "+edpMessage.getMessageCategory()+":"+edpMessage.getMessageType()+":"+edpMessage.getMessageText());
		}
		return result;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public MessageInfo syncPasswords(String abas_edp_password,
	                                 String abas_edp_port,
	                                 String abas_edp_lang,
	                                 String abas_edp_fopmode,
	                                 String abas_edp_fl,
	                                 String abas_edp_serverip,
	                                 String abas_s3_mandant)
	{
		MessageInfo result=new MessageInfo();
		// **********************
		AbasUserPropertiesModel model=new AbasUserPropertiesModel();
		for(AbasUserDetailsModel adminuser:abasUserProperties.getAdmins())
		{
			model.addAdmins(adminuser.getUsername(),adminuser.getPassword(),adminuser.getRoles()[0]);
		}
		//
		ArrayList<EDPMessage> edpMessages=new ArrayList<EDPMessage>();
		//
		EDPSession session=null;
		EDPQuery edpQuery=null;
		try
		{
			if(session==null)
			{
				
				session=EDPFactory.createEDPSession();
				session.setConnectTimeout(5000);
			}
			if(!session.isConnected())
			{
				// String host, int port, String mandant, String username, String passwort, boolean forceLicence, String appName
				// session.beginSessionWebUser(abas_edp_serverip,Integer.parseInt(abas_edp_port),abas_s3_mandant,"admin2","admin2",Boolean.parseBoolean(abas_edp_fl),"AbasMobile_TestConnection");
				// String host, int port, String mandant, String passwort, String appName, boolean readOnly
				session.beginSession(abas_edp_serverip,Integer.parseInt(abas_edp_port),abas_s3_mandant,abas_edp_password,"AbasMobile_SynchronizePasswords",true);
				//
				session.setEKSLanguage(abas_edp_lang);
				session.resetErrorMessageListener();
				session.setErrorMessageListener(edpSessionService.edpMessageListener(edpMessages));
				session.setStatusMessageListener(edpSessionService.edpMessageListener(edpMessages));
				session.setBoolMode(EDPSession.BOOLMODE_NUM);
			}
			if(session.isConnected())
			{
				if(edpQuery==null)
				{
					edpQuery=session.createQuery();
				}
				// edpQuery.startQuery("93:1","","sperre=false",false,9,true,true,"ymaiswh;ymaispdc;ymaisshpm;login;ymapass".split(";"),0,0);
				edpQuery.startQuery("93:1","","sperre=false",false,"ymaiswh,ymaispdc,ymaisshpm,login,ymapass");
				if(edpQuery!=null)
				{
					while(edpQuery.getNextRecord())
					{
						if(edpQuery.getField("ymaiswh").equals("1"))
						{
							model.addWhs(edpQuery.getField("login"),edpQuery.getField(4),"USER_WAREHOUSE");
						}
						else if(edpQuery.getField("ymaispdc").equals("1"))
						{
							model.addPdcs(edpQuery.getField("login"),edpQuery.getField(4),"USER_PDC");
						}
						else if(edpQuery.getField("ymaisshpm").equals("1"))
						{
							model.addShpms(edpQuery.getField("login"),edpQuery.getField(4),"USER_SHIPMENT");
						}
					}
				}
				edpQuery.breakQuery();
				edpQuery=null;
				//
				result.setStatus(true);
				result.setMessage(messageSource.getMessage("admin.settings.message.connectionsuccessfully",new Object[0],LocaleContextHolder.getLocale())+" "+session.getABASVersion()+" "+session.getOperatorCode());
				session.endSession();
			}
			else
			{
				result.setStatus(false);
				result.setMessage(messageSource.getMessage("admin.settings.message.connectionfailed",new Object[0],LocaleContextHolder.getLocale()));
			}
		}
		catch(Exception rt)
		{
			result.setStatus(false);
			result.setMessage(rt.getLocalizedMessage());
		}
		finally
		{
			if(session!=null&&session.isConnected())
			{
				if(edpQuery!=null)
				{
					edpQuery.breakQuery();
					edpQuery=null;
				}
				session.endSession();
			}
		}
		for(EDPMessage edpMessage:edpMessages)
		{
			LOGGER.debug("@@@ "+edpMessage.getMessageCategory()+":"+edpMessage.getMessageType()+":"+edpMessage.getMessageText());
		}
		//
		Map<String,Object> users=new LinkedHashMap<String,Object>();
		users.put("users",model);
		DumperOptions options=new DumperOptions();
		options.setExplicitStart(false);
		options.setIndent(2);
		Representer representer=new Representer()
		{
			@Override
			protected NodeTuple representJavaBeanProperty(Object javaBean,Property property,Object propertyValue,Tag customTag)
			{
				// if value of property is null, ignore it.
				if(int.class.equals(property.getType()))
				{
					return null;
				}
				else
				{
					return super.representJavaBeanProperty(javaBean,property,propertyValue,customTag);
				}
			}
		};
		representer.addClassTag(com.abas.mobile.model.AbasUserPropertiesModel.class,Tag.MAP);
		Yaml yaml=new Yaml(representer,options);
		String output=yaml.dump(users);
		LOGGER.debug("@@@"+output);
		//
		try
		{
			File f=Arrays.stream(environment.getActiveProfiles()).anyMatch(env->(env.equalsIgnoreCase("prod")))
			?new File("./config/abasmobileusers.yml")// jar
			:new File("./src/main/resources/config/abasmobileusers.yml");// springtools
			FileOutputStream fileOutputStream=new FileOutputStream(f);
			fileOutputStream.write(output.getBytes());
			fileOutputStream.close();
			result.setStatus(true);
			result.setMessage(messageSource.getMessage("admin.settings.message.passwordsupdated",new Object[0],LocaleContextHolder.getLocale()));
		}
		catch(Exception rt)
		{
			result.setStatus(false);
			result.setMessage(messageSource.getMessage("admin.settings.message.writetofileerror",new Object[0],LocaleContextHolder.getLocale()));
		}
		return result;
	}
	
	
}
