package com.abas.mobile.service;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import com.abas.mobile.model.MessageInfo;

@Service
public class UpdateSettingsService
{
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
			result.setMessage("Şifre boş olamaz ...");
			result.setStatus(false);
		}
		else if(abas_edp_port.equals(""))
		{
			result.setMessage("Port boş olamaz ...");
			result.setStatus(false);
		}
		else if(abas_edp_serverip.equals(""))
		{
			result.setMessage("Server ip boş olamaz ...");
			result.setStatus(false);
		}
		else if(abas_s3_dir.equals(""))
		{
			result.setMessage("S3 dir boş olamaz ...");
			result.setStatus(false);
		}
		else if(abas_s3_basedir.equals(""))
		{
			result.setMessage("S3 basedir boş olamaz ...");
			result.setStatus(false);
		}
		else if(abas_s3_mandant.equals(""))
		{
			result.setMessage("S3 mandant boş olamaz ...");
			result.setStatus(false);
		}
		else if(spring_mvc_locale.equals(""))
		{
			result.setMessage("Spring mvc locale boş olamaz ...");
			result.setStatus(false);
		}
		else if(server_port.equals(""))
		{
			result.setMessage("Server port boş olamaz ...");
			result.setStatus(false);
		}
		else if(server_connection_timeout.equals(""))
		{
			result.setMessage("Server connection timeout boş olamaz ...");
			result.setStatus(false);
		}
		else if(server_servlet_session_timeout.equals(""))
		{
			result.setMessage("Server servlet session timeout boş olamaz ...");
			result.setStatus(false);
		}
		//
		result.setMessage("Ayarlar güncellendi ...");
		result.setStatus(true);
		return result;
	}
}
