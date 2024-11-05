package com.ict.edu01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {
	@GetMapping("/start5")
	public ModelAndView method01() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("day01/result567");
		mav.addObject("msg", "안녕하세요start5입니다");
		return mav;
	}
	@GetMapping("/start6")
	public ModelAndView method02(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("day01/result567");
		mav.addObject("msg", "안녕하세요start6입니다");
		return mav;
	}
	@GetMapping("/start7")
	public ModelAndView method03(HttpServletRequest request) {
		request.setAttribute("msg", "안녕하세요start7입니다");
		return new ModelAndView("day01/result567");
	}
	
	@GetMapping("/start8")
	public ModelAndView method04(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("day01/result8");
		
		String username = request.getParameter("username");
		String userage = request.getParameter("userage");
		
		mav.addObject("username", username);
		mav.addObject("userage", userage);
		
		return mav;
	}
}
