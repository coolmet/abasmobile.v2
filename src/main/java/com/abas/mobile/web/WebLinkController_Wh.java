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
	public ModelAndView wh_receipt(HttpSession session,HttpServletRequest request)
	{
		ModelAndView mav=new ModelAndView();
		if(session.getAttribute("wh.receipt.product"+session.getId())!=null)
		{
			System.out.println(session.getAttribute("wh.receipt.product"+session.getId())+"::"+session.getAttribute("wh.receipt.message"+session.getId()));
			mav.addObject("product",session.getAttribute("wh.receipt.product"+session.getId()));
			mav.addObject("location",session.getAttribute("wh.receipt.location"+session.getId()));
			mav.addObject("charge",session.getAttribute("wh.receipt.charge"+session.getId()));
			mav.addObject("quantity",session.getAttribute("wh.receipt.quantity"+session.getId()));
			mav.addObject("message",session.getAttribute("wh.receipt.message"+session.getId()));
			mav.addObject("status",session.getAttribute("wh.receipt.status"+session.getId()));
			session.removeAttribute("wh.receipt.product"+session.getId());
			session.removeAttribute("wh.receipt.location"+session.getId());
			session.removeAttribute("wh.receipt.charge"+session.getId());
			session.removeAttribute("wh.receipt.quantity"+session.getId());
			session.removeAttribute("wh.receipt.message"+session.getId());
			session.removeAttribute("wh.receipt.status"+session.getId());
		}
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
		mav.addObject("status",mi.isStatus());
		mav.addObject("data1","");
		mav.addObject("data2","");
		mav.setViewName("th_result");
		return mav;
	}
	
	@RequestMapping(value=
	{"/wh/receipt/save2"})
	public ModelAndView wh_receipt_save2(HttpSession session,HttpServletRequest request)
	{
		
		ModelAndView mav=new ModelAndView();
		
		
		
		if(request.getMethod().equals("POST"))
		{
			MessageInfo mi=new MessageInfo();
			mi.setMessage("okkk");
			mi.setStatus(true);
			//
			session.setAttribute("wh.receipt.product"+session.getId(),request.getParameter("product"));
			session.setAttribute("wh.receipt.location"+session.getId(),request.getParameter("location"));
			session.setAttribute("wh.receipt.charge"+session.getId(),request.getParameter("charge"));
			session.setAttribute("wh.receipt.quantity"+session.getId(),request.getParameter("quantity"));
			session.setAttribute("wh.receipt.message"+session.getId(),mi.getMessage());
			session.setAttribute("wh.receipt.status"+session.getId(),""+mi.isStatus());
			mav.setViewName("redirect:/wh/receipt");
		}
		else
		{
			mav.setViewName("th_wh_receipt");
		}
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
