package com.abas.mobile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import com.abas.mobile.model.MessageInfo;

@Service
public class UpdateSettingsService
{
	@Autowired
	private MessageSource messageSource;
	
	public MessageInfo update(String abas_edp_password,
	                          String abas_edp_port,
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
		if(abas_edp_password.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.edppassword.empty",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(false);
		}
		else if(abas_edp_port.equals(""))
		{
			result.setMessage(messageSource.getMessage("admin.settings.message.edpport.empty",new Object[0],LocaleContextHolder.getLocale()));
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
			result.setMessage(messageSource.getMessage("admin.settings.message.updated",new Object[0],LocaleContextHolder.getLocale()));
			result.setStatus(true);
		}
		return result;
	}
}
