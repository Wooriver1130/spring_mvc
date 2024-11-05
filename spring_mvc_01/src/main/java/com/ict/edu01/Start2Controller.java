package com.ict.edu01;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class Start2Controller implements Controller {
@Override
public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	ModelAndView mav = new ModelAndView();
	mav.setViewName("result2");
	
	String[] onePiece = {"루피", "조로", "상디", "나미", "우솝"};
	mav.addObject("onePiece", onePiece);
	
	ArrayList<String> list = new ArrayList<String>();
	list.add("사과");
	list.add("바나나");
	list.add("딸기");
	mav.addObject("list", list);


	
	return mav;
}
}
