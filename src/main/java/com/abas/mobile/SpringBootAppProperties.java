package com.abas.mobile;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@ConfigurationProperties(prefix="abas")
public class SpringBootAppProperties
{
	private String edpPassword;
	private int edpPort;
	private String serverIp;
	private String s3Dir;
	private String baseDir;
	private String mandant;
	
	public String getEdpPassword()
	{
		return edpPassword;
	}
	
	public void setEdpPassword(String edpPassword)
	{
		this.edpPassword=edpPassword;
	}
	
	public int getEdpPort()
	{
		return edpPort;
	}
	
	public void setEdpPort(int edpPort)
	{
		this.edpPort=edpPort;
	}
	
	public String getServerIp()
	{
		return serverIp;
	}
	
	public void setServerIp(String serverIp)
	{
		this.serverIp=serverIp;
	}
	
	public String getS3Dir()
	{
		return s3Dir;
	}
	
	public void setS3Dir(String s3Dir)
	{
		this.s3Dir=s3Dir;
	}
	
	public String getBaseDir()
	{
		return baseDir;
	}
	
	public void setBaseDir(String baseDir)
	{
		this.baseDir=baseDir;
	}
	
	public String getMandant()
	{
		return mandant;
	}
	
	public void setMandant(String mandant)
	{
		this.mandant=mandant;
	}
	
}
