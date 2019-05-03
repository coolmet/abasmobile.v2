package com.abas.mobile.web;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebLinkController
{
	@RequestMapping(value=
	{"/abasadmin/actuators"})
	public ModelAndView indexTh()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_actuators");
		return mav;
	}
}
