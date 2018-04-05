package action.purpose;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.DailyDao;
import dao.PurposeDao;
import vo.DailyVo;
import vo.PurposeVo;

/**
 * Servlet implementation class PurposeInsertAction
 */
@WebServlet("/purpose/insert.do")
public class PurposeInsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// /purpose/insert.do?p_subejct=xx&p_content=yy&
		
		//p_subject,p_content,p_unit,p_goal,p_date,m_index
		
		//외부로 받아올떄는 무조건 String 형태로 처음에는 받아옴
				String p_subject = request.getParameter("p_subject");
				String p_content = request.getParameter("p_content");
				String p_unit = request.getParameter("p_unit");
				String str_p_goal = request.getParameter("p_goal");
				int p_goal = Integer.getInteger("str_p_goal");
				String str_m_index = request.getParameter("m_index");
				int m_index = Integer.getInteger("str_m_index");
				
				//포장
				PurposeVo vo = new PurposeVo(p_subject, p_content, p_unit,p_goal, m_index);
				
				int res = PurposeDao.getInstance().insert(vo);
					
				
				//성공여부
				boolean bSuccess = true;
				
				if(res==0) bSuccess = false;
				
				//결과를 : {'success': true} 형식으로 전송
				JSONObject json = new JSONObject();
				json.put("success", bSuccess);
				response.setContentType("text/plain; charset=utf-8");
				response.getWriter().print(json.toJSONString());
				
			
				
	}

}