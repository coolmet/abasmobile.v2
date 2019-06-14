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
import com.abas.mobile.service.AdminSettingsService;
import com.abas.mobile.service.EdpSessionService;

@Controller
public class WebLinkController_Wh
{
	@Autowired
	EdpSessionService edpSessionService;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@RequestMapping(value=
	{"/wh"})
	public ModelAndView wh()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_wh");
		return mav;
	}
	
	@RequestMapping(value=
	{"/wh/receipt"})
	public ModelAndView wh_receipt()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_wh_receipt"); 
		return mav;
	}
	
	@RequestMapping(value=
	{"/wh/receipt/input/check"})
	public ModelAndView wh_receipt_input_check(HttpSession session,HttpServletRequest request)
	{
		ModelAndView mav=new ModelAndView();
		//edpSessionService.EDPSESSION_START();
		
		System.out.println("*********************"+request.getParameter("fieldid")+":"+request.getParameter("fieldvalue"));
		mav.addObject("message","mesage");
		mav.addObject("status","false");
		mav.setViewName("th_result");
		return mav;
	}
}
