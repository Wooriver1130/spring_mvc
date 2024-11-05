package com.ict.edu06.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.edu06.service.LoginService;
import com.ict.edu06.vo.LoginVO;
@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 로그인폼 이동
	@GetMapping("/login")
	public ModelAndView getLoginForm() {
		return new ModelAndView("day06/login_form");
	}
	
	
	// 회원가입폼 이동 
	@GetMapping("/join_form")
	public ModelAndView getJoinForm() {
		return new ModelAndView("day06/join_form");
	}
	
	// 회원가입하기
	@PostMapping("/join_ok")
	public ModelAndView getJoinOK(LoginVO lvo) {
		ModelAndView mav = new ModelAndView("day06/login_form");
		try {
			// 비번 암호화
			String u_pw = passwordEncoder.encode(lvo.getU_pw());
			lvo.setU_pw(u_pw);
			
			int result = loginService.LoginJoin(lvo);
			if (result == 1) {
				mav.addObject("result", result);
			}else {
				mav.addObject("errorChk", "1");
			}
		} catch (Exception e) {
			System.out.println(e);
			mav.addObject("errorChk", "1");
		}
		return mav ;
	}
	
	// 로그인 하기
	@PostMapping("/login_ok")
	public ModelAndView getLoginOK(LoginVO lvo) {
		ModelAndView mav = new ModelAndView();
		try {
			// 1. 아이디 들고 DB 후딱 갔다오기
			String u_id = lvo.getU_id();
			LoginVO  lvo2 =  loginService.LoginChk(u_id);
			// 2. 사용자 입력한 암호를 암호화 하기
			if (lvo2 != null) {
				// 3. 두 암호를 비교해서 같으면 성공, 다르면 실패
				// matches(암호화 안 된 비밀번호, 암홓화 된 비밀번호) => 일치하면 true, 아니면 false
				if (passwordEncoder.matches(lvo.getU_pw(), lvo2.getU_pw())) {
					mav.addObject("loginChk", 1);
					mav.setViewName("index");
				}else {
					mav.addObject("loginChk", 0);
					mav.setViewName("day06/login_form");
				}
				
			}else { //아이디가 없으면
				mav.addObject("loginChk", 0);
				mav.setViewName("day06/login_form");
			}
		} catch (Exception e) {
			System.out.println(e);
			mav.addObject("errorChk", "1");
			mav.setViewName("day06/login_form");
		}
		return mav;
	}
	
	
	
	
	
	
	
	
	
}