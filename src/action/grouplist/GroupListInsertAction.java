package action.grouplist;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.GroupListDao;
import vo.GroupListVo;

/**
 * Servlet implementation class GroupListInsertAction
 */
@WebServlet("/grouplist/insert.do")
public class GroupListInsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//�Ķ���� �ޱ�
        int m_index = Integer.parseInt(request.getParameter("m_index"));
        int c_index = Integer.parseInt(request.getParameter("c_index"));
        String g_subject = request.getParameter("g_subject");
		
        GroupListVo vo = new GroupListVo(g_subject, m_index, c_index);
        
        int res = GroupListDao.getInstance().insert(vo);
		
		//���� ��������
		boolean bSuccess = true;
		
		if(res==0) bSuccess = false;
		
		//ȸ�����԰���� : {'success': true} �������� ����
		JSONObject json = new JSONObject();
		json.put("success", bSuccess);
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().print(json.toJSONString());
	}

}
