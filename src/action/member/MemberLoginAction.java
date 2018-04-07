package action.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.MemberVo;

/**
 * Servlet implementation class MemberLoginAction
 */
@WebServlet("/member/login.do")
public class MemberLoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        String id = request.getParameter("id"); 
        
        MemberVo  vo = MemberDao.getInstance().selectOne(id);
        
        if(vo==null)
        	vo = new MemberVo();
        
        //사용자 정보를 JSON 데이터로 전송
        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(vo.toJSONString());
        

	}

}
