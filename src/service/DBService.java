package service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBService {
	
	//JNDI기법으로 생성된 자원정보 얻기위한 객체
	DataSource ds;
	//Single-ton pattern : 1개의 객체만 
	//                     생성해서 서비스하자
	static DBService single = null;

	public static DBService getInstance() {
		if (single == null)
			single = new DBService();

		return single;
	}

	public DBService() {
		// TODO Auto-generated constructor stub
		
		try {
			//1.JNDI자원을 관리(획득)하는 객체
			InitialContext ic = new InitialContext();
			
			//2.Context정보를 획득(lookup)
			Context  ctx = (Context) ic.lookup("java:comp/env");
			
			//3.Context내의 자원(DataSource)정보를 획득
			ds = (DataSource) ctx.lookup("jdbc/ycm111");
			
			//cf)한꺼번에 이렇게 해도 된다
			//ds = (DataSource) ic.lookup("java:comp/env/jdbc/oracle_test");
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	
	
	
	
}
