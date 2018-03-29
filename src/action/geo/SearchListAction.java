package action.geo;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import service.MyLocationCollector;
import vo.LocationItem;

/**
 * Servlet implementation class SearchListAction
 */
@WebServlet("/geo/search_list.do")
public class SearchListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ���ൿ �λ����Ʈ : 37.483486,126.7775602
		// ���п�: 37.3843279,126.9356597
		double latitude = 37.3843279;
		double longitude= 126.9356597;
		
		//search.do?latitude=37.483782&longitude=126.9003409&search=�౹
		request.setCharacterEncoding("utf-8");
		String str_latitude = request.getParameter("latitude");
		String str_longitude= request.getParameter("longitude");
		//���ڿ�=>double������ ��ȯ
		if(str_latitude!=null)
		   latitude = Double.parseDouble(str_latitude);
		if(str_longitude!=null)
		   longitude= Double.parseDouble(str_longitude);
		
		String search = request.getParameter("search");
		int range = 1000;// �ݰ� 1000m��
		//������ġ�ֺ��� �˻��� ��ȣ��� ��������
		List<LocationItem> list 
		    = MyLocationCollector.getLocationItems(
		    		                      search, 
		    		                      latitude, 
		    		                      longitude, 
		    		                      range);
		
		String json_array = JSONArray.toJSONString(list);
		/*JSONObject json = new JSONObject();
		json.put("data", json_array);*/
		String send_str = String.format("{'data':%s}", json_array);
		
		response.setContentType("text/plain; charset=utf-8");
		response.getWriter().print(send_str);

	}

}