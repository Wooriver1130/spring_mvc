package com.ict.edu02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.edu02.service.CalcService;
import com.ict.edu02.service.MemberService;
import com.ict.edu02.vo.CalcVO;
import com.ict.edu02.vo.MembersVO;

@Controller
public class MyController3 {
	
	// @SInject : java
	// @Autowired: Spring
	
	// 서비스와 같은 다른곳에서 만들어진 객체를 가져와서 사용하기 위한 어노테이션
	@Autowired
	private CalcService calcService;
	@Autowired
	private MemberService memberService;
	
	/*
	 * @PostMapping("/calc2") public ModelAndView docalc2(CalcVO cvo) { // CalcVO에
	 * 파라미터들이 알아서 넣어진다.(변수와 파라미터 이름이 반드시 같아야 함) ModelAndView mav = new
	 * ModelAndView("day02/result1");
	 * 
	 * // 파라미터가 저장되어 있는지 확ㅇ니 // System.out.println(cvo.getS1());
	 * 
	 * // 일처리는 service로 넘기자 int result = calcService.getCalc(cvo);
	 * mav.addObject("cvo", cvo); mav.addObject("result", result); return mav;
	 * 
	 * }
	 */
	
	@PostMapping("/calc2")
	// Model 클래스의 속성을 이용해서 ("cvo", cvo) 저장되는 어노테이션이다.ㅋ
	public ModelAndView docalc2(@ModelAttribute("cvo")CalcVO cvo) { // CalcVO에 파라미터들이 알아서 넣어진다.(변수와 파라미터 이름이 반드시 같아야 함)
		
		ModelAndView mav = new ModelAndView("day02/result1");
		
		// 파라미터가 저장되어 있는지 확인
		// System.out.println(cvo.getS1());
		
		// 일처리는 service로 넘기자
		int result = calcService.getCalc(cvo);
	// 	mav.addObject("cvo", cvo); 위의 인자 안에 modelattribute를 사용하는것과 같다.
		
		mav.addObject("result", result);
		return mav;
	}
		
	    @GetMapping("/member_list")
		public ModelAndView memberList() {
	    	ModelAndView mav = new ModelAndView("day02/memberResult");
	    	
	    	List<MembersVO> list = memberService.getMemberList();
	    	System.out.println(list);
	    	mav.addObject("list", list);
			return mav;
		
	}
}
