package vo;

import java.io.Serializable;

import org.json.simple.JSONAware;

public class LocationItem implements Serializable,JSONAware{

	String name;
	String description;
	String telephone;
	String address;     //지번주소
	String road_address;//도로명주소
	double longitude;
	double latitude;
	int    distance;

	
	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		String json = String.format("{'name':'%s','telephone':'%s','address':'%s','road_address':'%s'" +
				                    ",'description':'%s','distance':'%d','latitude':'%f','longitude':'%f'}", 
				                    name,telephone,address,road_address,description,distance,latitude,longitude);
		return json;
	}
	
	
	public String getRoad_address() {
		return road_address;
	}

	public void setRoad_address(String road_address) {
		this.road_address = road_address;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

	
	
	
	
}
