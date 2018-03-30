package action.daily;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.DailyDao;
import vo.DailyVo;

/**
 * Servlet implementation class DialyInsertAction
 */
@WebServlet("/daily/modify.do")
public class DailyModifyAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		//파라메터 받기
		String d_subject = request.getParameter("d_subject");
		String d_content =  request.getParameter("d_content");
		int d_index = Integer.parseInt(request.getParameter("d_index"));
		
		
		//System.out.println("d_subject:" + d_subject);
		//System.out.println("d_content:" + d_content);
		
		//포장
		DailyVo vo = new DailyVo(d_index,d_subject, d_content);
		
		int res = DailyDao.getInstance().update(vo);
		
		//성공여부
		boolean bSuccess = true;
		
		if(res==0) bSuccess = false;
		
		//회원가입결과를 : {'success': true} 형식으로 전송
		JSONObject json = new JSONObject();
		json.put("success", bSuccess);
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().print(json.toJSONString());

	}

}