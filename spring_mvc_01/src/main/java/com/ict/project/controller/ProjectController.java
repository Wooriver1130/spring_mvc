package com.ict.project.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.ict.project.service.ProjectService;
import com.ict.project.vo.NaverUserResponse;
import com.ict.project.vo.NaverVO;
import com.ict.project.vo.UserVO;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/go_main")
	public ModelAndView goMain(HttpSession session) {
		ModelAndView mav = new ModelAndView("project_view/main_page");
		session.removeAttribute("identityChk");
		return mav ;
	}
	
	@RequestMapping("/go_my_page")
	public ModelAndView goMyPage(@ModelAttribute("isOk") String isOk) {
		ModelAndView mav = new ModelAndView("project_view/MEM_myPage");
		
		return mav ;
	}
	
	@GetMapping("/go_identify")
	public ModelAndView goIdentify(@ModelAttribute("cmd") String cmd, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
			if (session.getAttribute("identityChk") == "ok") {
				mav.setViewName("redirect:/" + cmd);
			}else {
				session.setAttribute("userId",  "testid");
				String userId = (String) session.getAttribute("userId");
				mav.addObject("userId", userId);
				mav.setViewName("project_view/MEM_myPage_identityCheck");
			}
			
		} catch (Exception e) {
			System.out.println(e);
			mav.setViewName("project_view/error");
		}
		return mav;
	}
	
	@PostMapping("go_pw_chk")
	public ModelAndView goPwChk(UserVO uvo, @RequestParam("cmd") String cmd, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
			String pw = uvo.getUserPw();
			
			String userId = uvo.getUserId();
			UserVO dbUvo = projectService.getUserDetail(userId);
			
			String dbPw = dbUvo.getUserPw();
	//		if (passwordEncoder.matches(pw, dbPw)) { 
			if (true) { // 임시코드임. 추후에 위 코드로 변경
				mav.setViewName("redirect:/" + cmd);
				session.setAttribute("identityChk", "ok");
			}else {
				mav.setViewName("project_view/MEM_myPage_identityCheck");
				mav.addObject("pwChk", false);
			}
		} catch (Exception e) {
			System.out.println(e);
			mav.setViewName("project_view/error");
		}
		return mav ;
	}
	
	
	
	@GetMapping("/go_my_comment")
	public ModelAndView goMyComment() {
		ModelAndView mav = new ModelAndView("project_view/MEM_myPage_myComment");
		
		 return mav ;
	}
	
	@GetMapping("/go_update")
	public ModelAndView goUpdate(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		 String userId =  (String) request.getSession().getAttribute("userId");
		 // 이름, 아이디, 이메일, 주소, 관심지역 123
		try {
			UserVO detail = projectService.getUserDetail(userId);
			mav.addObject("detail", detail);
			mav.setViewName("project_view/MEM_myPage_update");
		} catch (Exception e) {
			System.out.println(e);
			mav.setViewName("project_view/error");
		}
		return mav ;
	}
	
	@GetMapping("/go_pw_change")
	public ModelAndView goPwChange() {
		ModelAndView mav = new ModelAndView("project_view/MEM_myPage_changePw");
		
		return mav ;
	}
	
	@GetMapping("/go_user_out")
	public ModelAndView goUserOut() {
		ModelAndView mav = new ModelAndView("project_view/MEM_myPage_userOut");
		
		return mav ;
	}
	
	@RequestMapping("/go_update_ok")
	public ModelAndView goUpdateOK(UserVO uvo) {
		ModelAndView mav = new ModelAndView();
		try {
			String result = projectService.getUserUpdate(uvo);
			if (result != "0") {
				mav.setViewName("redirect:/go_my_page?isOk=yes");
			}else{
				mav.setViewName("project_view/error");
			}
		} catch (Exception e) {
			System.out.println(e);
			mav.setViewName("project_view/error");
		}
		return mav;
	}
	@PostMapping("/go_pw_change_ok")
	public ModelAndView goPwChangeOK(String userPw, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
		String userId = (String) session.getAttribute("userId");
		String encodePw = passwordEncoder.encode(userPw);
		
		UserVO uvo = new UserVO();
		uvo.setUserPw(encodePw);
		uvo.setUserId(userId);
		
		int result = projectService.getChangePw(uvo);
		if (result > 0) {
			mav.setViewName("redirect:/go_my_page?isOk=yes");
		}else {
			mav.setViewName("project_view/error");
		}
		} catch (Exception e) {
			System.out.println(e);
			mav.setViewName("project_view/error");
		}
		return mav ;
	}
	
	@PostMapping("/go_user_out_ok")
	public ModelAndView goUserOutOK(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
			String userId = (String) session.getAttribute("userId");
			int result = projectService.getUserOut(userId);
			if (result > 0) {
				mav.setViewName("redirect:/go_main?isOk=yes");
				session.invalidate();
			}else {
				mav.setViewName("project_view/error");
			}
		} catch (Exception e) {
			System.out.println(e);
			mav.setViewName("project_view/error");
		}
		return mav ;
	}
	
	@PostMapping("/judge_user_email")
	public ModelAndView judgeUserEmail(UserVO uvo) {
		ModelAndView mav = new ModelAndView();
		try {
			UserVO result = projectService.judgeUserEmail(uvo.getUserMail());
			if (result == null) {
				mav.addObject("isUsable", true);
				mav.addObject("detail", uvo);
			}else {
				mav.addObject("isUsable", false);
				mav.addObject("detail", uvo);
			}
			mav.setViewName("project_view/MEM_myPage_update");
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("project_view/error");
		}
		return mav ;
	}
	
	@GetMapping("/project_login")
	public ModelAndView goLogin() {
		return new ModelAndView("project_view/login");
	}
	
	@GetMapping("/ict5_naverlogin")
	public ModelAndView naverLogin(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		
		String requestURL = "https://nid.naver.com/oauth2.0/token";
		try {
			URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuffer sb = new StringBuffer();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=LATuMwgCg7IIRlZG9lKz");
			sb.append("&client_secret=AGqNgwJlZT");
			sb.append("&code=" + code);
			sb.append("&state=" + state);
			bw.write(sb.toString());
			bw.flush();
			
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = "";
				StringBuffer sb2 = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb2.append(line);
				}
				String result = sb2.toString();
				System.out.println(result);
				
				Gson gson = new Gson();
				NaverVO nvo = gson.fromJson(result, NaverVO.class);
				String access_token = nvo.getAccess_token();
				
				String requestURL2 = "https://openapi.naver.com/v1/nid/me";
				HttpURLConnection conn2 = null ;
				BufferedReader br2 = null ;
				try {
					URL url2 = new URL(requestURL2);
					conn2 = (HttpURLConnection) url2.openConnection();
					
					conn2.setRequestMethod("POST");
					conn2.setDoOutput(true);
					
					conn2.setRequestProperty("Authorization", "Bearer " + access_token);
					
					int responseCode2 = conn2.getResponseCode();
					if (responseCode2 == HttpURLConnection.HTTP_OK) {
						br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
						
						String line2 = "";
						StringBuffer sb3 = new StringBuffer();
						while ((line2 = br2.readLine()) != null) {
							sb3.append(line2);
						}
						Gson gson2 = new Gson();
						NaverUserResponse nvo2 = gson2.fromJson(sb3.toString(), NaverUserResponse.class);
						
					NaverUserResponse result2 = projectService.selectNaverUserOne(nvo2.getResponse().getId());
					if (result2 != null) {
						mav.setViewName("redirect:/go_main");
						request.getSession().setAttribute("userId", nvo2.getResponse().getId());
						request.getSession().setAttribute("loginChk", "ok");
						
					}else {
						int result3 = projectService.getInsertNaverUser(nvo2);
						if (result3 > 0) {
							mav.setViewName("redirect:/go_main");
							request.getSession().setAttribute("userId", nvo2.getResponse().getId());
							request.getSession().setAttribute("loginChk", "ok");
						}else {
							mav.setViewName("project_view/login");
							mav.addObject("loginFail", true);
						}
					}
					}
				} catch (Exception e) {
					e.printStackTrace();
					mav.setViewName("project_view/error");
				}
			}else {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				String line = "";
				StringBuffer sbError = new StringBuffer();
				while ((line = br.readLine()) != null) {
					sbError.append(line);
				}
				System.out.println("Error response: " + sbError.toString());
				mav.setViewName("project_view/error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("project_view/error");
		}
		return mav;
	}
	
}
