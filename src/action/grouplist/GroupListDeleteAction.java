package action.grouplist;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.DailyDao;
import dao.GroupListDao;
import vo.DailyVo;

/**
 * Servlet implementation class DialyInsertAction
 */
@WebServlet("/grouplist/delete.do")
public class GroupListDeleteAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int g_index = Integer.parseInt(request.getParameter("g_index"));
		
		List<DailyVo> daily_list = DailyDao.getInstance().selectList(g_index);
		
		String save_dir = request.getServletContext().getRealPath("/upload/");
		int res = 0;
		int count = 0;
		for(DailyVo vo : daily_list) {
			//첨부화일이 있으면 화일삭제
			if(!vo.getD_image().equals("no_file")) {
				File f = new File(save_dir,vo.getD_image());
				f.delete();
			}
			//DB삭제
			res =  DailyDao.getInstance().delete(vo.getD_index());
			count++;
		}
		
		res =  count + GroupListDao.getInstance().delete(g_index);
		
		
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