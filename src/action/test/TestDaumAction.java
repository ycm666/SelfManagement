package action.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class TestDaumAction
 */
@WebServlet("/test_daum.do")
public class TestDaumAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		 
		double latitude=37.483782;
		double longitude=126.9003409;
		String  kakaoAK = "KakaoAK 3ebe1c7e586491e37bc04090f8d133b0";
		String urlStr = String.format("https://dapi.kakao.com/v2/local/geo/coord2address.json?x=%f&y=%f&input_coord=WGS84",
				longitude,latitude
				);

		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		//발급받은 key
		connection.setRequestProperty("Authorization", kakaoAK); 
		connection.setRequestProperty("Content-Type", "application/plain"); 
		connection.connect();
		
		InputStreamReader isr = new InputStreamReader(connection.getInputStream(),"utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		
		while(true) {
			String readData = br.readLine();
			if(readData==null)break;
			sb.append(readData);
		}
		
	 try {
		 JSONParser jsonParser = new JSONParser();
		 JSONObject jsonObj   = (JSONObject) jsonParser.parse(sb.toString());
		 JSONArray  jsonArray = (JSONArray) jsonObj.get("documents");
		 JSONObject jsonObj2 = (JSONObject) jsonArray.get(0);
		 JSONObject jsonObj3 = (JSONObject) jsonObj2.get("address");
		 String address = (String) jsonObj3.get("address_name");
		 
		 
		response.setContentType("text/html;charset=utf-8");
			
		response.getWriter().println(address);
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
         
		
		
		
		
		
		
		
		
		
	}

}