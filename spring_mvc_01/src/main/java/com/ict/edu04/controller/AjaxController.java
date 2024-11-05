package com.ict.edu04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.edu04.service.AjaxMembersService;

@Controller
public class AjaxController {
	
	@Autowired
	private AjaxMembersService ajaxMembersService;
	
	@GetMapping("/ajax_test_xml")
	public ModelAndView ajaxTest() {
		return new ModelAndView("day04/ajax_result");
	}
	
	@GetMapping("/ajax_test_json")
	public ModelAndView ajaxTest2() {
		return new ModelAndView("day04/ajax_result2");
	}
	@RequestMapping("/ajax_test_db")
	public ModelAndView ajaxTest3() {
		return new ModelAndView("day04/ajax_result3");
	}
}
