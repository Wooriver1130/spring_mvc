package com.ict.project.dao;

import java.util.List;

import com.ict.project.vo.NaverUserResponse;
import com.ict.project.vo.TourTalkVO;
import com.ict.project.vo.UserVO;

public interface ProjectDAO {
	public String getUserUpdate(UserVO uvo) throws Exception;
	public List<TourTalkVO> getTTList(String userId, int offset, int limit) throws Exception;
	public String delTTOne(String tourTalkIdx) throws Exception;
	public String delTTChked(String[] chkedIdx) throws Exception;
	public UserVO getUserDetail(String userId) throws Exception;
	public int countData(String userId) throws Exception;
	public int getChangePw(UserVO uvo) throws Exception;
	public int getUserOut(String Userid) throws Exception;
	public UserVO judgeUserEmail(String userMail) throws Exception; 
	public NaverUserResponse selectNaverUserOne(String n_userId) throws Exception;
	public int getInsertNaverUser(NaverUserResponse nvo2) throws Exception;


	
	
	 
}
