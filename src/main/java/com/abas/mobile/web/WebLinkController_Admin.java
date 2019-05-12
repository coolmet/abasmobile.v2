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
	public ModelAndView settings(HttpSession session,
	                             HttpServletRequest request,
	                             @ModelAttribute(value="abas_edp_password") String abas_edp_password,
	                             @ModelAttribute(value="abas_edp_port") String abas_edp_port,
	                             @ModelAttribute(value="abas_edp_serverip") String abas_edp_serverip,
	                             @ModelAttribute(value="abas_s3_dir") String abas_s3_dir,
	                             @ModelAttribute(value="abas_s3_basedir") String abas_s3_basedir,
	                             @ModelAttribute(value="abas_s3_mandant") String abas_s3_mandant,
	                             @ModelAttribute(value="spring_mvc_locale") String spring_mvc_locale,
	                             @ModelAttribute(value="server_port") String server_port,
	                             @ModelAttribute(value="server_connection_timeout") String server_connection_timeout,
	                             @ModelAttribute(value="server_servlet_session_timeout") String server_servlet_session_timeout)
	{
		LOGGER.info("@@@ "+spring_mvc_locale+"\t"+request.getParameter("spring_mvc_locale"));
		ModelAndView mav=new ModelAndView();
		if(abas_edp_password==null||abas_edp_password.equals(""))
		{
			mav.addObject("abas_edp_password",configAbas.getEdp().getPassword());
			mav.addObject("abas_edp_port",""+configAbas.getEdp().getPort());
			mav.addObject("abas_edp_serverip",configAbas.getEdp().getServerip());
			mav.addObject("abas_s3_dir",configAbas.getS3().getDir());
			mav.addObject("abas_s3_basedir",configAbas.getS3().getBaseDir());
			mav.addObject("abas_s3_mandant",configAbas.getS3().getMandant());
			mav.addObject("spring_mvc_locale",configSpring.getMvc().getLocale());
			mav.addObject("server_port",""+configServer.getPort());
			mav.addObject("server_connection_timeout",configServer.getConnectionTimeout());
			mav.addObject("server_servlet_session_timeout",configServer.getServlet().getSession().getTimeout());
			mav.addObject("message",session.getAttribute("settings_message"+session.getId()));
			mav.addObject("status",session.getAttribute("settings_status"+session.getId()));
			mav.setViewName("th_admin_settings");
			session.removeAttribute("settings_abas_edp_password"+session.getId());
			session.removeAttribute("settings_abas_edp_port"+session.getId());
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
		else // save butonu
		{
			// SAVE
			LOGGER.info("@@@ "+abas_edp_password+"\t"+abas_edp_port+"\t"+abas_edp_serverip+"\t"+abas_s3_dir+"\t"+abas_s3_basedir+"\t"+abas_s3_mandant+"\t"+spring_mvc_locale+"\t"+server_port+"\t"+server_connection_timeout+"\t"+server_servlet_session_timeout);
			MessageInfo result=updateSettingsService.update(abas_edp_password,abas_edp_port,abas_edp_serverip,abas_s3_dir,abas_s3_basedir,abas_s3_mandant,spring_mvc_locale,server_port,server_connection_timeout,server_servlet_session_timeout);
			session.setAttribute("settings_abas_edp_password"+session.getId(),abas_edp_password);
			session.setAttribute("settings_abas_edp_port"+session.getId(),abas_edp_port);
			session.setAttribute("settings_abas_edp_serverip"+session.getId(),abas_edp_serverip);
			session.setAttribute("settings_abas_s3_dir"+session.getId(),abas_s3_dir);
			session.setAttribute("settings_abas_s3_basedir"+session.getId(),abas_s3_basedir);
			session.setAttribute("settings_abas_s3_mandant"+session.getId(),abas_s3_mandant);
			session.setAttribute("settings_spring_mvc_locale"+session.getId(),spring_mvc_locale);
			session.setAttribute("settings_server_port"+session.getId(),server_port);
			session.setAttribute("settings_server_connection_timeout"+session.getId(),server_connection_timeout);
			session.setAttribute("settings_server_servlet_session_timeout"+session.getId(),server_servlet_session_timeout);
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
