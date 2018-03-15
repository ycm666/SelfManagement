package service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBService {
	
	//JNDI������� ������ �ڿ����� ������� ��ü
	DataSource ds;
	//Single-ton pattern : 1���� ��ü�� 
	//                     �����ؼ� ��������
	static DBService single = null;

	public static DBService getInstance() {
		if (single == null)
			single = new DBService();

		return single;
	}

	public DBService() {
		// TODO Auto-generated constructor stub
		
		try {
			//1.JNDI�ڿ��� ����(ȹ��)�ϴ� ��ü
			InitialContext ic = new InitialContext();
			
			//2.Context������ ȹ��(lookup)
			Context  ctx = (Context) ic.lookup("java:comp/env");
			
			//3.Context���� �ڿ�(DataSource)������ ȹ��
			ds = (DataSource) ctx.lookup("jdbc/ycm111");
			
			//cf)�Ѳ����� �̷��� �ص� �ȴ�
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
