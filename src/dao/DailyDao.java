package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;
import vo.DailyVo;

public class DailyDao {

	//Single-ton pattern : 1���� ��ü�� 
	//                     �����ؼ� ��������
	static DailyDao single = null;

	public static DailyDao getInstance() {
		if (single == null)
			single = new DailyDao();

		return single;
	}

	public DailyDao() {
		// TODO Auto-generated constructor stub

	}
	
	public List<DailyVo> selectList(int g_index) {

		List<DailyVo> list = new ArrayList<DailyVo>();
		String sql = "select * from daily where g_index=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//1.Connection ������
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ������
			pstmt = conn.prepareStatement(sql);

			//2-1.pstmt setting
			pstmt.setInt(1, g_index);
			     
			//3.�����ó����ü ������
			rs = pstmt.executeQuery();
			//ó��~������ �ݺ��ض�..
			while (rs.next()) {
				//1���� ���ڵ带 ������ ��ü
				DailyVo vo = new DailyVo();

				//���緹�ڵ�(rs)�� �ʵ尪�� ���ͼ�->VO�� �ִ´�
				//vo�� �ֱ�
				vo.setD_index(rs.getInt("d_index"));
				vo.setD_subject(rs.getString("d_subject"));
				vo.setD_content(rs.getString("d_content"));
				vo.setD_date(rs.getString("d_date"));
				vo.setD_image(rs.getString("d_image"));
				vo.setG_index(rs.getInt("g_index"));
				
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
	
	
	public int insert(DailyVo vo) {
		// TODO Auto-generated method stub
		int res = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into daily(d_subject,d_content,d_image,d_date,g_index) values(?,?,?,now(),?)";

		try {
			//1.Connection���
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ���
			pstmt = conn.prepareStatement(sql);
			//3.pstmt�� parameter setting
			pstmt.setString(1, vo.getD_subject());
			pstmt.setString(2, vo.getD_content());
			pstmt.setString(3, vo.getD_image());
			pstmt.setInt(4, vo.getG_index());

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
