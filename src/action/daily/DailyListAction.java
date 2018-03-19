package action.daily;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.DailyDao;
import vo.DailyVo;


/**
 * Servlet implementation class DailyListAction
 */
@WebServlet("/daily/list.do")
public class DailyListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		int g_index = Integer.parseInt(request.getParameter("g_index"));
		
		List<DailyVo> list = DailyDao.getInstance().selectList(g_index);
		
		String json_array = JSONArray.toJSONString(list);
		/*JSONObject json = new JSONObject();
		json.put("data", json_array);*/
		
		String send_str = String.format("{'data':%s}", json_array);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().print(send_str);

	}

}
