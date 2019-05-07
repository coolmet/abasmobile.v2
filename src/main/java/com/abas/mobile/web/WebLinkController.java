package com.abas.mobile.web;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.abas.mobile.HttpSessionConfig;
import com.abas.mobile.SprinBootAppConfiguration;

@Controller
public class WebLinkController
{
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private HttpSessionConfig httpSessionConfig;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@RequestMapping(value=
	{"/","/index"})
	public ModelAndView indexTh()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_index");
		return mav;
	}
	
	@RequestMapping(value=
	{"/abasadmin"})
	public ModelAndView abasadmin()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_abasadmin");
		return mav;
	}
	
	@RequestMapping(value=
	{"/abasadmin/actuators"})
	public ModelAndView actuators()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_abasadmin_actuators");
		return mav;
	}
	
	@RequestMapping(value=
	{"/login"})
	public ModelAndView abasadminlogin()
	{
		ModelAndView mav=new ModelAndView();
		if((""+SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser"))
		{
			mav.setViewName("th_login");
		}
		else
		{
			mav.setViewName("redirect:/default");
		}
		return mav;
	}
	
	@RequestMapping("/default")
	public String defaultAfterLoginTh(HttpServletRequest request)
	{
		if(request.isUserInRole("ROLE_ADMIN"))
		{
			return "redirect:/abasadmin/";
		}
		else if(request.isUserInRole("ROLE_USER_WH"))
		{
			return "redirect:/wh/";
		}
		else if(request.isUserInRole("ROLE_USER_PDC"))
		{
			return "redirect:/pdc/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value=
	{"/abasadmin/activesessions"})
	public ModelAndView activesessions()
	{
		ModelAndView mav=new ModelAndView();
		UserDetails activeSession=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserDetails> allSessions=sessionRegistry.getAllPrincipals().stream()
		                                             .filter(u->!sessionRegistry.getAllSessions(u,true).isEmpty())
		                                             .map(u->(UserDetails)u)
		                                             .collect(Collectors.toList());
		//
		List<UserDetails> allSessions1=httpSessionConfig.getActiveSessions()
		                                                .stream()
		                                                .map(session->(UserDetails)((SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal())
		                                                .collect(Collectors.toList());
		//
		System.out.println("sessionRegistry:"+sessionRegistry.getAllPrincipals().size()+"\t"+
		"httpSessionConfig.getActiveSessions:"+httpSessionConfig.getActiveSessions().size()+"\t"+
		"allSessions:"+allSessions.size()+
		"allSessions1:"+allSessions1.size()+
		
		"");
		//
		mav.addObject("activeSession",activeSession);
		mav.addObject("allSessions",allSessions);
		mav.setViewName("th_abasadmin_activesessions");
		return mav;
	}
}
