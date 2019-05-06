package com.abas.mobile;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="abas")
public class SpringBootAppProperties
{
	private String edpPassword;
	private int edpPort;
	private String serverIp;
	private String s3Dir;
	private String baseDir;
	private String mandant;
	private String adminUser;
	private String adminPass;
	private String adminRole;
	private String whUser;
	private String whPass;
	private String whRole;
	private String pdcUser;
	private String pdcPass;
	private String pdcRole;
	
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
	
	public String getAdminUser()
	{
		return adminUser;
	}
	
	public void setAdminUser(String adminUser)
	{
		this.adminUser=adminUser;
	}
	
	public String getAdminPass()
	{
		return adminPass;
	}
	
	public void setAdminPass(String adminPass)
	{
		this.adminPass=adminPass;
	}
	
	public String getAdminRole()
	{
		return adminRole;
	}
	
	public void setAdminRole(String adminRole)
	{
		this.adminRole=adminRole;
	}
	
	public String getWhUser()
	{
		return whUser;
	}
	
	public void setWhUser(String whUser)
	{
		this.whUser=whUser;
	}
	
	public String getWhPass()
	{
		return whPass;
	}
	
	public void setWhPass(String whPass)
	{
		this.whPass=whPass;
	}
	
	public String getWhRole()
	{
		return whRole;
	}
	
	public void setWhRole(String whRole)
	{
		this.whRole=whRole;
	}
	
	public String getPdcUser()
	{
		return pdcUser;
	}
	
	public void setPdcUser(String pdcUser)
	{
		this.pdcUser=pdcUser;
	}
	
	public String getPdcPass()
	{
		return pdcPass;
	}
	
	public void setPdcPass(String pdcPass)
	{
		this.pdcPass=pdcPass;
	}
	
	public String getPdcRole()
	{
		return pdcRole;
	}
	
	public void setPdcRole(String pdcRole)
	{
		this.pdcRole=pdcRole;
	}
	
}
