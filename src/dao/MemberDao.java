package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.DBService;
import vo.MemberVo;

public class MemberDao {

	//Single-ton pattern : 1���� ��ü�� 
	//                     �����ؼ� ��������
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
			//1.Connection ������
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ������
			pstmt = conn.prepareStatement(sql);

			//2-1.pstmt parameter setting
            pstmt.setString(1, id);
			//3.�����ó����ü ������
			rs = pstmt.executeQuery();

			if (rs.next()) {
				//1���� ���ڵ带 ������ ��ü
				vo = new MemberVo();

				//���緹�ڵ�(rs)�� �ʵ尪�� ���ͼ�->VO�� �ִ´�
				vo.setM_index(rs.getInt("m_index"));
				vo.setM_name(rs.getString("m_name"));
				vo.setM_id(rs.getString("m_id"));
				vo.setM_pwd(rs.getString("m_pwd"));
				vo.setM_phone_num(rs.getString("m_phone_num"));
				vo.setM_gender(rs.getString("m_gender"));
				vo.setM_age(rs.getString("m_age"));

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				//���� �������� �ݱ�
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
        //VALUES(#{ m_id },#{ m_pwd },#{ m_name },#{ m_phone_num },#{ m_gender },#{ m_age })
		String sql = "INSERT INTO member(m_id,m_pwd,m_name,m_phone_num,m_gender,m_age)" +
                     "VALUES(?,?,?,?,?,?)";

		try {
			//1.Connection���
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ���
			pstmt = conn.prepareStatement(sql);
			//3.pstmt�� parameter setting
            pstmt.setString(1,vo.getM_id());
            pstmt.setString(2, vo.getM_pwd());
            pstmt.setString(3, vo.getM_name());
            pstmt.setString(4, vo.getM_phone_num());
            pstmt.setString(5, vo.getM_gender());
            pstmt.setString(6, vo.getM_age());
                        
			//4.DB ����			
			res = pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {

			try {
				//���� �������� �ݱ�
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
	
	
}
