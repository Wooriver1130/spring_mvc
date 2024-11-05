package com.ict.edu03.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ict.edu03.vo.FileVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class FileController {

	@GetMapping("/f_up1")
	public ModelAndView fileGetUp() {
		return new ModelAndView("day03/error");
	}
	
	// cos 라이브러리 사용
	@PostMapping("/f_up1")
	public ModelAndView fileUP(HttpServletRequest request, HttpServletResponse response) {
		try {
			ModelAndView mav = new ModelAndView("day03/result1");
			// 실전에서는 프로젝트가 아닌 다른 곳에 저장 할 수도 있다.(AWS의 S3나 서버의 특정위치)
			String path = request.getSession().getServletContext().getRealPath("/resources/upload");
			// cos 라이브러리를 사용하면 request를 사용하는게 아니다.(cos를 지원하는 것을 써야한다.)
			MultipartRequest mr = new MultipartRequest(  // com.orilley.servlet
					request, // request 대신하기 위해서 request를 받는다.
					path, 	// 저장위치
					500*1024*1024, 	// 최대 업로드 용량 크기설정: 500MB     
					// 1024(KB) * 1024(MB) * 1024(GB) 1024(TB)
					"utf-8", // 한글처리
					new DefaultFileRenamePolicy()); // 같은 이름의 파일이 있을 떄 이름뒤에 숫자를 붙여서 업로드한다. => 그래서 rename
			
			//  파라미터를 request로  받을때 name: null 나옴
			/*
			  String name = request.getParameter("name"); 
			  System.out.println("name: " + name);   */
			
			// mr을 사용하기
			String name = mr.getParameter("name");
			String f_name = mr.getFilesystemName("f_name"); // 저장 당시 이름
			String file_type = mr.getContentType("f_name");
			
			// 해당 파일정보 추출
			File file = mr.getFile("f_name"); // java.io
		// 	long size = file.length(); // 파일 크기를 byte로 나타냄
			long size = file.length()/1024; // 파일 크기를KB로 나타냄\
			

			// 최종수정날짜
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss E");
			String lastday = sdf.format(file.lastModified()); 
			
			mav.addObject("name", name);
			mav.addObject("f_name", f_name);
			mav.addObject("file_type", file_type);
			mav.addObject("size", size);
			mav.addObject("lastday", lastday);
			
			return mav ;
		}catch (Exception e) {
			System.out.println(e);
			return  new ModelAndView("day03/error");
		}
	}
		@GetMapping("/down")
		public void  FileDown(HttpServletRequest request, HttpServletResponse response) {
			FileInputStream fis = null ;
			BufferedInputStream bis = null ;
			BufferedOutputStream bos = null ;
			try {
				String f_name = request.getParameter("f_name");
				String path = request.getSession().getServletContext().getRealPath("/resources/upload/" + f_name);
				String r_path = URLEncoder.encode(f_name, "utf-8");
				
				// 브라우저 설정
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + r_path);
				
				// 실제 다운로드 하기
				File file = new File(new String(path.getBytes(), "utf-8"));
				int b ;
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				bos = new BufferedOutputStream(response.getOutputStream());
				while ((b = bis.read()) != -1) {
					bos.write(b);
					bos.flush();
				}
			} catch (Exception e) {
				System.out.println(e);
			}finally {
				try {
					bos.close();
					bis.close();
					fis.close();
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
		}

		@PostMapping("/f_up2")
		public ModelAndView fileUp2(
				@RequestParam("f_name") MultipartFile f_name, 
				HttpServletRequest request) {
			try {
				ModelAndView mav = new ModelAndView("day03/result1");
				String path = request.getSession().getServletContext().getRealPath("resources/upload");
				// 파일 업로드 할떄의 이름만 존재한다. => 같은 이름의 파일을 업로드하면 업로드 안됨(단점)
				// 해결책: 파일이름 뒤에 id와 업로드 날짜를 붙인다.
				// 아니면 UUID를 생성해서 붙인다.(UUID: Universally Unique IDentifier의 약자로, 전세계에 하나밖에 없는 ID이다.)
				UUID uuid = UUID.randomUUID();
				// f_name은 파일 객체, fname은 파일이름
				String fname = uuid.toString() +"_"+ f_name.getOriginalFilename();
				
				System.out.println(f_name);
				System.out.println(f_name.getOriginalFilename());
				
				String file_type = f_name.getContentType();
				long size = f_name.getSize()/1024;
				
				
				// 실제로 올리는 작업을 하자(FileCopyUtils.copy(byte[] in , File  out)) => byte타입 배열을 지정한 File에 복사한다.
				// 올린 파일을 byte[] 로 만듦
				byte[] in  = f_name.getBytes();
				
				// 업로드할 장소와 저장이름
				File out = new File(path, fname);
				
				// 파일 복사(upload = down)
				FileCopyUtils.copy(in, out);
				
				String name = request.getParameter("name");
				
				
				long lastModified = out.lastModified();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss E");
				String lastday = sdf.format(lastModified); 
				
				
				mav.addObject("name", name);
				mav.addObject("f_name", fname);
				mav.addObject("file_type", file_type);
				mav.addObject("size", size);
				mav.addObject("lastday", lastday);
				
				return mav;
			} catch (Exception e) {
				System.out.println(e);
				return new ModelAndView("day03/error");			
				}
		}
		
		@GetMapping("/down2")
		public void fileDown2(HttpServletRequest request, HttpServletResponse response) {
			try {
				String f_name = request.getParameter("f_name");
				String path = request.getSession().getServletContext().getRealPath("/resources/upload/" + f_name);
				
				// 한글처리
				String r_path = URLEncoder.encode(f_name, "UTF-8");
				// 브라우저 설정
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + r_path);
				
				// byte[] in 
				File file = new File(new String(path.getBytes(), "UTF-8"));
				FileInputStream in = new FileInputStream(file);
				
				// File out
				OutputStream out = response.getOutputStream();
				
				FileCopyUtils.copy(in, out);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		@GetMapping("/f_up3")
		public ModelAndView fileGetUp3() {
			return new ModelAndView("day03/error");
		}
		
		@PostMapping("/f_up3")
		public ModelAndView fileUp3(
				FileVO fvo,  // 2번에서 바뀐코드
				HttpServletRequest request) {
			try {
				ModelAndView mav = new ModelAndView("day03/result1");
				String path = request.getSession().getServletContext().getRealPath("resources/upload");
				
				UUID uuid = UUID.randomUUID();
				MultipartFile f_name = fvo.getF_name(); // 바뀐 코드
				String fname = uuid.toString() +"_"+ f_name.getOriginalFilename();
				
				System.out.println(f_name);
				System.out.println(f_name.getOriginalFilename());
				
				String file_type = f_name.getContentType();
				long size = f_name.getSize()/1024;
				
				
				// 실제로 올리는 작업을 하자(FileCopyUtils.copy(byte[] in , File  out)) => byte타입 배열을 지정한 File에 복사한다.
				// 올린 파일을 byte[] 로 만듦
				byte[] in  = f_name.getBytes();
				
				// 업로드할 장소와 저장이름
				File out = new File(path, fname);
				
				// 파일 복사(upload = down)
				FileCopyUtils.copy(in, out);
				
				String name = fvo.getName();
				
				long lastModified = out.lastModified();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss E");
				String lastday = sdf.format(lastModified); 
				
				mav.addObject("name", name);
				mav.addObject("f_name", fname);
				mav.addObject("file_type", file_type);
				mav.addObject("size", size);
				mav.addObject("lastday", lastday);
				
				return mav;
			} catch (Exception e) {
				System.out.println(e);
				return new ModelAndView("day03/error");			
				}
		}
		
		
	}

