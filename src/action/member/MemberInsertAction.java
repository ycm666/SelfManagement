package action.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.MemberDao;
import vo.MemberVo;

/**
 * Servlet implementation class MemberInsertAction
 */
@WebServlet("/member/insert.do")
public class MemberInsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//�Ķ���� �ޱ�
		String m_id 		= request.getParameter("m_id");
		String m_pwd 		= request.getParameter("m_pwd");
		String m_name 		= request.getParameter("m_name");
		String m_phone_num 	= request.getParameter("m_phone_num");
		String m_gender 	= request.getParameter("m_gender");
		String m_age 		= request.getParameter("m_age");
		
		MemberVo  vo = new MemberVo(m_id, m_pwd, m_name, m_phone_num, m_gender, m_age);
		
		int res = MemberDao.getInstance().insert(vo);
		
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
