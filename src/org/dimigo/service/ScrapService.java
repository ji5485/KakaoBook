package org.dimigo.service;

import java.sql.Connection;
import java.util.List;

import org.dimigo.dao.ScrapDao;
import org.dimigo.vo.ScrapVO;

public class ScrapService extends AbstractService {

	public void scrap(ScrapVO vo) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			ScrapDao dao = new ScrapDao(conn);
			ScrapVO result = dao.searchScrapByLink(vo);
			
			if (result != null) throw new Exception("이미 스크랩한 글입니다.");
			
			dao.insertScrap(vo);
		} finally {
			if (conn != null) conn.close();
		}
	}
	
	public List<ScrapVO> searchScrap(String nickname) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			ScrapDao dao = new ScrapDao(conn);
			List<ScrapVO> result = dao.searchScrapList(nickname);
			
			return result;
		} finally {
			if (conn != null) conn.close();
		}
	}
	
	public void removeScrap(String link) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			ScrapDao dao = new ScrapDao(conn);
			dao.removeScrap(link);
		} finally {
			if (conn != null) conn.close();
		}
	}
	
	public void removeScrapAll(String nickname) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			ScrapDao dao = new ScrapDao(conn);
			dao.removeScrapAll(nickname);
		} finally {
			if (conn != null) conn.close();
		}
	}
}
