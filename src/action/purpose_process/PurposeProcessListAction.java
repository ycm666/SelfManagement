package action.purpose_process;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import dao.PurposeDao;
import dao.PurposeProcessDao;
import vo.PurposeProcessVo;
import vo.PurposeVo;

/**
 * Servlet implementation class PurposeProcessListAction
 */
@WebServlet("/purpose_process/list.do")
public class PurposeProcessListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// purpose_process/list.do?p_index=10
		
		//외부로 받아올떄는 무조건 String 형태로 처음에는 받아옴
		String str_p_index = request.getParameter("p_index");
		int p_index = Integer.parseInt(str_p_index);
		//System.out.println("p_index=" + p_index);
		PurposeProcessDao dao = PurposeProcessDao.getInstance();
		List<PurposeProcessVo> list = dao.selectList(p_index);
		
		//ArrayList => JSONArray변환
		String json_array = JSONArray.toJSONString(list);

		String send_str = String.format("{'data':%s}", json_array);
		
		response.setContentType("text/plain;charset=utf-8");
		response.getWriter().print(send_str);

	}

}
