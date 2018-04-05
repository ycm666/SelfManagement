package service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import util.DaumCoordToAddress;
import util.GeoConvert;
import util.coord.GeoCoord;
import util.coord.MyCoord;
import util.key.MyOpenAPIKey;
import vo.LocationItem;

public class MyLocationCollector {

	

	public static ArrayList<LocationItem> getLocationItems(
			String category,double lat,double lon,int range){

		ArrayList<LocationItem> list = new ArrayList<LocationItem>();
		try {
			//category = URLEncoder.encode(category, "UTF8");
			int scope = range;
			int start = 1;
			int display = 100; //음식점 100개 보여주기

			SAXBuilder sb = new SAXBuilder();

			//Daum : GPS좌표->주소얻기
			//String searchField="서울시 구로구 구로3동" + category;
			String searchField = DaumCoordToAddress.getCoordToAddress(lat, lon) + category;
			
//Naver OpenAPI
			/*
			String clientId = "B6P5_6lUk9ZoZlmT93CT";
			String clientSecret = "8jHC8rFrhH";
			
			*/
			String clientId = MyOpenAPIKey.Naver.CLIENTID;
			String clientSecret = MyOpenAPIKey.Naver.CLIENTSECRET;
			
			
			
			
			searchField = URLEncoder.encode(searchField, "utf-8");
			String urlStr = String.format("https://openapi.naver.com/v1/search/local.xml?query=%s&start=%d&display=%d",
						searchField,start,display
					);

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			//발급받은 ID
			connection.setRequestProperty("X-Naver-Client-Id", clientId); 
			//발급받은 PW
			connection.setRequestProperty("X-Naver-Client-Secret", clientSecret); 
			// 받을요청타입
			connection.setRequestProperty("Content-Type", "application/xml"); 
			connection.connect();
			//URL url = new URL("http://openapi.naver.com/search?key=fa65213e0082bd7fc5b7f1cff89190e6&query=" + URLEncoder.encode(searchField, "utf-8") + "&target=local&start=2&display=100");
			

			Document doc = sb.build(connection.getInputStream());
			Element root = doc.getRootElement();// <rss>
			Element channel = root.getChild("channel");
			List itemList = channel.getChildren("item");
			URL lonlatUrl = null;

			//xml 생성
			//네이버에서 넘어온 좌표가 맵좌이기 때문에 야후 OpenAPI에서 위성(위도/경도)좌표를 얻어온다(맵좌표->위성좌표)
			//root = new Element("root");

			GeoConvert conv = new GeoConvert() ;
			conv.SetSrcType(GeoConvert.kBessel1984, GeoConvert.kKatec);	// WGS 구글 좌표계
			conv.SetDstType(GeoConvert.kWgs84, GeoConvert.kGeographic);	// KTM 네이버 좌표 (카텍)

			for(Object obj : itemList) {

				//Element item = new Element("item");
				Element tmpItem = (Element)obj;
				String name = tmpItem.getChild("title").getText().replace("<b>","").replace("</b>","");
				String description = tmpItem.getChild("description").getText();
				String telephone = tmpItem.getChild("telephone").getText();
				String address = tmpItem.getChild("address").getText();
				String road_address = tmpItem.getChild("roadAddress").getText();
				double mapx = Double.parseDouble(tmpItem.getChild("mapx").getText());
				double mapy = Double.parseDouble(tmpItem.getChild("mapy").getText());
				//System.out.printf("mapx=%f mapy=%f\n",longitude,latitude );
				// Map좌표->GPS좌표 변환
				//ReturnValueDouble d =  conv.Conv(mapx, mapy);//, lon, lat);
				GeoCoord gpsCoord = conv.Conv(mapx, mapy);
				
				//System.out.printf("longitude=%f latitude=%f\n",d.x,d.y );
                //                                       내위치   아이템위치
				int distance = (int)Math.round(MyCoord.distance(lat, lon, gpsCoord.latitude, gpsCoord.longitude)*1000);

				//Log.d("me", distance+":"+name);

				if(scope >= distance){
					//root.addContent(item);
					LocationItem item = new LocationItem();
					item.setName(name);
					item.setDescription(description);
					item.setTelephone(telephone);
					item.setAddress(address);
					item.setRoad_address(road_address);
					item.setLatitude(gpsCoord.latitude);
					item.setLongitude(gpsCoord.longitude);
					item.setDistance(distance);
					list.add(item);
				}
			}

			//Distance Sort처리
			LocationItem [] items = new LocationItem[list.size()];
			list.toArray(items);
			//sort
			LocationItemComp comp = new LocationItemComp();
			Arrays.sort(items, comp);

			list = new ArrayList<LocationItem>();
			for(LocationItem item : items){
				list.add(item);
			}


		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	static class LocationItemComp implements Comparator<LocationItem>{

		public int compare(LocationItem lhs, LocationItem rhs) {
			// TODO Auto-generated method stub
			int ret=0;
			if(lhs.getDistance()>rhs.getDistance())ret=1;
			else if(lhs.getDistance()<rhs.getDistance())ret=-1;

			return ret;
		}
	}
}
