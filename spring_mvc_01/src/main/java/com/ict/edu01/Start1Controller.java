package com.ict.edu01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// 컨트롤러를 어노테이션 하지 않으면 반드시 컨트롤러를(implements) 구현해야 한다. 
public class Start1Controller  implements Controller{

// 실행하는 메소드
@Override
public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	// servlet-context.xml 되돌아 갔다가 ViewResolver에 들어가는 이름
	// 단순하게 말하면 결과를 보여줄 jsp 이름이다.( 경로, 확장자 제외)

	// 방법1
	ModelAndView mav = new ModelAndView();
	mav.setViewName("result1");
	
	// 방법2
	mav = new ModelAndView("result1");
	
	/*
	     1. (일처리 = 비즈니스로직) 
	  => 2. 서비스(interface) 
	  => 3. 서비스임플리먼트(클래스) 
	  => 4. DAO(interface) DAO임플리먼트(클래스)
	  => 5. DB
	  
	  // Spring에서 일처리 하는 패턴
	  2, 3은 비즈니스 레이어, 4, 5는 레포지터리(Mapper) 레이어라고 한다.
	  
	 */
	// JSP에서 표현하기 위해서 값을 저장하는 방법
	request.setAttribute("name", "홍길동"); // 1) request 이용 (JSP MVC와 같음, 비권장)
	mav.addObject("age", 27); // 2) ModelAndView 이용(권장)
	request.getSession().setAttribute("phone", "010-1234-5678"); // 3)session(로그인 처리할떄 만 사용)
	
	return mav;
}
}
