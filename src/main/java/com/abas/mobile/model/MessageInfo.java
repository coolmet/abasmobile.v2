package com.abas.mobile.model;

public class MessageInfo
{
	String message;
	boolean status;
	String data1;
	String data2;
	
	public MessageInfo()
	{
		super();
		message="";
		status=false;
	}
	
	public MessageInfo(String message,boolean status)
	{
		super();
		this.message=message;
		this.status=status;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message=message;
	}
	
	public boolean isStatus()
	{
		return status;
	}
	
	public void setStatus(boolean status)
	{
		this.status=status;
	}
	
	public String getData1()
	{
		return data1;
	}
	
	public void setData1(String data1)
	{
		this.data1=data1;
	}
	
	public String getData2()
	{
		return data2;
	}
	
	public void setData2(String data2)
	{
		this.data2=data2;
	}
	
}
