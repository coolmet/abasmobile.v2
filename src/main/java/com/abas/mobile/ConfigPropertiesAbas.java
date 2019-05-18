package com.abas.mobile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.annotation.Order;

@ConfigurationProperties(prefix="abas")
public class ConfigPropertiesAbas
{
	@NotBlank
	private Edp edp;
	@NotBlank
	private S3 s3;
	
	public Edp getEdp()
	{
		return edp;
	}
	
	public void setEdp(Edp edp)
	{
		this.edp=edp;
	}
	
	public S3 getS3()
	{
		return s3;
	}
	
	public void setS3(S3 s3)
	{
		this.s3=s3;
	}
	
	public static class S3
	{
		@NotBlank
		private String dir;
		@NotBlank
		private String baseDir;
		@NotBlank
		private String mandant;
		
		public String getDir()
		{
			return dir;
		}
		
		public void setDir(String dir)
		{
			this.dir=dir;
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
	
	public static class Edp
	{
		@NotBlank
		private String serverip;
		@NotBlank
		private String password;
		@Min(1025)
		@Max(65536)
		private int port;
		@NotBlank
		private String lang;
		@NotBlank
		private boolean fopmode;
		
		public String getServerip()
		{
			return serverip;
		}
		
		public void setServerip(String serverip)
		{
			this.serverip=serverip;
		}
		
		public String getPassword()
		{
			return password;
		}
		
		public void setPassword(String password)
		{
			this.password=password;
		}
		
		public int getPort()
		{
			return port;
		}
		
		public void setPort(int port)
		{
			this.port=port;
		}
		
		public String getLang()
		{
			return lang;
		}
		
		public void setLang(String lang)
		{
			this.lang=lang;
		}
		
		public boolean isFopmode()
		{
			return fopmode;
		}
		
		public void setFopmode(boolean fopmode)
		{
			this.fopmode=fopmode;
		}
		
	}
	
}
