package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.DBService;
import vo.MemberVo;

public class MemberDao {

	//Single-ton pattern : 1개의 객체만 
	//                     생성해서 서비스하자
	static MemberDao single = null;

	public static MemberDao getInstance() {
		if (single == null)
			single = new MemberDao();

		return single;
	}

	public MemberDao() {
		// TODO Auto-generated constructor stub

	}
	
	public MemberVo selectOne(String id) {

		MemberVo vo = null;
		//                                                
		String sql = "select * from member where m_id=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//1.Connection 얻어오기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻어오기
			pstmt = conn.prepareStatement(sql);

			//2-1.pstmt parameter setting
            pstmt.setString(1, id);
			//3.결과행처리객체 얻어오기
			rs = pstmt.executeQuery();

			if (rs.next()) {
				//1개의 레코드를 저장할 객체
				vo = new MemberVo();

				//현재레코드(rs)의 필드값을 얻어와서->VO에 넣는다
				vo.setM_index(rs.getInt("m_index"));
				vo.setM_name(rs.getString("m_name"));
				vo.setM_id(rs.getString("m_id"));
				vo.setM_pwd(rs.getString("m_pwd"));
				vo.setM_phone_num(rs.getString("m_phone_num"));
				vo.setM_hint(rs.getString("m_hint"));
				

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				//열린 역순으로 닫기
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	}

	public int insert(MemberVo vo) {
		// TODO Auto-generated method stub
		int res = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		//INSERT INTO member(m_id,m_pwd,m_name,m_phone_num,m_gender,m_age) 
        
		String sql = "INSERT INTO member(m_id,m_pwd,m_name,m_phone_num,m_hint)" +
                     "VALUES(?,?,?,?,?)";

		try {
			//1.Connection얻기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻기
			pstmt = conn.prepareStatement(sql);
			//3.pstmt의 parameter setting
            pstmt.setString(1,vo.getM_id());
            pstmt.setString(2, vo.getM_pwd());
            pstmt.setString(3, vo.getM_name());
            pstmt.setString(4, vo.getM_phone_num());
            pstmt.setString(5, vo.getM_hint());
            
                        
			//4.DB 전송			
			res = pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				//열린 역순으로 닫기
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public MemberVo selectOneFind(String m_phone_num) {

		MemberVo vo = null;
		//                                                
		String sql = "select * from member where m_phone_num=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//1.Connection 얻어오기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻어오기
			pstmt = conn.prepareStatement(sql);

			//2-1.pstmt parameter setting
            pstmt.setString(1, m_phone_num);

			//3.결과행처리객체 얻어오기
			rs = pstmt.executeQuery();

			if (rs.next()) {
				//1개의 레코드를 저장할 객체
				vo = new MemberVo();

				//현재레코드(rs)의 필드값을 얻어와서->VO에 넣는다
				vo.setM_index(rs.getInt("m_index"));
				vo.setM_name(rs.getString("m_name"));
				vo.setM_id(rs.getString("m_id"));
				vo.setM_pwd(rs.getString("m_pwd"));
				vo.setM_phone_num(rs.getString("m_phone_num"));
				vo.setM_hint(rs.getString("m_hint"));
				

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				//열린 역순으로 닫기
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	}
	
}
