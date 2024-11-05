package com.ict.edu01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController2 {
	@PostMapping("/calc")
	public ModelAndView doCalc(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("day01/result9");
		String s1 = request.getParameter("s1");
		String s2 = request.getParameter("s2");
		String op = request.getParameter("op");
		String cPage = request.getParameter("cPage");
		
		String[] hobby = request.getParameterValues("hobby");
		
		int su1= Integer.parseInt(s1);
		int su2 = Integer.parseInt(s2);
		int result =  0 ;
		
		switch (op) {
		case "+": result =  su1 + su2 ; break;
		case "-": result =  su1 - su2 ; break;
		case "*": result =  su1 * su2 ; break;
		case "/": result =  su1 / su2 ; break;

		}
		// 저장하자
		mav.addObject("result", result);
		mav.addObject("s1", s1);
		mav.addObject("s2", s2);
		mav.addObject("op", op);
		mav.addObject("cPage", cPage);
		mav.addObject("hobby", hobby);
		
		
		return mav ;
	}
}
