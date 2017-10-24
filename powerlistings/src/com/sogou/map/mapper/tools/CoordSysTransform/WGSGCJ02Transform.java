package com.sogou.map.mapper.tools.CoordSysTransform;

public class WGSGCJ02Transform {
	static final double pi = 3.14159265358979324;
	 
    // Krasovsky 1940; a = 6378245.0; 1/f = 298.3; b = a * (1 - f);
    static final double a = 6378245.0;
 
    // ee = (a^2 - b^2) / a^2;
    static final double ee = 0.00669342162296594323;
 
    // Mars Geodetic System World ==> Geodetic System; cost 3e-3 ms
    public static double[] transformMGS2GS(double mgLon, double mgLat) {
 
        return ReachTransform.transform(mgLon, mgLat, new ReachTransform.ReversTransform() {
            public double[] transform(double lon, double lat) {
                return transformGS2MGS(lon, lat);
            }
        });
    }
 
    // World Geodetic System ==> Mars Geodetic System; cost 8e-4 ms
    public static double[] transformGS2MGS(double wgLon, double wgLat) {
        if (outOfChina(wgLon, wgLat))
            return new double[] { wgLon, wgLat };
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        return new double[] { wgLon + dLon, wgLat + dLat };
    }
 
    static boolean outOfChina(double lon, double lat) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }
 
    static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }
 
    static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
}
