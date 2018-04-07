package util.coord;

import java.awt.Point;

public class MyCoord {

	//2개의 위도/경도을 이용해서 거리 구하기
	public static double distance(
			double lat1,double lon1,
			double lat2,double lon2)
	{
		double d2r = Math.PI / 180.0;
		lon1 = lon1 * d2r;
		lat1 = lat1 * d2r;
		lon2 = lon2 * d2r;
		lat2 = lat2 * d2r;
		double dlon = lon2-lon1;
		double dlat = lat2-lat1;
		double a = Math.pow(Math.sin(dlat/2.0), 2.0) +
				Math.cos(lat1) * Math.cos(lat2) *
						Math.pow(Math.sin(dlon/2.0),2.0);
		double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return  6376.5* c;
	}
		
	//기준위치에서 거리가 len만큼떨어지고 각도가 degree인 위치구하기
	public static Point getPosition(
			Point cpt,int len,int degree){
		//계산된 위치
		Point pt=new Point();
		double radian = Math.PI/180.0;

		pt.x = (int)(cpt.x + len*Math.cos(degree*radian));
		pt.y = (int)(cpt.y - len*Math.sin(degree*radian));

		return pt;
	}

	// 두 지점(위도/경도)의 위치에 따른 각도 구하기 (Radian)(방위각 고정 필요)
	public static double radianFromCoordinate(double lat1,double lon1,
											  double lat2,double lon2)
	{
		double longitudinalDifference = lon2 - lon1;
		double latitudinalDifference = lat2 - lat1;
		double possibleAzimuth = (Math.PI * .5f) - Math.atan(latitudinalDifference / longitudinalDifference);
		if (longitudinalDifference > 0) return possibleAzimuth;
		else if (longitudinalDifference < 0) return possibleAzimuth + Math.PI;
		else if (latitudinalDifference < 0) return Math.PI;

		return 0.0f;
	}

	//Degree to Radian
	public static double DtoR(double degree){
		return Math.PI/180.0 * degree;
	}

	//Radian to Degree
	public static double RtoD(double radian){
		return 180.0/Math.PI * radian;
	}

}
