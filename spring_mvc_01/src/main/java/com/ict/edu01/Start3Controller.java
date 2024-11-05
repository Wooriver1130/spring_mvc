package com.ict.edu01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller // @Controller(어노테이션)을 하면 상속받지 않아도 Spring에서 Controller로 인식한다.)
public class Start3Controller {
	// URL 매핑( 옛날방식)
//	@RequestMapping(value = "/(URL-mapping)", method = RequestMethod.GET;
//	@RequestMapping(value = "/", method = RequestMethod.POST; 
	
//	@RequestMapping("URL"); // get, post방식 둘 다받는다.
//	@GetMapping("URL");
//	@PostMapping("URL");   
	
	// 해당 메소드는 URL 매핑이 와야 실행된다.
	
	@GetMapping("/start3") // a링크는 get 방식이므로  @GetMapping 이나@RequestMapping 을 사용해야 한다.
	public ModelAndView exec() { // exec()의 인자는 필요할때 사용하면(넣어주면) 된다.
		ModelAndView mav = new ModelAndView("day01/result3");
		mav.addObject("city", "서울");
		return mav ;
	}
}
