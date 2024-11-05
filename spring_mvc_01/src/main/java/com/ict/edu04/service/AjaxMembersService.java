package com.ict.edu04.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ict.edu04.vo.MembersVO;


public interface AjaxMembersService {
	public List<MembersVO> getMemberList();
	public String getMemberIdChk(String u_id);
	public MembersVO getMemberDetail(String u_idx);
	public String getMemberInsert(MembersVO uvo);
	public String getMemberDelete(String u_idx);
	public String getMemberUpdate(MembersVO uvo);
	
}
