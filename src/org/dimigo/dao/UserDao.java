package org.dimigo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.dimigo.vo.UserVO;

public class UserDao {
	
	private Connection conn = null;
	
	public UserDao(Connection conn) {
		this.conn = conn;
	}
	
	public UserVO searchUser(UserVO vo) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM USER WHERE ID=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getNickname());
			
			rs = pstmt.executeQuery();
			
			UserVO result = null;
			
			if (rs.next()) {
				result = new UserVO();
				result.setNickname(rs.getString(1));
				result.setAge(rs.getInt(2));
				result.setGender(rs.getString(3));
				result.setFavorite(rs.getString(4));
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("사용자 조회 시 오류가 발생했습니다.");
		} finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
	}

	public UserVO searchUserByNickname(UserVO vo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM KAKAOBOOK.USER WHERE NICKNAME=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getNickname());
			
			rs = pstmt.executeQuery();
			
			UserVO result = null;
						
			if (rs.next()) {
				result = new UserVO();
				result.setNickname(rs.getString(1));
				result.setAge(rs.getInt(2));
				result.setGender(rs.getString(3));
				result.setFavorite(rs.getString(4));
			} 
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("사용자 조회 시 오류가 발생했습니다.");
		} finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
	}

	public void insertUser(UserVO vo) throws Exception {
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO KAKAOBOOK.USER VALUES(?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getNickname());
			pstmt.setInt(2, vo.getAge());
			pstmt.setString(3, vo.getGender());
			pstmt.setString(4, vo.getFavorite());
			
			// executeQuery : SELECT
			// executeUpdate : INSERT / UPDATE / DELETE
			int cnt = pstmt.executeUpdate();
			
			if (cnt == 0) throw new Exception("사용자 등록에 실패하였습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("사용자 등록 시 오류가 발생했습니다.");
		} finally {
			if (pstmt != null) pstmt.close();
		}
	}
	
	public void removeUser(String nickname) throws Exception {
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM KAKAOBOOK.USER WHERE NICKNAME=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("가입 탈퇴 시 오류가 발생했습니다.");
		} finally {
			if (pstmt != null) pstmt.close();
		}
	}
	
}
