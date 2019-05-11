package com.abas.mobile;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;

@ConfigurationProperties(prefix="server")
public class ConfigPropertiesServer
{
	@Min(1025)
	@Max(65536)
	private int port;
	@NotBlank
	private String connectionTimeout;
	@NotBlank
	private Servlet servlet=new Servlet();
	
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port=port;
	}
	
	public String getConnectionTimeout()
	{
		return connectionTimeout;
	}
	
	public void setConnectionTimeout(String connectionTimeout)
	{
		this.connectionTimeout=connectionTimeout;
	}
	
	public Servlet getServlet()
	{
		return servlet;
	}
	
	public void setServlet(Servlet servlet)
	{
		this.servlet=servlet;
	}
	
	public static class Servlet
	{
		@NotBlank
		private Session session;
		
		public Session getSession()
		{
			return session;
		}
		
		public void setSession(Session session)
		{
			this.session=session;
		}
		
		public static class Session
		{
			@NotBlank
			private String timeout;
			
			public String getTimeout()
			{
				return timeout;
			}
			
			public void setTimeout(String timeout)
			{
				this.timeout=timeout;
			}
			
		}
		
	}
	
}
