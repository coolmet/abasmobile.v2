package com.abas.mobile.web;

import java.util.Enumeration;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.abas.mobile.ConfigPropertiesAbas;
import com.abas.mobile.ConfigPropertiesServer;
import com.abas.mobile.ConfigPropertiesSpring;
import com.abas.mobile.HttpSessionConfig;
import com.abas.mobile.SprinBootAppConfiguration;
import com.abas.mobile.model.MessageInfo;
import com.abas.mobile.service.UpdateSettingsService;

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
	
	@Autowired
	UpdateSettingsService updateSettingsService;
	
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
	public ModelAndView settings(HttpSession session,HttpServletRequest request)
	{
		ModelAndView mav=new ModelAndView();
		if(request.getMethod().equals("GET"))
		{
			if(session.getAttribute("settings_message"+session.getId())==null)
			{
				mav.addObject("abas_edp_password",configAbas.getEdp().getPassword());
				mav.addObject("abas_edp_port",""+configAbas.getEdp().getPort());
				mav.addObject("abas_edp_lang",configAbas.getEdp().getLang());
				mav.addObject("abas_edp_fopmode",""+configAbas.getEdp().isFopmode());
				mav.addObject("abas_edp_serverip",configAbas.getEdp().getServerip());
				mav.addObject("abas_s3_dir",configAbas.getS3().getDir());
				mav.addObject("abas_s3_basedir",configAbas.getS3().getBaseDir());
				mav.addObject("abas_s3_mandant",configAbas.getS3().getMandant());
				mav.addObject("spring_mvc_locale",configSpring.getMvc().getLocale());
				mav.addObject("server_port",""+configServer.getPort());
				mav.addObject("server_connection_timeout",configServer.getConnectionTimeout());
				mav.addObject("server_servlet_session_timeout",configServer.getServlet().getSession().getTimeout());
				mav.addObject("message","");
				mav.addObject("status","");
			}
			else
			{
				mav.addObject("abas_edp_password",session.getAttribute("settings_abas_edp_password"+session.getId()));
				mav.addObject("abas_edp_port",session.getAttribute("settings_abas_edp_port"+session.getId()));
				mav.addObject("abas_edp_lang",session.getAttribute("settings_abas_edp_lang"+session.getId()));
				mav.addObject("abas_edp_fopmode",session.getAttribute("settings_abas_edp_fopmode"+session.getId()));
				mav.addObject("abas_edp_serverip",session.getAttribute("settings_abas_edp_serverip"+session.getId()));
				mav.addObject("abas_s3_dir",session.getAttribute("settings_abas_s3_dir"+session.getId()));
				mav.addObject("abas_s3_basedir",session.getAttribute("settings_abas_s3_basedir"+session.getId()));
				mav.addObject("abas_s3_mandant",session.getAttribute("settings_abas_s3_mandant"+session.getId()));
				mav.addObject("spring_mvc_locale",session.getAttribute("settings_spring_mvc_locale"+session.getId()));
				mav.addObject("server_port",session.getAttribute("settings_server_port"+session.getId()));
				mav.addObject("server_connection_timeout",session.getAttribute("settings_server_connection_timeout"+session.getId()));
				mav.addObject("server_servlet_session_timeout",session.getAttribute("settings_server_servlet_session_timeout"+session.getId()));
				mav.addObject("message",session.getAttribute("settings_message"+session.getId()));
				mav.addObject("status",session.getAttribute("settings_status"+session.getId()));
				session.removeAttribute("settings_abas_edp_password"+session.getId());
				session.removeAttribute("settings_abas_edp_port"+session.getId());
				session.removeAttribute("settings_abas_edp_lang"+session.getId());
				session.removeAttribute("settings_abas_edp_fopmode"+session.getId());
				session.removeAttribute("settings_abas_edp_serverip"+session.getId());
				session.removeAttribute("settings_abas_s3_dir"+session.getId());
				session.removeAttribute("settings_abas_s3_basedir"+session.getId());
				session.removeAttribute("settings_abas_s3_mandant"+session.getId());
				session.removeAttribute("settings_spring_mvc_locale"+session.getId());
				session.removeAttribute("settings_server_port"+session.getId());
				session.removeAttribute("settings_server_connection_timeout"+session.getId());
				session.removeAttribute("settings_server_servlet_session_timeout"+session.getId());
				session.removeAttribute("settings_message"+session.getId());
				session.removeAttribute("settings_status"+session.getId());
			}
			mav.setViewName("th_admin_settings");			
		}
		else if(request.getMethod().equals("POST")) // save butonu
		{
			// SAVE
			MessageInfo result=updateSettingsService.update(request.getParameter("abas_edp_password"),
			                                                request.getParameter("abas_edp_port"),
			                                                request.getParameter("abas_edp_lang"),
			                                                ""+(request.getParameter("abas_edp_fopmode")!=null),
			                                                request.getParameter("abas_edp_serverip"),
			                                                request.getParameter("abas_s3_dir"),
			                                                request.getParameter("abas_s3_basedir"),
			                                                request.getParameter("abas_s3_mandant"),
			                                                request.getParameter("spring_mvc_locale"),
			                                                request.getParameter("server_port"),
			                                                request.getParameter("server_connection_timeout"),
			                                                request.getParameter("server_servlet_session_timeout"));
			session.setAttribute("settings_abas_edp_password"+session.getId(),request.getParameter("abas_edp_password"));
			session.setAttribute("settings_abas_edp_port"+session.getId(),request.getParameter("abas_edp_port"));
			session.setAttribute("settings_abas_edp_lang"+session.getId(),request.getParameter("abas_edp_lang"));
			session.setAttribute("settings_abas_edp_fopmode"+session.getId(),request.getParameter("abas_edp_fopmode")!=null);
			session.setAttribute("settings_abas_edp_serverip"+session.getId(),request.getParameter("abas_edp_serverip"));
			session.setAttribute("settings_abas_s3_dir"+session.getId(),request.getParameter("abas_s3_dir"));
			session.setAttribute("settings_abas_s3_basedir"+session.getId(),request.getParameter("abas_s3_basedir"));
			session.setAttribute("settings_abas_s3_mandant"+session.getId(),request.getParameter("abas_s3_mandant"));
			session.setAttribute("settings_spring_mvc_locale"+session.getId(),request.getParameter("spring_mvc_locale"));
			session.setAttribute("settings_server_port"+session.getId(),request.getParameter("server_port"));
			session.setAttribute("settings_server_connection_timeout"+session.getId(),request.getParameter("server_connection_timeout"));
			session.setAttribute("settings_server_servlet_session_timeout"+session.getId(),request.getParameter("server_servlet_session_timeout"));
			session.setAttribute("settings_message"+session.getId(),result.getMessage());
			session.setAttribute("settings_status"+session.getId(),result.isStatus());
			mav.setViewName("redirect:/admin/settings");
		}
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
