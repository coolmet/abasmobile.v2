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
import com.abas.mobile.ConfigPropertiesAbas;
import com.abas.mobile.ConfigPropertiesServer;
import com.abas.mobile.ConfigPropertiesSpring;
import com.abas.mobile.HttpSessionConfig;
import com.abas.mobile.SprinBootAppConfiguration;

@Controller
public class WebLinkController_Admin
{
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private HttpSessionConfig httpSessionConfig;
	
	@Autowired
	private ConfigPropertiesAbas configAbas;
	
	@Autowired
	private ConfigPropertiesSpring configSpring;
	
	@Autowired
	private ConfigPropertiesServer configServer;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@RequestMapping(value=
	{"/admin"})
	public ModelAndView admin()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_admin");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/settings"})
	public ModelAndView settings()
	{
		ModelAndView mav=new ModelAndView();
		mav.addObject("abas.edp.password",configAbas.getEdp().getPassword());
		mav.addObject("abas.edp.port",configAbas.getEdp().getPort());
		mav.addObject("abas.edp.serverip",configAbas.getEdp().getServerip());
		mav.addObject("abas.s3.dir",configAbas.getS3().getDir());
		mav.addObject("abas.s3.basedir",configAbas.getS3().getBaseDir());
		mav.addObject("abas.s3.mandant",configAbas.getS3().getMandant());
		mav.addObject("spring.mvc.locale",configSpring.getMvc().getLocale());		
		mav.addObject("server.port",configServer.getPort());
		mav.addObject("server.connection-timeout",configServer.getConnectionTimeout());
		mav.addObject("server.servlet.session.timeout",configServer.getServlet().getSession().getTimeout());		
		mav.setViewName("th_admin_settings");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/actuators"})
	public ModelAndView actuators()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_admin_actuators");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/activesessions"})
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
		mav.setViewName("th_admin_activesessions");
		return mav;
	}
}
