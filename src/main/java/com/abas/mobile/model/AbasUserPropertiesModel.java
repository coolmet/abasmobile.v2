package com.abas.mobile.model;

import java.util.ArrayList;
import java.util.List;

public class AbasUserPropertiesModel
{
	private List<AbasUserDetailsModel> admins=new ArrayList<>();
	private List<AbasUserDetailsModel> whs=new ArrayList<>();
	private List<AbasUserDetailsModel> pdcs=new ArrayList<>();
	private List<AbasUserDetailsModel> shpms=new ArrayList<>();
	
	
	public List<AbasUserDetailsModel> getAdmins()
	{
		return admins;
	}
	
	public void setAdmins(List<AbasUserDetailsModel> admins)
	{
		this.admins=admins;
	}
	
	public void addAdmins(AbasUserDetailsModel admin)
	{
		this.admins.add(admin);
	}
	
	public void addAdmins(String username,String password,String...roles)
	{
		AbasUserDetailsModel model=new AbasUserDetailsModel();
		model.setUsername(username);
		model.setPassword(password);
		model.setRoles(roles);
		this.admins.add(model);
	}
	
	public List<AbasUserDetailsModel> getWhs()
	{
		return whs;
	}
	
	public void setWhs(List<AbasUserDetailsModel> whs)
	{
		this.whs=whs;
	}
	
	public void addWhs(AbasUserDetailsModel wh)
	{
		this.whs.add(wh);
	}
	
	public void addWhs(String username,String password,String...roles)
	{
		AbasUserDetailsModel model=new AbasUserDetailsModel();
		model.setUsername(username);
		model.setPassword(password);
		model.setRoles(roles);
		this.whs.add(model);
	}
	
	public List<AbasUserDetailsModel> getPdcs()
	{
		return pdcs;
	}
	
	public void setPdcs(List<AbasUserDetailsModel> pdcs)
	{
		this.pdcs=pdcs;
	}
	
	public void addPdcs(AbasUserDetailsModel pdc)
	{
		this.pdcs.add(pdc);
	}
	
	public void addPdcs(String username,String password,String...roles)
	{
		AbasUserDetailsModel model=new AbasUserDetailsModel();
		model.setUsername(username);
		model.setPassword(password);
		model.setRoles(roles);
		this.pdcs.add(model);
	}
	
	public List<AbasUserDetailsModel> getShpms()
	{
		return shpms;
	}
	
	public void setShpms(List<AbasUserDetailsModel> shpms)
	{
		this.shpms=shpms;
	}
	
	public void addShpms(AbasUserDetailsModel shpm)
	{
		this.shpms.add(shpm);
	}
	
	public void addShpms(String username,String password,String...roles)
	{
		AbasUserDetailsModel model=new AbasUserDetailsModel();
		model.setUsername(username);
		model.setPassword(password);
		model.setRoles(roles);
		this.shpms.add(model);
	}
}
