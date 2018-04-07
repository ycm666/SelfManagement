package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import util.key.MyOpenAPIKey;

public class DaumCoordToAddress {

	public static String getCoordToAddress(double latitude,double longitude) {
		
		String address="";
		try {
			//                         3ebe1c7e586491e37bc04090f8d133b0
			//String  kakaoAK = "KakaoAK 3ebe1c7e586491e37bc04090f8d133b0";
			String  kakaoAK = MyOpenAPIKey.Daum.KAKAOAK;
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
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj   = (JSONObject) jsonParser.parse(sb.toString());
			JSONArray  jsonArray = (JSONArray) jsonObj.get("documents");
			JSONObject jsonObj2 = (JSONObject) jsonArray.get(0);
			JSONObject jsonObj3 = (JSONObject) jsonObj2.get("address");
			address = (String) jsonObj3.get("address_name");
			//System.out.println(address);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return address;
	}
}
