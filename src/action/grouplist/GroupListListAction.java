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
		int c_index = 0;
		String str_c_index = request.getParameter("c_index");
		if(str_c_index != null &&  !str_c_index.equals(""))
			c_index = Integer.parseInt(str_c_index);
		
		int m_index = Integer.parseInt(request.getParameter("m_index"));
		
		List<GroupListVo> list = null;
		
		if(c_index==0)
		   list = GroupListDao.getInstance().selectList(m_index);
		else
			list = GroupListDao.getInstance().selectList(m_index,c_index);
		
		
		String json_array = JSONArray.toJSONString(list);
		/*JSONObject json = new JSONObject();
		json.put("data", json_array);*/
		
		String send_str = String.format("{'data':%s}", json_array);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().print(send_str);
		

	}

}