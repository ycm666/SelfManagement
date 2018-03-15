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

	//Single-ton pattern : 1개의 객체만 
	//                     생성해서 서비스하자
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
			//1.Connection 얻어오기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻어오기
			pstmt = conn.prepareStatement(sql);
			
			//2-1.pstmt setting
			pstmt.setInt(1, m_index);

			//3.결과행처리객체 얻어오기
			rs = pstmt.executeQuery();
			//처음~끝까지 반복해라..
			while (rs.next()) {
				//1개의 레코드를 저장할 객체
				GroupListVo vo = new GroupListVo();

				//현재레코드(rs)의 필드값을 얻어와서->VO에 넣는다
				//vo에 넣기
				vo.setG_index(rs.getInt("g_index"));
				vo.setG_subject(rs.getString("g_subject"));
				vo.setM_index(rs.getInt("m_index"));
				vo.setC_index(rs.getInt("c_index"));
				
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
	
	
	public int insert(GroupListVo vo) {
		// TODO Auto-generated method stub
		int res = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into group_list(g_subject,m_index,c_index) values(?,?,?)";

		try {
			//1.Connection얻기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻기
			pstmt = conn.prepareStatement(sql);
			//3.pstmt의 parameter setting
            pstmt.setString(1, vo.getG_subject());
            pstmt.setInt(2, vo.getM_index());
            pstmt.setInt(3, vo.getC_index());
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
}
