package action.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MyLocationCollector;
import vo.LocationItem;

/**
 * Servlet implementation class SearchListAction
 */
@WebServlet("/search.do")
public class SearchListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
        //search.do?latitude=37.483782&longitude=126.9003409&search=�౹
		request.setCharacterEncoding("utf-8");
		String str_latitude = request.getParameter("latitude");
		String str_longitude= request.getParameter("longitude");
		//���ڿ�=>double������ ��ȯ
		double latitude = Double.parseDouble(str_latitude);
		double longitude= Double.parseDouble(str_longitude);
		
		String search = request.getParameter("search");
		int range = 500;// �ݰ� 1000m��
		//������ġ�ֺ��� �˻��� ��ȣ��� ��������
		List<LocationItem> list 
		    = MyLocationCollector.getLocationItems(
		    		                      search, 
		    		                      latitude, 
		    		                      longitude, 
		    		                      range);
		request.setAttribute("list", list);
		
		//forward
		String forward_page = "search_list.jsp";
		RequestDispatcher disp = request.getRequestDispatcher(forward_page);
		disp.forward(request, response);

	}

}