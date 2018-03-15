package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;
import vo.CategoryVo;

public class CategoryDao {

	//Single-ton pattern : 1���� ��ü�� 
	//                     �����ؼ� ��������
	static CategoryDao single = null;

	public static CategoryDao getInstance() {
		if (single == null)
			single = new CategoryDao();

		return single;
	}

	public CategoryDao() {
		// TODO Auto-generated constructor stub

	}
	
	public List<CategoryVo> selectList() {

		List<CategoryVo> list = new ArrayList<CategoryVo>();
		String sql = "select * from category";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//1.Connection ������
			conn = DBService.getInstance().getConnection();
			//2.���ó����ü ������
			pstmt = conn.prepareStatement(sql);

			//3.�����ó����ü ������
			rs = pstmt.executeQuery();
			//ó��~������ �ݺ��ض�..
			while (rs.next()) {
				//1���� ���ڵ带 ������ ��ü
				CategoryVo vo = new CategoryVo();

				//���緹�ڵ�(rs)�� �ʵ尪�� ���ͼ�->VO�� �ִ´�
				//vo�� �ֱ�
				vo.setC_index(rs.getInt("c_index"));
				vo.setC_name(rs.getString("c_name"));

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
	
}
