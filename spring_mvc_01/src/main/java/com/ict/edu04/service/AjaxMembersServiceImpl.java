package com.ict.edu04.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu04.dao.AjaxMembersDAO;
import com.ict.edu04.vo.MembersVO;

@Service
public class AjaxMembersServiceImpl implements AjaxMembersService {

	@Autowired
	private AjaxMembersDAO ajaxMembersDAO;
	
	
	
	@Override
	public List<MembersVO> getMemberList() {
		
		return ajaxMembersDAO.getMemberList();
	}

	@Override
	public String getMemberIdChk(String u_id) {
		return ajaxMembersDAO.getMemberIdChk(u_id);
	}

	@Override
	public String getMemberInsert(MembersVO uvo) {
		
		return ajaxMembersDAO.getMemberInsert(uvo);
	}
	
	@Override
	public String getMemberDelete(String u_idx) {
		
		return ajaxMembersDAO.getMemberDelete(u_idx);
	}
	@Override
	public MembersVO getMemberDetail(String u_idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMemberUpdate(MembersVO uvo) {
		// TODO Auto-generated method stub
		return null;
	}


}
