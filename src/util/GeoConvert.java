package util;

import util.coord.GeoCoord;

public class GeoConvert {
	public static final int kBessel1984 = 0;
	public static final int kWgs84 = 1;
	public static final int kGeographic = 0;
	public static final int kTmWest = 1;
	public static final int kTmMid = 2;
	public static final int kTmEast = 3;
	public static final int kKatec = 4;
	public static final int kUtm52 = 5;
	public static final int kUtm51 = 6;

	public static final double PI = 3.14159265358979;
	public static final double EPSLN = 0.0000000001;
	public static final double S2R = 4.84813681109536E-06; // sin(1")

	public static final int X_W2B = 128;
	public static final int Y_W2B = -481;
	public static final int Z_W2B = -664;


	// Ellips/System Type
	int m_eSrcEllips;
	int m_eSrcSystem;
	int m_eDstEllips;
	int m_eDstSystem;

	// Ellips Factor List
	double m_arMajor[];
	double m_arMinor[];

	// System Factor List 
	double m_arScaleFactor[];
	double m_arLonCenter[];
	double m_arLatCenter[];
	double m_arFalseNorthing[];
	double m_arFalseEasting[];


	// Internal Value for Tm2Geo
	double m_dSrcE0, m_dSrcE1, m_dSrcE2, m_dSrcE3;
	double m_dSrcE, m_dSrcEs, m_dSrcEsp;
	double m_dSrcMl0, m_dSrcInd;

	// Internal Value for Geo2Tm
	double m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3;
	double m_dDstE, m_dDstEs, m_dDstEsp;
	double m_dDstMl0, m_dDstInd;

	// Internal Value for DatumTrans
	double m_dTemp;
	double m_dEsTemp;
	int m_iDeltaX;
	int m_iDeltaY;
	int m_iDeltaZ;
	double m_dDeltaA, m_dDeltaF;




	public GeoConvert() {
		super();
		// TODO Auto-generated constructor stub
		m_arMajor = new double[2];
		m_arMinor = new double[2];

		// System Factor List 
		m_arScaleFactor = new double[7];
		m_arLonCenter = new double[7];
		m_arLatCenter = new double[7];
		m_arFalseNorthing = new double[7];
		m_arFalseEasting = new double[7];

		// Set Ellips factor
		m_arMajor[kBessel1984] = 6377397.155;
		m_arMinor[kBessel1984] = 6356078.96325;

		m_arMajor[kWgs84] = 6378137.0;
		m_arMinor[kWgs84] = 6356752.3142;

		// Set System Factor
		m_arScaleFactor[kGeographic] = 1;
		m_arLonCenter[kGeographic] = 0.0;
		m_arLatCenter[kGeographic] = 0.0;
		m_arFalseNorthing[kGeographic] = 0.0;
		m_arFalseEasting[kGeographic] = 0.0;

		m_arScaleFactor[kTmWest] = 1;
		m_arLonCenter[kTmWest] = 2.18171200985643;
		m_arLatCenter[kTmWest] = 0.663225115757845;
		m_arFalseNorthing[kTmWest] = 500000.0;
		m_arFalseEasting[kTmWest] = 200000.0;

		m_arScaleFactor[kTmMid] = 1;
		m_arLonCenter[kTmMid] = 2.21661859489632;
		m_arLatCenter[kTmMid] = 0.663225115757845;
		m_arFalseNorthing[kTmMid] = 500000.0;
		m_arFalseEasting[kTmMid] = 200000.0;

		m_arScaleFactor[kTmEast] = 1;
		m_arLonCenter[kTmEast] = 2.2515251799362;
		m_arLatCenter[kTmEast] = 0.663225115757845;
		m_arFalseNorthing[kTmEast] = 500000.0;
		m_arFalseEasting[kTmEast] = 200000.0;

		m_arScaleFactor[kKatec] = 0.9999;
		m_arLonCenter[kKatec] = 2.23402144255274;
		m_arLatCenter[kKatec] = 0.663225115757845;
		m_arFalseNorthing[kKatec] = 600000.0;
		m_arFalseEasting[kKatec] = 400000.0;

		m_arScaleFactor[kUtm52] = 0.9996;
		m_arLonCenter[kUtm52] = 2.25147473507269;
		m_arLatCenter[kUtm52] = 0.0;
		m_arFalseNorthing[kUtm52] = 0.0;
		m_arFalseEasting[kUtm52] = 500000.0;

		m_arScaleFactor[kUtm51] = 0.9996;
		m_arLonCenter[kUtm51] = 2.14675497995303;
		m_arLatCenter[kUtm51] = 0.0;
		m_arFalseNorthing[kUtm51] = 0.0;
		m_arFalseEasting[kUtm51] = 500000.0;

	}


	//#define D2R(degree) (degree * PI / 180.0)
	public double D2R(double degree)
	{
		return degree * PI/180.0;
	}
	//#define R2D(radian) (radian * 180.0 / PI)
	public double R2D(double radian)
	{
		return radian*180.0/PI;
	}

	public void  SetSrcType(int eEllips, int eSystem)
	{
		m_eSrcEllips = eEllips;
		m_eSrcSystem = eSystem;

		double temp = m_arMinor[m_eSrcEllips] / m_arMajor[m_eSrcEllips];
		m_dSrcEs = 1.0 - temp * temp;
		m_dSrcE = Math.sqrt(m_dSrcEs);
		m_dSrcE0 = e0fn(m_dSrcEs);
		m_dSrcE1 = e1fn(m_dSrcEs);
		m_dSrcE2 = e2fn(m_dSrcEs);
		m_dSrcE3 = e3fn(m_dSrcEs);
		m_dSrcMl0 = m_arMajor[m_eSrcEllips] * mlfn(m_dSrcE0, m_dSrcE1, m_dSrcE2, m_dSrcE3, m_arLatCenter[m_eSrcSystem]);
		m_dSrcEsp = m_dSrcEs / (1.0 - m_dSrcEs);

		if (m_dSrcEs < 0.00001)
			m_dSrcInd = 1.0;
		else
			m_dSrcInd = 0.0;

		InitDatumVar();
	}

	// Set Internal Values
	public void  SetDstType(int eEllips, int eSystem)
	{
		m_eDstEllips = eEllips;
		m_eDstSystem = eSystem;

		double temp = m_arMinor[m_eDstEllips] / m_arMajor[m_eDstEllips];
		m_dDstEs = 1.0 - temp * temp;
		m_dDstE = Math.sqrt(m_dDstEs);
		m_dDstE0 = e0fn(m_dDstEs);
		m_dDstE1 = e1fn(m_dDstEs);
		m_dDstE2 = e2fn(m_dDstEs);
		m_dDstE3 = e3fn(m_dDstEs);
		m_dDstMl0 = m_arMajor[m_eDstEllips] * mlfn(m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3, m_arLatCenter[m_eDstSystem]);
		m_dDstEsp = m_dDstEs / (1.0 - m_dDstEs);

		if (m_dDstEs < 0.00001)
			m_dDstInd = 1.0;
		else
			m_dDstInd = 0.0;

		InitDatumVar();
	}

	///////////////////////////////////////////
	// Main Convert Function
	public GeoCoord  Conv(double dInX, double dInY)//, double& dOutX, double& dOutY)
	{
		double dInLon, dInLat;
		double dOutLon, dOutLat;

		double dTmX, dTmY;
		double dOutX,dOutY;
		//System.out.println("===1====");
		// Convert to Radian Geographic
		if (m_eSrcSystem == kGeographic)
		{
			//System.out.println("===1-1====");
			dInLon = D2R(dInX);
			dInLat = D2R(dInY);

		}
		else
		{
			//System.out.println("===1-2====");
			// Geographic calculating
			GeoCoord d = Tm2Geo(dInX, dInY);//, dInLon, dInLat);
			dInLon = d.longitude;
			dInLat = d.latitude;

		}

		if (m_eSrcEllips == m_eDstEllips)
		{
			dOutLon = dInLon;
			dOutLat = dInLat;
		}
		else
		{
			//System.out.println("===1-3====");
			// Datum transformation using molodensky function
			GeoCoord d = DatumTrans(dInLon, dInLat);//, dOutLon, dOutLat);
			dOutLon = d.longitude;
			dOutLat = d.latitude;

		}

		// now we should make a output. but it depends on user options
		if (m_eDstSystem == kGeographic) // if output option is latitude & longitude
		{
			//System.out.println("===1-4====");
			dOutX = R2D(dOutLon);
			dOutY = R2D(dOutLat);

		}
		else // if output option is cartesian systems
		{
			//System.out.println("===1-5====");
			// TM or UTM calculating
			GeoCoord d = Geo2Tm(dOutLon, dOutLat);//, dTmX, dTmY);

			dOutX = d.longitude;
			dOutY = d.latitude;

		}

		// System.out.println("===1-final====");
		return new GeoCoord(dOutX,dOutY);

	}

	/////////////////////////////////////////////
	// Global Utility Function
	/*
	public void  D2Dms(double dInDecimalDegree, int &iOutDegree, int &iOutMinute, double &dOutSecond)
	{
		double dTmpMinute;

		iOutDegree = (int)dInDecimalDegree;
		dTmpMinute = (dInDecimalDegree - iOutDegree) * 60.0;
		iOutMinute = (int)dTmpMinute;
		dOutSecond = (dTmpMinute - iOutMinute) * 60.0;
		if ((dOutSecond+0.00001) >= 60.0)
		{
			if (iOutMinute == 59)
			{
				iOutDegree++;
				iOutMinute = 0;
				dOutSecond = 0.0;
			}
			else {
				iOutMinute++;
				dOutSecond = 0.0;
			}
		}
	}
    */

	// Molodensky Datum Transformation function for general datum transformation.
	// Coded by Shin, Sanghee(endofcap@geo.giri.co.kr) 24th Feb, 1999
	// Reference manual : DEFENSE MAPPING AGENCY TECHNICAL MANUAL 8358.1
	// You can read above manual in this home page. http://164.214.2.59/GandG/tm83581/toc.htm
	// Converted to C++ by Jang, Byyng-jin(jangbi@taff.co.kr) 20th Ap
	public GeoCoord  DatumTrans(double dInLon, double dInLat)//, double& dOutLon, double& dOutLat)
	{
		double dRm, dRn;
		double dDeltaPhi, dDeltaLamda;
//		double dDeltaH;
		double dOutLon,dOutLat;

		dRm = m_arMajor[m_eSrcEllips] * (1.0-m_dEsTemp) / Math.pow(1.0-m_dEsTemp*Math.sin(dInLat)*Math.sin(dInLat), 1.5);
		dRn = m_arMajor[m_eSrcEllips] / Math.sqrt(1.0 - m_dEsTemp*Math.sin(dInLat)*Math.sin(dInLat));

		dDeltaPhi = ((((-m_iDeltaX*Math.sin(dInLat)*Math.cos(dInLon) - m_iDeltaY*Math.sin(dInLat)*Math.sin(dInLon)) + m_iDeltaZ*Math.cos(dInLat)) + m_dDeltaA*dRn*m_dEsTemp*Math.sin(dInLat)*Math.cos(dInLat)/m_arMajor[m_eSrcEllips]) + m_dDeltaF*(dRm/m_dTemp+dRn*m_dTemp)*Math.sin(dInLat)*Math.cos(dInLat))/dRm;
		dDeltaLamda = (-m_iDeltaX * Math.sin(dInLon) + m_iDeltaY * Math.cos(dInLon)) / (dRn * Math.cos(dInLat));
//		dDeltaH = iDeltaX * cos(dInLat) * cos(dInLon) + iDeltaY * cos(dInLat) * sin(dInLon) + iDeltaZ * sin(dInLat) - dDeltaA * m_arMajor[eSrcEllips] / dRn + dDeltaF * dTemp * dRn * sin(dInLat) * sin(dInLat);

		dOutLat = dInLat + dDeltaPhi;
		dOutLon = dInLon + dDeltaLamda;

		return new GeoCoord(dOutLon,dOutLat);
	}

	// function for converting longitude, latitude to TM X, Y
	public GeoCoord  Geo2Tm(double lon, double lat)//, double& x, double& y)
	{
		double delta_lon; // Delta longitude (Given longitude - center longitude)
		double sin_phi, cos_phi; // sin and cos value
		double al, als; // temporary values
		double b, c, t, tq; // temporary values
		double con, n, ml; // cone constant, small m
		double x,y;

		// LL to TM Forward equations from here
		delta_lon = lon - m_arLonCenter[m_eDstSystem];
		sin_phi = Math.sin(lat);
		cos_phi = Math.cos(lat);

		if (m_dDstInd != 0)
		{
			b = cos_phi * Math.sin(delta_lon);
			if ((Math.abs(Math.abs(b) - 1.0)) < 0.0000000001)
			{
				//throw (CString)"吏�?吏뺤쭬吏ы씥兩�??吏뺤㎏姨뷀쉪 ?夷덉㎚?遺뗈볥　�땴�씤 ?�솘�닊�뺣껄��?;
				//throw NULL;
			}
		}
		else
		{
			b = 0;
			x = 0.5 * m_arMajor[m_eDstEllips] * m_arScaleFactor[m_eDstSystem] * Math.log((1.0 + b) / (1.0 - b));
			con = Math.acos(cos_phi * Math.cos(delta_lon) / Math.sqrt(1.0 - b * b));
			if (lat < 0)
			{
				con = -con;
				y = m_arMajor[m_eDstEllips] * m_arScaleFactor[m_eDstSystem] * (con - m_arLatCenter[m_eDstSystem]);
			}
		}

		al = cos_phi * delta_lon;
		als = al * al;
		c = m_dDstEsp * cos_phi * cos_phi;
		tq = Math.tan(lat);
		t = tq * tq;
		con = 1.0 - m_dDstEs * sin_phi * sin_phi;
		n = m_arMajor[m_eDstEllips] / Math.sqrt(con);
		ml = m_arMajor[m_eDstEllips] * mlfn(m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3, lat);

		x = m_arScaleFactor[m_eDstSystem] * n * al * (1.0 + als / 6.0 * (1.0 - t + c + als / 20.0 * (5.0 - 18.0 * t + t * t + 72.0 * c - 58.0 * m_dDstEsp))) + m_arFalseEasting[m_eDstSystem];
		y = m_arScaleFactor[m_eDstSystem] * (ml - m_dDstMl0 + n * tq * (als * (0.5 + als / 24.0 * (5.0 - t + 9.0 * c + 4.0 * c * c + als / 30.0 * (61.0 - 58.0 * t + t * t + 600.0 * c - 330.0 * m_dDstEsp))))) + m_arFalseNorthing[m_eDstSystem];
		return new GeoCoord(x,y);
	}

	// function for converting TM X,Y to Longitude and Latitude
	public GeoCoord  Tm2Geo(double x, double y)//, double& lon, double& lat)
	{

		double con; // temporary angles
		double phi; // temporary angles
		double delta_Phi; // difference between longitudes
		long i; // counter variable
		double sin_phi, cos_phi, tan_phi; // sin cos and tangent values
		double c, cs, t, ts, n, r, d, ds; // temporary variables
		double f, h, g, temp; // temporary variables
		double lon,lat;
		long max_iter = 6; // maximun number of iterations

		if (m_dSrcInd != 0)
		{
			f = Math.exp(x / (m_arMajor[m_eSrcEllips] * m_arScaleFactor[m_eSrcSystem]));
			g = 0.5 * (f - 1.0 / f);
			temp = m_arLatCenter[m_eSrcSystem] + y / (m_arMajor[m_eSrcEllips] * m_arScaleFactor[m_eSrcSystem]);
			h = Math.cos(temp);
			con = Math.sqrt((1.0 - h * h) / (1.0 + g * g));
			lat = asinz(con);

			if (temp < 0)
				lat *= -1;

			if ((g == 0) && (h == 0))
				lon = m_arLonCenter[m_eSrcSystem];
			else
				lon = Math.atan(g / h) + m_arLonCenter[m_eSrcSystem];
		}

		// TM to LL inverse equations from here

		x -= m_arFalseEasting[m_eSrcSystem];
		y -= m_arFalseNorthing[m_eSrcSystem];

		con = (m_dSrcMl0 + y / m_arScaleFactor[m_eSrcSystem]) / m_arMajor[m_eSrcEllips];
		phi = con;

		i = 0;
		while(true)
		{
			delta_Phi = ((con + m_dSrcE1 * Math.sin(2.0 * phi) - m_dSrcE2 * Math.sin(4.0 * phi) + m_dSrcE3 * Math.sin(6.0 * phi)) / m_dSrcE0) - phi;
			phi = phi + delta_Phi;
			if (Math.abs(delta_Phi) <= EPSLN) break;
			//System.out.printf("Math.abs(delta_Phi) <= EPSLN : %.20f <= %.20f\n",delta_Phi, EPSLN);
			//if (i >= max_iter) 
			//throw (CString)"Latitude failed to converge";
			//throw NULL;

			i++;

		}

		if (Math.abs(phi) < (PI / 2))
		{
			sin_phi = Math.sin(phi);
			cos_phi = Math.cos(phi);
			tan_phi = Math.tan(phi);
			c = m_dSrcEsp * cos_phi * cos_phi;
			cs = c * c;
			t = tan_phi * tan_phi;
			ts = t * t;
			con = 1.0 - m_dSrcEs * sin_phi * sin_phi;
			n = m_arMajor[m_eSrcEllips] / Math.sqrt(con);
			r = n * (1.0 - m_dSrcEs) / con;
			d = x / (n * m_arScaleFactor[m_eSrcSystem]);
			ds = d * d;
			lat = phi - (n * tan_phi * ds / r) * (0.5 - ds / 24.0 * (5.0 + 3.0 * t + 10.0 * c - 4.0 * cs - 9.0 * m_dSrcEsp - ds / 30.0 * (61.0 + 90.0 * t + 298.0 * c + 45.0 * ts - 252.0 * m_dSrcEsp - 3.0 * cs)));
			lon = m_arLonCenter[m_eSrcSystem] + (d * (1.0 - ds / 6.0 * (1.0 + 2.0 * t + c - ds / 20.0 * (5.0 - 2.0 * c + 28.0 * t - 3.0 * cs + 8.0 * m_dSrcEsp + 24.0 * ts))) / cos_phi);
		}
		else
		{
			lat = PI*0.5 * Math.sin(y);
			lon = m_arLonCenter[m_eSrcSystem];
		}

		return new GeoCoord(lon,lat);
	}

	//////////////////////////////////////////
	// Internal Value calculation Function
	public double  e0fn(double x)
	{
		return 1.0 - 0.25 * x * (1.0 + x / 16.0 * (3.0 + 1.25 * x));
	}

	public double  e1fn(double x)
	{
		return 0.375 * x * (1.0 + 0.25 * x * (1.0 + 0.46875 * x));
	}

	public double  e2fn(double x)
	{
		return 0.05859375 * x * x * (1.0 + 0.75 * x);
	}

	public double  e3fn(double x)
	{
		return x * x * x * (35.0 / 3072.0);
	}

	public double  e4fn(double x)
	{
		double con, com;

		con = 1.0 + x;
		com = 1.0 - x;
		return Math.sqrt(Math.pow(con, con) * Math.pow(com, com));
	}

	public double  mlfn(double e0, double e1, double e2, double e3, double phi)
	{
		return e0 * phi - e1 * Math.sin(2.0 * phi) + e2 * Math.sin(4.0 * phi) - e3 * Math.sin(6.0 * phi);
	}

	public double  asinz(double value)
	{
		if (Math.abs(value) > 1.0)
			value = (value>0?1:-1);

		return Math.asin(value);
	}

	public void  InitDatumVar()
	{
		int iDefFact;
		double dF;

		// direction factor for datum transformation
		// eg) Bessel to Bessel would be 0
		//     WGS84 to Bessel would be 1
		//     BEssel to WGS84 would be -1
		iDefFact = m_eSrcEllips - m_eDstEllips;
		m_iDeltaX = iDefFact * X_W2B;
		m_iDeltaY = iDefFact * Y_W2B;
		m_iDeltaZ = iDefFact * Z_W2B;

		m_dTemp = m_arMinor[m_eSrcEllips] / m_arMajor[m_eSrcEllips];
		dF = 1.0 - m_dTemp; // flattening
		m_dEsTemp = 1.0 - m_dTemp * m_dTemp; // e2

		m_dDeltaA = m_arMajor[m_eDstEllips] - m_arMajor[m_eSrcEllips]; // output major axis - input major axis
		m_dDeltaF = m_arMinor[m_eSrcEllips] / m_arMajor[m_eSrcEllips] - m_arMinor[m_eDstEllips] / m_arMajor[m_eDstEllips]; // Output Flattening - input flattening
	}
}
