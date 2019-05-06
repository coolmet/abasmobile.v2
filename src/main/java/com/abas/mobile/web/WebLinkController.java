package com.abas.mobile.web;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.abas.mobile.SprinBootAppConfiguration;
import com.abas.mobile.service.AbasSessionRegistry;

@Controller
public class WebLinkController
{
	@Autowired
	private AbasSessionRegistry abasSessionRegistry;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
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
	{"/abasadmin/activesessions"})
	public ModelAndView activesessions()
	{
		ModelAndView mav=new ModelAndView();
		LOGGER.info(""+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		LOGGER.info(""+SecurityContextHolder.getContext().getAuthentication().getName());
		LOGGER.info(""+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		LOGGER.info(""+SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
		LOGGER.info(""+SecurityContextHolder.getContext().getAuthentication().getDetails().toString());
		LOGGER.info(""+abasSessionRegistry.getSessions().size());
		for(HttpSession hs:abasSessionRegistry.getSessions())
		{
			LOGGER.info("@@@ getId:"+hs.getId());
			LOGGER.info("@@@ getCreationTime:"+hs.getCreationTime());
			LOGGER.info("@@@ getLastAccessedTime:"+hs.getLastAccessedTime());
			LOGGER.info("@@@ getMaxInactiveInterval:"+hs.getMaxInactiveInterval());
			LOGGER.info("@@@ getValueNames:"+hs.getValueNames());
			LOGGER.info("@@@ getSessionContext:"+hs.getSessionContext().toString());
			LOGGER.info("@@@ getServletContext:"+hs.getServletContext().toString());
		}
		
		
		
		// LOGGER.info(""+sessionRegistry.getAllSessions(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),false).size());
		//
		// LOGGER.info(""+sessionRegistry.getAllPrincipals().size());
		// LOGGER.info(""+sessionRegistry.getAllSessions(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),false).size());
		
		// UserDetails currenstsession=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//
		// List<UserDetails> activesessions=sessionRegistry.getAllPrincipals().stream()
		// .filter(u->!sessionRegistry.getAllSessions(u,true).isEmpty())
		// .map(u->(UserDetails)u)
		// .collect(Collectors.toList());
		//
		// mav.addObject("activeSessions",activesessions);
		// mav.addObject("currenstSession",currenstsession);
		mav.setViewName("th_abasadmin_activesessions");
		return mav;
	}
}
