package action.purpose_process;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.PurposeDao;
import dao.PurposeProcessDao;
import vo.PurposeProcessVo;
import vo.PurposeVo;

/**
 * Servlet implementation class PurposeProcessInsertAction
 */
@WebServlet("/purpose_process/insert.do")
public class PurposeProcessInsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		//외부로 받아올떄는 무조건 String 형태로 처음에는 받아옴

		String pp_memo = request.getParameter("pp_memo");
		String str_pp_value = request.getParameter("pp_value");
		int pp_value = Integer.parseInt(str_pp_value);
		String pp_date = request.getParameter("pp_date");
		String str_p_index = request.getParameter("p_index");
		int p_index = Integer.parseInt(str_p_index);
		
		//포장
		PurposeProcessVo vo = new PurposeProcessVo(pp_memo, pp_value, pp_date,p_index);
		
		int res = PurposeProcessDao.getInstance().insert(vo);
			
		
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


