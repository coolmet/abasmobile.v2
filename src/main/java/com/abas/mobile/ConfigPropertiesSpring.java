package com.abas.mobile;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;

@ConfigurationProperties(prefix="spring")
public class ConfigPropertiesSpring
{
	@NotBlank
	private Mvc mvc=new Mvc();
	
	public Mvc getMvc()
	{
		return mvc;
	}
	
	public void setMvc(Mvc mvc)
	{
		this.mvc=mvc;
	}
	
	public static class Mvc
	{
		@NotBlank
		private String locale;
		
		public String getLocale()
		{
			return locale;
		}
		
		public void setLocale(String locale)
		{
			this.locale=locale;
		}
		
	}
	
}
