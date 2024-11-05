package com.ict.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ict.email.service.EmailService;
import com.ict.project.service.ProjectService;
import com.ict.project.vo.Paging;
import com.ict.project.vo.TourTalkVO;

@RestController
public class ProjectRestController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private Paging paging;
	@Autowired
	private EmailService eamilService;

	@RequestMapping(value = "/get_comment_data", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> getTTList(HttpServletRequest request) { // TT = tourtalk의 약자
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			
			int count = projectService.countData(userId);
			paging.setTotalCount(count);
			int numPerPage = paging.getNumPerPage();
			
			if (count <= numPerPage) {
				paging.setTotalPage(1);
			}else {
				int totalPage = (count / numPerPage);
				if ((count % numPerPage) == 0) {
					paging.setTotalPage(totalPage);
				}else {
					paging.setTotalPage(totalPage + 1);
				}
			}
			
			String nowPage = request.getParameter("nowPage");
		
			if (nowPage == null || nowPage.equals("undefined") || Integer.parseInt(nowPage) < 1) {
				paging.setNowPage(1);
			}else {
				paging.setNowPage(Integer.parseInt(nowPage));
			}
			
		
			paging.setOffset(numPerPage * (paging.getNowPage() - 1));
			paging.setStartBlock((int)((paging.getNowPage() - 1)) / paging.getBlockPerPage() * paging.getBlockPerPage() + 1);
			paging.setEndBlock(paging.getStartBlock() + paging.getBlockPerPage() - 1);
			
			if (paging.getEndBlock() > paging.getTotalPage()) {
				paging.setEndBlock(paging.getTotalPage());
			}

			List<TourTalkVO> list = projectService.getTTList(userId, paging.getOffset(), paging.getNumPerPage());
			
			if (list != null) { 
				for (TourTalkVO k : list) {  
					StringBuffer sb = new StringBuffer();
					sb.append(k.getTourTalkContent());
					if (sb.length() > 20) { // 댓글 내용이 일정 글자 수가 넘어가면 짜르고 '...'를 붙이자
						sb.setLength(20);
						sb.append("...");
						k.setTourTalkContent(sb.toString());
					}
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("paging", paging);
				return map;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	  @RequestMapping(value="/get_del_one", produces="text/plain; charset=utf-8")
	  @ResponseBody 
	  public String DeleteTTOne(String tourTalkIdx) {
	  try {
		  String result = projectService.delTTOne(tourTalkIdx);
		  return result;
	  } catch (Exception e) {
		System.out.println(e);
		return "error";
	  }
	  }
	  
	  @RequestMapping(value="/get_del_chked", produces="text/plain; charset=utf-8")
	  @ResponseBody 
	  public String getDeleteTTChked(@RequestParam("chkedIdx[]") String[] chkedIdx) {
		  try {
			  String result = projectService.delTTChked(chkedIdx);
			  
			  return result;
		  } catch (Exception e) {
			  System.out.println(e);
			  return "error";
		  }
	  }
	
	  @RequestMapping(value="/send_email_code", produces="text/plain; charset=utf-8")
	  @ResponseBody
	  public String sendCode(@RequestParam("userMail") String userMail, HttpSession session ) {
		  try {
			  System.out.println("레스트 컨트롤러 도착");
			  Random random = new Random();
				// 0 ~ 1000000 미만의 정수를 무작위로 생성(6자리 숫자 중 하나를 랜덤으로 만듦)
				String ranNum = String.valueOf(random.nextInt(1000000));
				
				// 길이가 6자리가 안 되면 0으로 채우자
				if (ranNum.length() < 6) {
					int substract = 6 - ranNum.length();
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < substract; i++) {
						sb.append("0");
					}
					sb.append(ranNum);
					ranNum = sb.toString();
				}
				session.setAttribute("emailCode", ranNum);
				eamilService.sendEmail(ranNum, userMail);
				return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	  }
	  @RequestMapping(value="/judge_code_match", produces="text/plain; charset=utf-8")
	  @ResponseBody
	  public String judgeCodeMatch(@RequestParam("u_emailCode") String u_emailCode, HttpSession session ) {
		  
		  String emailCode = (String) session.getAttribute("emailCode");
		  if (emailCode.equals(u_emailCode)) {
			  return "success";
		  }else {
			  return "fail" ;
		  }
	  }
	 
}
