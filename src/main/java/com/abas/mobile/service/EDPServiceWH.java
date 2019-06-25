package com.abas.mobile.service;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import com.abas.mobile.SprinBootAppConfiguration;
import com.abas.mobile.model.MessageInfo;
import de.abas.ceks.jedp.EDPEditor;
import de.abas.ceks.jedp.EDPMessage;
import de.abas.ceks.jedp.EDPQuery;
import de.abas.ceks.jedp.EDPSession;

@Service
public class EDPServiceWH
{
	@Autowired
	EDPSessionService edpSessionService;
	
	@Autowired
	AbasMobileUtils abasMobileUtils;
	
	@Autowired
	private MessageSource messageSource;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	public MessageInfo receiptSave(String product,String location,String lot,String quantity)
	{
		MessageInfo mi=new MessageInfo();
		
		if(!(mi=this.productIsAvailable(product)).isStatus())
		{
		}
		else if(!(mi=this.locationIsAvailable(location)).isStatus())
		{
		}
		else if(!(mi=this.lotIsAvailable(lot,product)).isStatus())
		{
		}
		else
		{
			EDPEditor EDPEDIT=null;
			ArrayList<EDPMessage> edpMessages=new ArrayList<EDPMessage>();
			if(edpSessionService.EDPSESSION_START())
			{
				
				for(int i=0;i<1;i++)
				{
					if(edpSessionService.EDPSESSION_START())
					{
						try
						{
							// ^^^^^^^^
							try
							{
								if(EDPEDIT!=null&&EDPEDIT.isActive())
								{
									EDPEDIT.endEditCancel();
								}
							}
							catch(Exception rt)
							{
								EDPEDIT=null;
							}
							finally
							{
								if(EDPEDIT==null)
								{
									EDPEDIT=edpSessionService.SESSION.createEditor();
									EDPEDIT.resetErrorMessageListener();
									EDPEDIT.setErrorMessageListener(edpSessionService.edpMessageListener(edpMessages));
									EDPEDIT.setStatusMessageListener(edpSessionService.edpMessageListener(edpMessages));
								}
							}
							// ^^^^^^^^
							EDPEDIT=edpSessionService.SESSION.createEditor();
							EDPEDIT.beginEdit(EDPEditor.EDIT_DO,"(Stockadjustment)","",EDPEditor.REFTYPE_EMPTY,""); // Stockadjustment-lbuch
							EDPEDIT.setFieldVal("artikel",product);
							EDPEDIT.setFieldVal("buart","(1)");
							EDPEDIT.setFieldVal("beleg","AbasMobile");
							EDPEDIT.setFieldVal("beldat",".");
							EDPEDIT.setFieldVal("ljtext1","AbasMobile");
							EDPEDIT.setFieldVal(1,"mge",quantity);
							EDPEDIT.setFieldVal(1,"platz2",location);
							if(!lot.equals(""))
							{
								EDPEDIT.setFieldVal(1,"charge2",lot);
							}
							EDPEDIT.endEditSave();
							mi.setMessage(messageSource.getMessage("wh.receipt.message.receiptok",new Object[0],LocaleContextHolder.getLocale()));
							mi.setStatus(true);
							mi.setData1("");
							mi.setData2("");
						}
						catch(Exception e)
						{
							e.printStackTrace();
							LOGGER.info(abasMobileUtils.exceptionMessage(e));
							LOGGER.debug(abasMobileUtils.exceptionMessageDetails(e));
							mi.setMessage(e.getLocalizedMessage());
							mi.setStatus(false);
							mi.setData1("");
							mi.setData2("");
							edpSessionService.EDPSESSION_END();
						}
					}
				}
				
			}
			else
			{
				mi.setMessage(messageSource.getMessage("wh.receipt.message.connectionerror",new Object[0],LocaleContextHolder.getLocale()));
				mi.setStatus(false);
				mi.setData1("");
				mi.setData2("");
			}
		}
		
		return mi;
	}
	
	public MessageInfo productIsAvailable(String data)
	{
		MessageInfo mi=new MessageInfo();
		if(data.equals(""))
		{
			mi.setMessage(messageSource.getMessage("wh.receipt.message.productcantfound",new Object[0],LocaleContextHolder.getLocale())+" : "+data);
			mi.setStatus(false);
			mi.setData1(data);
			mi.setData2(data);
		}
		else
		{
			if(edpSessionService.EDPSESSION_START())
			{
				EDPQuery edpQuery;
				String lotField=edpSessionService.getAbasVersion()>=2018?"chpflicht":"lot";
				try
				{
					edpQuery=edpSessionService.SESSION.createQuery();
					edpQuery.startQuery("2:1","",(Character.isDigit(data.charAt(0))?"nummer=":"such=")+data,false,"nummer"+","+lotField);
					if(edpQuery!=null)
					{
						if(edpQuery.getNextRecord())
						{
							mi.setMessage(messageSource.getMessage("wh.receipt.message.productfound",new Object[0],LocaleContextHolder.getLocale())+" : "+data);
							mi.setStatus(true);
							mi.setData1(edpQuery.getField("nummer").trim());
							mi.setData2(edpQuery.getField(lotField).trim());
						}
						else
						{
							mi.setMessage(messageSource.getMessage("wh.receipt.message.productcantfound",new Object[0],LocaleContextHolder.getLocale())+" : "+data);
							mi.setStatus(false);
							mi.setData1(data);
							mi.setData2(data);
						}
					}
					else
					{
						mi.setMessage(messageSource.getMessage("wh.receipt.message.connectionerror",new Object[0],LocaleContextHolder.getLocale()));
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
				mi.setMessage(messageSource.getMessage("wh.receipt.message.connectionerror",new Object[0],LocaleContextHolder.getLocale()));
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
			mi.setMessage(messageSource.getMessage("wh.receipt.message.locationcantfound",new Object[0],LocaleContextHolder.getLocale())+" : "+data);
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
							mi.setMessage(messageSource.getMessage("wh.receipt.message.locationfound",new Object[0],LocaleContextHolder.getLocale())+" : "+data);
							mi.setStatus(true);
							mi.setData1(edpQuery.getField("such").trim());
							mi.setData2(edpQuery.getField("such").trim());
						}
						else
						{
							mi.setMessage(messageSource.getMessage("wh.receipt.message.locationcantfound",new Object[0],LocaleContextHolder.getLocale())+" : "+data);
							mi.setStatus(false);
							mi.setData1(data);
							mi.setData2(data);
						}
					}
					else
					{
						mi.setMessage(messageSource.getMessage("wh.receipt.message.connectionerror",new Object[0],LocaleContextHolder.getLocale()));
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
				mi.setMessage(messageSource.getMessage("wh.receipt.message.connectionerror",new Object[0],LocaleContextHolder.getLocale()));
				mi.setStatus(false);
				mi.setData1(data);
				mi.setData2(data);
			}
		}
		return mi;
	}
	
	public MessageInfo lotIsAvailable(String lot,String artikel)
	{
		MessageInfo mi=new MessageInfo();
		if(lot.equals(""))
		{
			mi.setMessage(messageSource.getMessage("wh.receipt.message.lotcantfound",new Object[0],LocaleContextHolder.getLocale())+" : "+lot+"|"+artikel);
			mi.setStatus(false);
			mi.setData1(lot);
			mi.setData2(lot);
		}
		if(artikel.equals(""))
		{
			mi.setMessage(messageSource.getMessage("wh.receipt.message.productcantfound",new Object[0],LocaleContextHolder.getLocale())+" : "+lot+"|"+artikel);
			mi.setStatus(false);
			mi.setData1(artikel);
			mi.setData2(artikel);
		}
		else
		{
			if(edpSessionService.EDPSESSION_START())
			{
				EDPQuery edpQuery;
				try
				{
					edpQuery=edpSessionService.SESSION.createQuery();
					edpQuery.startQuery("59:0","",(Character.isDigit(lot.charAt(0))?"nummer=":"such=")+lot,false,"nummer,artikel^nummer");
					if(edpQuery!=null)
					{
						if(edpQuery.getNextRecord())
						{
							if(edpQuery.getField("artikel^nummer").trim().equals(artikel))
							{
								mi.setMessage(messageSource.getMessage("wh.receipt.message.lotfound",new Object[0],LocaleContextHolder.getLocale())+" : "+lot+"|"+artikel);
								mi.setStatus(true);
								mi.setData1(edpQuery.getField("nummer").trim());
								mi.setData2(edpQuery.getField("nummer").trim());
							}
							else
							{
								mi.setMessage(messageSource.getMessage("wh.receipt.message.productnotmatchlot",new Object[0],LocaleContextHolder.getLocale())+" : "+lot+"|"+artikel);
								mi.setStatus(false);
								mi.setData1(edpQuery.getField("nummer").trim());
								mi.setData2(edpQuery.getField("artikel^nummer").trim());
							}
							
						}
						else
						{
							mi.setMessage(messageSource.getMessage("wh.receipt.message.lotcantfound",new Object[0],LocaleContextHolder.getLocale())+" : "+lot+"|"+artikel);
							mi.setStatus(false);
							mi.setData1(lot);
							mi.setData2(lot);
						}
					}
					else
					{
						mi.setMessage(messageSource.getMessage("wh.receipt.message.connectionerror",new Object[0],LocaleContextHolder.getLocale()));
						mi.setStatus(false);
						mi.setData1(lot+"|"+artikel);
						mi.setData2(lot+"|"+artikel);
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
					mi.setData1(lot+"|"+artikel);
					mi.setData2(lot+"|"+artikel);
				}
			}
			else
			{
				mi.setMessage(messageSource.getMessage("wh.receipt.message.connectionerror",new Object[0],LocaleContextHolder.getLocale()));
				mi.setStatus(false);
				mi.setData1(lot+"|"+artikel);
				mi.setData2(lot+"|"+artikel);
			}
		}
		return mi;
	}
}
