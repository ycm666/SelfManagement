package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;
import vo.GroupListVo;

public class GroupListDao {

	//Single-ton pattern : 1���� ��ü�� 
	//                     �����ؼ� ��������
	static GroupListDao single = null;

	public static GroupListDao getInstance() {
		if (single == null)
			single = new GroupListDao();

		return single;
	}

	public GroupListDao() {
		// TODO Auto-generated constructor stub

	}
	
	public List<GroupListVo> selectList(int m_index) {

		List<GroupListVo> list = new ArrayList<GroupListVo>();
		String sql = "select * from group_list where m_index=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//1.Connection ������
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ������
			pstmt = conn.prepareStatement(sql);
			
			//2-1.pstmt setting
			pstmt.setInt(1, m_index);

			//3.�����ó����ü ������
			rs = pstmt.executeQuery();
			//ó��~������ �ݺ��ض�..
			while (rs.next()) {
				//1���� ���ڵ带 ������ ��ü
				GroupListVo vo = new GroupListVo();

				//���緹�ڵ�(rs)�� �ʵ尪�� ���ͼ�->VO�� �ִ´�
				//vo�� �ֱ�
				vo.setG_index(rs.getInt("g_index"));
				vo.setG_subject(rs.getString("g_subject"));
				vo.setM_index(rs.getInt("m_index"));
				vo.setC_index(rs.getInt("c_index"));
				
				//ArrayList�߰�
				list.add(vo);
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
		return list;
	}
	
	
	public int insert(GroupListVo vo) {
		// TODO Auto-generated method stub
		int res = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into group_list(g_subject,m_index,c_index) values(?,?,?)";

		try {
			//1.Connection���
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ���
			pstmt = conn.prepareStatement(sql);
			//3.pstmt�� parameter setting
            pstmt.setString(1, vo.getG_subject());
            pstmt.setInt(2, vo.getM_index());
            pstmt.setInt(3, vo.getC_index());
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
