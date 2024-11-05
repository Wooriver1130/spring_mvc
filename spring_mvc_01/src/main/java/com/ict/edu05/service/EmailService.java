package com.ict.edu05.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	// Controller에서 호출할 메소드 생성
	public void sendEmail(String ranNum, String mailAddr) {
		try {
			EmailHandler sendMail = new EmailHandler(javaMailSender);
			
			// 메일 제목
			sendMail.setSubject("[ICT EDU 인증 메일 입니다.]");
			
			// 내용
			sendMail.setText("<table><tbody>"
					+ "<tr><td><h2>ICT EDU 메일 인증 </h2></td></tr>"
					+ "<tr><td><h3>ICT EDU </h3></td></tr>"
					+ "<tr><td><font size='8px'>인증번호 안내</font></td></tr>"
					+ "<tr><td><font size='10px'>인증번호: " + ranNum +  "</font></td></tr>"
					+ "</tbody></table>");
			
			// 보낸 사람의 이메일과 제목
			sendMail.setForm("ictedu@gmail.com", "ict 관리자");
			
			// 받는 이메일
			sendMail.setTo(mailAddr);
			sendMail.send();
		} catch (Exception e) {
			System.out.println(e);
			
		}
	}
	
}
