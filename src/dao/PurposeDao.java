package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;
import vo.PurposeVo;

public class PurposeDao {
	//Single-ton pattern : 1개의 객체만 
	//                     생성해서 서비스하자
	static PurposeDao single = null;

	public static PurposeDao getInstance() {
		if (single == null)
			single = new PurposeDao();

		return single;
	}

	public PurposeDao() {
		// TODO Auto-generated constructor stub

	}
	
	
	public List<PurposeVo> selectList(int m_index) {

		List<PurposeVo> list = new ArrayList<PurposeVo>();
		String sql = "select p.*,( select ifnull(sum(pp_value),0) from purpose_process pp where pp.p_index=p.p_index  ) p_now from purpose p where m_index=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//1.Connection 얻어오기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻어오기
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, m_index);
			
			//3.결과행처리객체 얻어오기
			rs = pstmt.executeQuery();
			//처음~끝까지 반복해라..
			while (rs.next()) {
				//1개의 레코드를 저장할 객체
				PurposeVo vo = new PurposeVo();

				//현재레코드(rs)의 필드값을 얻어와서->VO에 넣는다
				//vo에 넣기
				vo.setP_index(rs.getInt("p_index"));
				vo.setP_subject(rs.getString("p_subject"));
				vo.setP_content(rs.getString("p_content"));
				vo.setP_date(rs.getString("p_date"));
				vo.setP_goal(rs.getInt("p_goal"));
				vo.setP_now(rs.getInt("p_now"));
				vo.setP_unit(rs.getString("p_unit"));
				vo.setM_index(rs.getInt("m_index"));
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
	
	public int insert(PurposeVo vo) {
		// TODO Auto-generated method stub
		int res = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;                                                       //   1 2 3 4       5
		String sql = "insert into purpose(p_subject,p_content,p_unit,p_goal,p_date,m_index) values(?,?,?,?,now(),?)";

		try {
			//1.Connection얻기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻기
			pstmt = conn.prepareStatement(sql);
			//3.pstmt의 parameter setting
			pstmt.setString(1, vo.getP_subject());
			pstmt.setString(2, vo.getP_content());
			pstmt.setString(3, vo.getP_unit());
			pstmt.setInt(4, vo.getP_goal());
			
			pstmt.setInt(5, vo.getM_index());
			
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
	
	public int delete(int p_index) {
		// TODO Auto-generated method stub
		int res = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from purpose where p_index=?";

		try {
			//1.Connection얻기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻기
			pstmt = conn.prepareStatement(sql);
			//3.pstmt의 parameter setting
			pstmt.setInt(1,p_index);
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
