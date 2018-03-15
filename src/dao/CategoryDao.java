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

	//Single-ton pattern : 1개의 객체만 
	//                     생성해서 서비스하자
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
			//1.Connection 얻어오기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻어오기
			pstmt = conn.prepareStatement(sql);

			//3.결과행처리객체 얻어오기
			rs = pstmt.executeQuery();
			//처음~끝까지 반복해라..
			while (rs.next()) {
				//1개의 레코드를 저장할 객체
				CategoryVo vo = new CategoryVo();

				//현재레코드(rs)의 필드값을 얻어와서->VO에 넣는다
				//vo에 넣기
				vo.setC_index(rs.getInt("c_index"));
				vo.setC_name(rs.getString("c_name"));

				//ArrayList추가
				list.add(vo);
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
		return list;
	}
	
}
