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
		//÷��ȭ���� ������ ȭ�ϻ���
		if(vo.getD_image().equals("no_file")) {
			File f = new File(save_dir,vo.getD_image());
			f.delete();
		}
		//DB����
		int res = DailyDao.getInstance().delete(d_index);
		
		//��������
		boolean bSuccess = true;
		
		if(res==0) bSuccess = false;
		
		//ȸ�����԰���� : {'success': true} �������� ����
		JSONObject json = new JSONObject();
		json.put("success", bSuccess);
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().print(json.toJSONString());

	}

}