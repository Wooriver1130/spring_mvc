package com.ict.edu05.controller;

import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ict.edu05.service.EmailService;


public class EmailController {
	
	@Autowired
	private EmailService emailService ;
	
	@GetMapping("/email")
	public ModelAndView emailForm() {
		return new ModelAndView("day05/email_form");
	}
	
	@PostMapping("/email_send")
	public ModelAndView sendMail(@RequestParam("email") String email, HttpServletRequest request) {
		try {
			ModelAndView mav = new ModelAndView("day05/email_form");

			// 임시번호 6자리 만들기
			Random random = new Random(); 
			// 0~ 1000000 미만의 정수를 무작위로 생성
			String ranNum = String.valueOf(random.nextInt(1000000));
			
			// 길이가 6자리가 안되면 0 으로 채우자
			if (ranNum.length() < 6) {
				int substract = 6- ranNum.length();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < substract; i++) {
					sb.append("0");
				}
				sb.append(ranNum);
				ranNum = sb.toString();
			}
				// 임시번호 서버에 출력
				System.out.println("임시번호: " + ranNum);
				
			// 해당 임시번호를 DB에 저장하기 또는 세션에 저장하기
			request.getSession().setAttribute("sessionNumber", ranNum);
			
			// EmailService 호출해서 사용하기
			emailService.sendEmail(ranNum, email);
				
			return mav;
			
		} catch (Exception e) {
			System.out.println(e);
			return null ;
		}
		
	}

	@PostMapping("/email_send_chk")
	public ModelAndView sendMailOK(@RequestParam("authNumber") String authNumber, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("day05/email_form");
		
		String sessionNumber = (String) request.getSession().getAttribute("sessionNumber");
		if (authNumber.equalsIgnoreCase(sessionNumber)) {
			mav.addObject("emailchk", "1");
		}
		return mav;
	}
}
