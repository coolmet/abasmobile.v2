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
	{"/login"})
	public ModelAndView adminlogin()
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
			return "redirect:/admin/";
		}
		else if(request.isUserInRole("ROLE_USER_WAREHOUSE"))
		{
			return "redirect:/wh/";
		}
		else if(request.isUserInRole("ROLE_USER_PDC"))
		{
			return "redirect:/pdc/";
		}
		else if(request.isUserInRole("ROLE_USER_SHIPMENT"))
		{
			return "redirect:/shpm/";
		}
		return "redirect:/";
	}
	
}
