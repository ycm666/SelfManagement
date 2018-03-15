package action.grouplist;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.GroupListDao;
import vo.GroupListVo;

/**
 * Servlet implementation class GroupListListAction
 */
@WebServlet("/grouplist/list.do")
public class GroupListListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int m_index = Integer.parseInt(request.getParameter("m_index"));
		
		List<GroupListVo> list = GroupListDao.getInstance().selectList(m_index);
		
		String json_array = JSONArray.toJSONString(list);
		JSONObject json = new JSONObject();
		json.put("data", json_array);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().print(json.toJSONString());
		

	}

}