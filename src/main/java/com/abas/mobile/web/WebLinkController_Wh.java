package com.abas.mobile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.abas.mobile.SprinBootAppConfiguration;
import com.abas.mobile.model.MessageInfo;
import com.abas.mobile.service.EDPServiceWH;

@Controller
public class WebLinkController_Wh
{
	@Autowired
	EDPServiceWH edpServicewh;
	
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
	{"/wh/receipt/save"})
	public ModelAndView wh_receipt_save(HttpSession session,HttpServletRequest request)
	{
		ModelAndView mav=new ModelAndView();
		//
		MessageInfo mi=new MessageInfo();
		mi.setMessage("okkk");
		mi.setStatus(true);
		mi.setData1("");
		mi.setData2("");
		//
		mav.addObject("message",mi.getMessage());
		mav.addObject("status",""+mi.isStatus());
		mav.addObject("data1","");
		mav.addObject("data2","");
		mav.setViewName("th_result");
		return mav;
	}
	
	@RequestMapping(value=
	{"/wh/receipt/input/check"})
	public ModelAndView wh_receipt_input_check(HttpSession session,HttpServletRequest request)
	{
		ModelAndView mav=new ModelAndView();
		MessageInfo mi=new MessageInfo();
		if(request.getParameter("fieldid").equals("product"))
		{
			mi=edpServicewh.productIsAvailable(request.getParameter("fieldvalue"));
		}
		else if(request.getParameter("fieldid").equals("location"))
		{
			mi=edpServicewh.locationIsAvailable(request.getParameter("fieldvalue"));
		}
		else if(request.getParameter("fieldid").equals("charge"))
		{
			mi=edpServicewh.chargeIsAvailable(request.getParameter("fieldvalue"),request.getParameter("fieldproductvalue"));
		}
		else if(request.getParameter("fieldid").equals("quantity"))
		{
			mi.setMessage("ok");
			mi.setStatus(true);
			mi.setData1(request.getParameter("fieldvalue"));
			mi.setData2(request.getParameter("fieldvalue"));
		}
		mav.addObject("message",mi.getMessage());
		mav.addObject("status",""+mi.isStatus());
		mav.addObject("data1",""+mi.getData1());
		mav.addObject("data2",""+mi.getData2());
		mav.setViewName("th_result");
		return mav;
	}
}
