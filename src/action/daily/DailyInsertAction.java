package action.daily;

import java.io.File;
import java.io.IOException;

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
@WebServlet("/daily/insert.do")
public class DailyInsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		//화일저장경로
		String save_dir = request.getServletContext().getRealPath("/upload/");
		int max_size = 1024*1024*100; // 100MB
		MultipartRequest mr = new MultipartRequest(request, 
				                                   save_dir,
				                                   max_size,
				                                   "utf-8",
				                                   new DefaultFileRenamePolicy());
		File f = mr.getFile("d_photo");
		String d_image="no_file";
		if(f!=null) {
			d_image = f.getName();
		}
		
		
		
		//파라메터 받기
		String d_subject = mr.getParameter("d_subject");
		String d_content = mr.getParameter("d_content");
		int g_index = Integer.parseInt(mr.getParameter("g_index"));
		
		
		
		//포장
		DailyVo vo = new DailyVo(d_subject, d_content, d_image, g_index);
		
		int res = DailyDao.getInstance().insert(vo);
		
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