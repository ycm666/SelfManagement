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
			int display = 100; //������ 100�� �����ֱ�

			SAXBuilder sb = new SAXBuilder();

			//Daum : GPS��ǥ->�ּҾ��
			//String searchField="����� ���α� ����3��" + category;
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
			//�߱޹��� ID
			connection.setRequestProperty("X-Naver-Client-Id", clientId); 
			//�߱޹��� PW
			connection.setRequestProperty("X-Naver-Client-Secret", clientSecret); 
			// ������ûŸ��
			connection.setRequestProperty("Content-Type", "application/xml"); 
			connection.connect();
			//URL url = new URL("http://openapi.naver.com/search?key=fa65213e0082bd7fc5b7f1cff89190e6&query=" + URLEncoder.encode(searchField, "utf-8") + "&target=local&start=2&display=100");
			

			Document doc = sb.build(connection.getInputStream());
			Element root = doc.getRootElement();// <rss>
			Element channel = root.getChild("channel");
			List itemList = channel.getChildren("item");
			URL lonlatUrl = null;

			//xml ����
			//���̹����� �Ѿ�� ��ǥ�� �����̱� ������ ���� OpenAPI���� ����(����/�浵)��ǥ�� ���´�(����ǥ->������ǥ)
			//root = new Element("root");

			GeoConvert conv = new GeoConvert() ;
			conv.SetSrcType(GeoConvert.kBessel1984, GeoConvert.kKatec);	// WGS ���� ��ǥ��
			conv.SetDstType(GeoConvert.kWgs84, GeoConvert.kGeographic);	// KTM ���̹� ��ǥ (ī��)

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
				// Map��ǥ->GPS��ǥ ��ȯ
				//ReturnValueDouble d =  conv.Conv(mapx, mapy);//, lon, lat);
				GeoCoord gpsCoord = conv.Conv(mapx, mapy);
				
				//System.out.printf("longitude=%f latitude=%f\n",d.x,d.y );
                //                                       ����ġ   ��������ġ
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

			//Distance Sortó��
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
