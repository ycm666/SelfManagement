package action.purpose;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.DailyDao;
import dao.GroupListDao;
import dao.PurposeDao;
import dao.PurposeProcessDao;
import vo.DailyVo;
import vo.PurposeProcessVo;
import vo.PurposeVo;

/**
 * Servlet implementation class PurposeDeleteAction
 */
@WebServlet("/purpose/delete.do")
public class PurposeDeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		int p_index = Integer.parseInt(request.getParameter("p_index"));
		
		//메모 및 값 추가에 포함된 모든 것들 삭제
		List<PurposeProcessVo> purpose_process_list = PurposeProcessDao.getInstance().selectList(p_index);
	
		int res = 0;
		int count = 0;
		
		for(PurposeProcessVo vo : purpose_process_list) {
	
			//DB삭제
			res =  PurposeProcessDao.getInstance().delete(vo.getPp_index());
			count++;
		}
		
		//목표삭제
				res =  count + PurposeDao.getInstance().delete(p_index);
				
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