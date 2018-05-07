package action.member;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.MemberVo;

/**
 * Servlet implementation class MemberListAction
 */
@WebServlet("/member/find.do")
public class MemberFindAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String m_phone_num = request.getParameter("m_phone_num");
		//String m_hint = request.getParameter("m_hint");
		MemberDao dao = MemberDao.getInstance();
		MemberVo vo = dao.selectOneFind(m_phone_num);
		
		if(vo==null)
        	vo = new MemberVo();
        
        //사용자 정보를 JSON 데이터로 전송
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(vo.toJSONString());
		
		

	}

}
