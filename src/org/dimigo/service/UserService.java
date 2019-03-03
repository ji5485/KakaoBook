package org.dimigo.service;

import java.sql.Connection;

import org.dimigo.dao.UserDao;
import org.dimigo.vo.UserVO;

public class UserService extends AbstractService {
	
	public UserVO login(UserVO user) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			UserDao dao = new UserDao(conn);
			UserVO result = dao.searchUserByNickname(user);
			
			System.out.println(result);
			
			if (result == null) throw new Exception("Invalid Username or Password");
						
			return result;
		} finally {
			if (conn != null) conn.close();
		}
	}
	
	public void signup(UserVO user) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			UserDao dao = new UserDao(conn);
			
			// 사용중인 아이디인지 체크
			UserVO result = dao.searchUserByNickname(user);
						
			if (result != null) {
				throw new Exception("이미 사용중인 아이디입니다.");
			}
			
			// 사용자 등록
			dao.insertUser(user);
			
		} finally {
			if (conn != null) conn.close();
		}
	}
	
	public void removeUser(String nickname) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			UserDao dao = new UserDao(conn);
			
			// 사용중인 아이디인지 체크
			dao.removeUser(nickname);
		} finally {
			if (conn != null) conn.close();
		}
	}
}
