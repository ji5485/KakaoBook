package org.dimigo.service;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public abstract class AbstractService {
	
	public Connection getConnection() throws Exception {
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql");
			return dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("데이터베이스 연결에 실패했습니다.");
		}
	}
}
