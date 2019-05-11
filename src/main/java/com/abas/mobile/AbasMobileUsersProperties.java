package com.abas.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.thymeleaf.util.ListUtils;
import com.abas.mobile.model.AbasUserDetails;

@ConfigurationProperties("users")
public class AbasMobileUsersProperties
{
	private final List<AbasUserDetails> admins=new ArrayList<>();
	private final List<AbasUserDetails> whs=new ArrayList<>();
	private final List<AbasUserDetails> pdcs=new ArrayList<>();
	private final List<AbasUserDetails> shpms=new ArrayList<>();
	
	public List<AbasUserDetails> getAdmins()
	{
		return this.admins;
	}
	
	public List<AbasUserDetails> getWhs()
	{
		return this.whs;
	}
	
	public List<AbasUserDetails> getPdcs()
	{
		return this.pdcs;
	}
	
	public List<AbasUserDetails> getShpms()
	{
		return this.shpms;
	}
	
	public List<AbasUserDetails> getUsers()
	{
		return Stream.of(admins,whs,pdcs,shpms)
		             .flatMap(x->x.stream())
		             .collect(Collectors.toList());
	}
}
