package com.ict.edu05.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ict.edu05.service.EmpService;
import com.ict.edu05.vo.EmpVO;

@Controller
public class EmpController {
	
	@Autowired
	private EmpService empservice;

	@RequestMapping("/emp") // 인덱스에서 emp_form으로 가자
	public ModelAndView emp_form() {
		return new ModelAndView("day05/emp_form");
	}
	
	@PostMapping("/emp_getlist") // 전체보기 클릭 시  emp_list 로 가자
	public ModelAndView emp_list() {
		ModelAndView mav = new ModelAndView("day05/emp_list");
		try { // DAO부터  throw  한것들 try~catch로 처리하자.
			List<EmpVO> list = empservice.getList();
			mav.addObject("list", list);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return  mav ;
	}
	@PostMapping("/emp_search")
	public ModelAndView emp_search(@RequestParam("deptno") String deptno) {
		ModelAndView mav = new ModelAndView("day05/emp_search");
		try { 
			List<EmpVO> list = empservice.getSearch(deptno);
			mav.addObject("list", list);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return  mav ;
	}
	 // 파라미터가 vo에 존재하지 않을 경우 처리방법 
//	1: vo에 넣는다.
//	@PostMapping("/emp_dynamic_search")
//	public ModelAndView emp_dynamic_search(EmpVO empvo) {
//		try { 
//			ModelAndView mav = new ModelAndView("day05/emp_dynamic");
//			List<EmpVO> list = empservice.getSearch(empvo);
//			mav.addObject("list", list);
//			mav.addObject("idx", empvo.getIdx());
//			
//			
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//			return  mav ;
//	}

	// 2. 별도로 받아서 나중에 Map으로 처리한다.
	@PostMapping("/emp_dynamic_search")			// 받은 idx와 keyword를 다음 jsp 로 그대로 넘기기 위해서
	public ModelAndView emp_dynamic_search(@ModelAttribute("idx") String idx, @ModelAttribute("keyword") String keyword) {
		ModelAndView mav = new ModelAndView("day05/emp_dynamic");
		try { 
			List<EmpVO> list = empservice.getSearch(idx, keyword);
			mav.addObject("list", list);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return  mav ;
	}
	
}
