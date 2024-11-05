package com.ict.edu04.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.ict.edu04.service.AjaxMembersService;
import com.ict.edu04.vo.MembersVO;

@RestController
public class AjaxDBController {
	
	@Autowired
	private AjaxMembersService ajaxMembersService;
	
	@RequestMapping(value="/ajax_db_list", produces="application/xml; charset=utf-8")
	@ResponseBody
	public String getAjaxList() {
		List<MembersVO> list = ajaxMembersService.getMemberList();
		
		if (list != null) {
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			sb.append("<members>");
			for (MembersVO k : list) {
				sb.append("<member>");
				sb.append("<u_idx>" + k.getU_idx() + "</u_idx>");
				sb.append("<u_id>" + k.getU_id() + "</u_id>");
				sb.append("<u_pw>" + k.getU_pw() + "</u_pw>");
				sb.append("<u_name>" + k.getU_name() + "</u_name>");
				sb.append("<u_age>" + k.getU_age() + "</u_age>");
				sb.append("<u_reg>" + k.getU_reg() + "</u_reg>");
				sb.append("</member>");
			}
			sb.append("</members>");
			
			return sb.toString();
		}
		
		return "error";

	}
	
	@RequestMapping(value="/ajax_db_list2", produces="application/json; charset=utf-8")
	@ResponseBody
	public String getAjaxList2() {
		// Spring에서 json을 만들거나 파싱할 때에는 여러가지 라이브러리를 사용할 수 있다.
		// 그 중 gson이라는 라이브러리를 사용하자=(pom.xml에 등록)
		List<MembersVO> list = ajaxMembersService.getMemberList();
		
		if (list != null) {
			Gson gson = new Gson();
			String jsonString = gson.toJson(list);
			return jsonString;
			}
		return "error";
		
	}

	
	@RequestMapping(value="/ajax_db_list3", produces="text/csv; charset=utf-8")
	@ResponseBody
	public String getAjaxList3() {

		List<MembersVO> list = ajaxMembersService.getMemberList();
		
		if (list != null) {
			// CSV 만들기
			StringBuffer sb = new StringBuffer();
			
			// 헤더 추가 (컬럼명)
			sb.append("u_idx, u_id, u_pw, u_name, u_age, u_reg \n");
			
			for (MembersVO k : list) {
				sb.append(k.getU_idx()).append(",")
				.append(k.getU_id()).append(",")
				.append(k.getU_pw()).append(",")
				.append(k.getU_name()).append(",")
				.append(k.getU_age()).append(",")
				.append(k.getU_reg()).append("\n");
			}
			return sb.toString();
			
		}
		System.out.println("error");
		return "error";
		
	}
	

	@RequestMapping(value="/ajax_id_chk", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String getAjaxIdChk(HttpServletRequest request) {

		String u_id = request.getParameter("u_id");
		String result = ajaxMembersService.getMemberIdChk(u_id);
		
		return result ;
		
	}
	
	@RequestMapping(value="/ajax_member_join", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String getAjaxMemberJoin(MembersVO uvo) {
		String result = ajaxMembersService.getMemberInsert(uvo);
		
		return result ;
		
	}
	
	@RequestMapping(value="/ajax_member_delete", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String getAjaxMemberDelete(@RequestParam("u_idx") String u_idx) {
		String result = ajaxMembersService.getMemberDelete(u_idx);
		
		return result ;
		
	}

	
	
	
}
