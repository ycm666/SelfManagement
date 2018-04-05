package action.purpose;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import dao.PurposeDao;
import vo.PurposeVo;

/**
 * Servlet implementation class PurposeListAction
 */
@WebServlet("/purpose/list.do")
public class PurposeListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// /purpose/list.do?m_index=12
		
		//외부로 받아올떄는 무조건 String 형태로 처음에는 받아옴
		String str_m_index = request.getParameter("m_index");
		int m_index = Integer.parseInt(str_m_index);
		PurposeDao dao = PurposeDao.getInstance();
		List<PurposeVo> list = dao.selectList(m_index);
		
		//ArrayList => JSONArray변환
		String json_array = JSONArray.toJSONString(list);

		String send_str = String.format("{'data':%s}", json_array);
		
		response.setContentType("text/plain;charset=utf-8");
		response.getWriter().print(send_str);
		
		
		
		
	}

}