package com.abas.mobile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abas.mobile.SprinBootAppConfiguration;
import com.abas.mobile.model.MessageInfo;
import de.abas.ceks.jedp.EDPQuery;

@Service
public class EDPServiceWH
{
	@Autowired
	EDPSessionService edpSessionService;
	
	@Autowired
	AbasMobileUtils abasMobileUtils;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	public MessageInfo productIsAvailable(String data)
	{
		MessageInfo mi=new MessageInfo();
		if(data.equals(""))
		{
			mi.setMessage("Veri yok ...");
			mi.setStatus(false);
			mi.setData1(data);
			mi.setData2(data);
		}
		else
		{
			if(edpSessionService.EDPSESSION_START())
			{
				EDPQuery edpQuery;
				String chargeField=edpSessionService.getAbasVersion()>=2018?"chpflicht":"charge";
				try
				{
					edpQuery=edpSessionService.SESSION.createQuery();
					edpQuery.startQuery("2:1","",(Character.isDigit(data.charAt(0))?"nummer=":"such=")+data,false,"nummer"+","+chargeField);
					if(edpQuery!=null)
					{
						if(edpQuery.getNextRecord())
						{
							mi.setMessage("Veri bulundu ...");
							mi.setStatus(true);
							mi.setData1(edpQuery.getField("nummer").trim());
							mi.setData2(edpQuery.getField(chargeField).trim());
						}
						else
						{
							mi.setMessage("Veri bulunamadı ...");
							mi.setStatus(false);
							mi.setData1(data);
							mi.setData2(data);
						}
					}
					else
					{
						mi.setMessage("Bağlantı hatası ...");
						mi.setStatus(false);
						mi.setData1(data);
						mi.setData2(data);
					}
					edpQuery.breakQuery();
					edpQuery=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					LOGGER.info(abasMobileUtils.exceptionMessage(e));
					LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
					mi.setMessage(e.getLocalizedMessage());
					mi.setStatus(false);
					mi.setData1(data);
					mi.setData2(data);
				}
			}
			else
			{
				mi.setMessage("Bağlantı hatası ...");
				mi.setStatus(false);
				mi.setData1(data);
				mi.setData2(data);
			}
		}
		return mi;
	}
	
	public MessageInfo locationIsAvailable(String data)
	{
		MessageInfo mi=new MessageInfo();
		if(data.equals(""))
		{
			mi.setMessage("Veri yok ...");
			mi.setStatus(false);
			mi.setData1(data);
			mi.setData2(data);
		}
		else
		{
			if(edpSessionService.EDPSESSION_START())
			{
				EDPQuery edpQuery;
				try
				{
					edpQuery=edpSessionService.SESSION.createQuery();
					edpQuery.startQuery("38:1","",(Character.isDigit(data.charAt(0))?"nummer=":"such=")+data,false,"such");
					if(edpQuery!=null)
					{
						if(edpQuery.getNextRecord())
						{
							mi.setMessage("Veri bulundu ...");
							mi.setStatus(true);
							mi.setData1(edpQuery.getField("such").trim());
							mi.setData2(edpQuery.getField("such").trim());
						}
						else
						{
							mi.setMessage("Veri bulunamadı ...");
							mi.setStatus(false);
							mi.setData1(data);
							mi.setData2(data);
						}
					}
					else
					{
						mi.setMessage("Bağlantı hatası ...");
						mi.setStatus(false);
						mi.setData1(data);
						mi.setData2(data);
					}
					edpQuery.breakQuery();
					edpQuery=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					LOGGER.info(abasMobileUtils.exceptionMessage(e));
					LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
					mi.setMessage(e.getLocalizedMessage());
					mi.setStatus(false);
					mi.setData1(data);
					mi.setData2(data);
				}
			}
			else
			{
				mi.setMessage("Bağlantı hatası ...");
				mi.setStatus(false);
				mi.setData1(data);
				mi.setData2(data);
			}
		}
		return mi;
	}
	
	public MessageInfo chargeIsAvailable(String data,String artikel)
	{
		MessageInfo mi=new MessageInfo();
		if(data.equals(""))
		{
			mi.setMessage("Veri yok ...");
			mi.setStatus(false);
			mi.setData1(data);
			mi.setData2(data);
		}
		else
		{
			if(edpSessionService.EDPSESSION_START())
			{
				EDPQuery edpQuery;
				try
				{
					edpQuery=edpSessionService.SESSION.createQuery();
					edpQuery.startQuery("59:0","",(Character.isDigit(data.charAt(0))?"nummer=":"such=")+data,false,"nummer,artikel^nummer");
					if(edpQuery!=null)
					{
						if(edpQuery.getNextRecord())
						{
							if(edpQuery.getField("artikel^nummer").trim().equals(artikel))
							{
								mi.setMessage("Veri bulundu ...");
								mi.setStatus(true);
								mi.setData1(edpQuery.getField("nummer").trim());
								mi.setData2(edpQuery.getField("nummer").trim());
							}
							else
							{
								mi.setMessage("Ürün lot ile eşleşmiyor ...");
								mi.setStatus(false);
								mi.setData1(edpQuery.getField("nummer").trim());
								mi.setData2(edpQuery.getField("artikel^nummer").trim());
							}
							
						}
						else
						{
							mi.setMessage("Veri bulunamadı ...");
							mi.setStatus(false);
							mi.setData1(data);
							mi.setData2(data);
						}
					}
					else
					{
						mi.setMessage("Bağlantı hatası ...");
						mi.setStatus(false);
						mi.setData1(data);
						mi.setData2(data);
					}
					edpQuery.breakQuery();
					edpQuery=null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					LOGGER.info(abasMobileUtils.exceptionMessage(e));
					LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
					mi.setMessage(e.getLocalizedMessage());
					mi.setStatus(false);
					mi.setData1(data);
					mi.setData2(data);
				}
			}
			else
			{
				mi.setMessage("Bağlantı hatası ...");
				mi.setStatus(false);
				mi.setData1(data);
				mi.setData2(data);
			}
		}
		return mi;
	}
}
