package com.sogou.map.mapper.tools.CoordSysTransform;

public class ReachTransform {
	static interface ReversTransform {
        double[] transform(double x, double y);
    }
 
    /*** 2 * pi * 6378245.0 / 360 * 1.0e-8 = 1mm **/
    final static double eps = 1.0e-8;
 
    /*** speed insurance **/
    final static int max_trys = 8;
 
    public static double[] transform(double lon, double lat, ReversTransform revTrans) {
        // // for analysis:
        // double axy0[] = { lon, lat };
 
        // double[] qxy1 = transformGS2MGS(axy0[0], axy0[1]);
        // double axy1[] = { lon + axy0[0] - qxy1[0], lat + axy0[1] - qxy1[1] };
        //
        // double[] qxy2 = transformGS2MGS(axy1[0], axy1[1]);
        // double axy2[] = { lon + axy1[0] - qxy2[0], lat + axy1[1] - qxy2[1] };
        //
        // double[] qxy3 = transformGS2MGS(axy2[0], axy2[1]);
        // double axy3[] = { lon + axy2[0] - qxy3[0], lat + axy2[1] - qxy3[1] };
        //
        // double[] qxy4 = transformGS2MGS(axy3[0], axy3[1]);
        // double axy4[] = { lon + axy3[0] - qxy4[0], lat + axy3[1] - qxy4[1] };
        //
        // return axy4;
 
        double qxy[] = { 0.0d, 0.0d };
        double dLat = 0.0d, dLon = 0.0d;
        double[] axy = { lon, lat };
 
        int tries = 0;
        do {
            qxy = revTrans.transform(axy[0], axy[1]);
            dLon = lon - qxy[0];
            dLat = lat - qxy[1];
            axy[0] += dLon;
            axy[1] += dLat;
        } while (++tries < max_trys && (Math.abs(dLat) > eps || Math.abs(dLon) > eps));
 
        return axy;
    }
}
