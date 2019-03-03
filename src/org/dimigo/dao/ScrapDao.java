package org.dimigo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.dimigo.vo.ScrapVO;

public class ScrapDao {
		
	private Connection conn = null;
		
	public ScrapDao(Connection conn) {
		this.conn = conn;
	}
	
	public ScrapVO searchScrapByLink(ScrapVO vo) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM KAKAOBOOK.SCRAP WHERE NICKNAME=? AND LINK=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getNickname());
			pstmt.setString(2, vo.getLink());
			
			rs = pstmt.executeQuery();
			
			ScrapVO result = null;
						
			if (rs.next()) {
				result = new ScrapVO();
				result.setNickname(rs.getString(1));
				result.setTitle(rs.getString(2));
				result.setBlogger(rs.getString(3));
				result.setDate(rs.getString(4));
				result.setLink(rs.getString(5));
			} 
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("스크랩 조회 시 오류가 발생했습니다.");
		} finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
	}
	
	public List<ScrapVO> searchScrapList(String nickname) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM KAKAOBOOK.SCRAP WHERE NICKNAME=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			
			rs = pstmt.executeQuery();
			
			ScrapVO result = null;
			List<ScrapVO> list = new ArrayList<>();
			
			while (rs.next()) {
				result = new ScrapVO();
				result.setNickname(rs.getString(1));
				result.setTitle(rs.getString(2));
				result.setBlogger(rs.getString(3));
				result.setDate(rs.getString(4));
				result.setLink(rs.getString(5));
				
				list.add(result);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("스크랩 목록 조회 시 오류가 발생했습니다.");
		} finally {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
		}
	}
	
	public void insertScrap(ScrapVO vo) throws Exception {
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO KAKAOBOOK.SCRAP VALUES(?, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getNickname());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getBlogger());
			pstmt.setString(4, vo.getDate());
			pstmt.setString(5, vo.getLink());
			
			int cnt = pstmt.executeUpdate();
			
			if (cnt == 0) throw new Exception("스크랩 등록에 실패하였습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("스크랩 등록 시 오류가 발생했습니다.");
		} finally {
			if (pstmt != null) pstmt.close();
		}
	}
	
	public void removeScrap(String link) throws Exception {
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM KAKAOBOOK.SCRAP WHERE LINK=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, link);
			
			int cnt = pstmt.executeUpdate();
			
			if (cnt == 0) throw new Exception("스크랩 삭제에 실패하였습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("스크랩 삭제 시 오류가 발생했습니다.");
		} finally {
			if (pstmt != null) pstmt.close();
		}
	}
	
	public void removeScrapAll(String nickname) throws Exception {
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM KAKAOBOOK.SCRAP WHERE NICKNAME=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("스크랩 삭제 시 오류가 발생했습니다.");
		} finally {
			if (pstmt != null) pstmt.close();
		}
	}
	
}
