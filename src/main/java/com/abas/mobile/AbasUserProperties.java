package com.abas.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.thymeleaf.util.ListUtils;
import com.abas.mobile.model.AbasUserDetailsModel;

@ConfigurationProperties("users")
public class AbasUserProperties
{ 
	private final List<AbasUserDetailsModel> admins=new ArrayList<>();
	private final List<AbasUserDetailsModel> whs=new ArrayList<>();
	private final List<AbasUserDetailsModel> pdcs=new ArrayList<>();
	private final List<AbasUserDetailsModel> shpms=new ArrayList<>();
	
	public List<AbasUserDetailsModel> getAdmins()
	{
		return this.admins;
	}
	
	public List<AbasUserDetailsModel> getWhs()
	{
		return this.whs;
	}
	
	public List<AbasUserDetailsModel> getPdcs()
	{
		return this.pdcs;
	}
	
	public List<AbasUserDetailsModel> getShpms()
	{
		return this.shpms;
	}
	
	public List<AbasUserDetailsModel> getUsers()
	{
		return Stream.of(admins,whs,pdcs,shpms)
		             .flatMap(x->x.stream())
		             .collect(Collectors.toList());
	}
}
