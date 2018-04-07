package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.DBService;
import vo.PurposeProcessVo;

public class PurposeProcessDao {
	
	//Single-ton pattern : 1개의 객체만 
	//                     생성해서 서비스하자
	static PurposeProcessDao single = null;

	public static PurposeProcessDao getInstance() {
		if (single == null)
			single = new PurposeProcessDao();

		return single;
	}

	public PurposeProcessDao() {
		// TODO Auto-generated constructor stub

	}

	public List<PurposeProcessVo> selectList(int p_index) {

		List<PurposeProcessVo> list = new ArrayList<PurposeProcessVo>();
		String sql = "select * from purpose_process where p_index=?";

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
				PurposeProcessVo vo = new PurposeProcessVo();

				//현재레코드(rs)의 필드값을 얻어와서->VO에 넣는다
				//vo에 넣기
				vo.setPp_index(rs.getInt("pp_index"));
				vo.setPp_memo(rs.getString("pp_memo"));
				vo.setPp_value(rs.getInt("pp_value"));
				vo.setPp_date(rs.getString("pp_date"));
				vo.setP_index(rs.getInt("p_index"));
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
	
	public int insert(PurposeProcessVo vo) {
		// TODO Auto-generated method stub
		int res = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into purpose_process(pp_memo,pp_value,pp_date,p_index) values(?,?,now(),?)";

		try {
			//1.Connection얻기
			conn = DBService.getInstance().getConnection();
			//2.명령처리객체 얻기
			pstmt = conn.prepareStatement(sql);
			//3.pstmt의 parameter setting
			pstmt.setString(1, vo.getPp_memo());
			pstmt.setInt(2, vo.getPp_value());
			
			pstmt.setInt(3, vo.getP_index());
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
