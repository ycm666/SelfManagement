package action.daily;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.DailyDao;
import vo.DailyVo;

/**
 * Servlet implementation class DialyInsertAction
 */
@WebServlet("/daily/delete.do")
public class DailyDeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int d_index = Integer.parseInt(request.getParameter("d_index"));
		
		DailyVo  vo = DailyDao.getInstance().selectOne(d_index);
		
		String save_dir = request.getServletContext().getRealPath("/upload/");
		//첨부화일이 있으면 화일삭제
		if(!vo.getD_image().equals("no_file")) {
			File f = new File(save_dir,vo.getD_image());
			f.delete();
		}
		//DB삭제
		int res = DailyDao.getInstance().delete(d_index);
		
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