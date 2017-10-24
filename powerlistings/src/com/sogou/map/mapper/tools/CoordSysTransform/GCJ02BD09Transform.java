package com.sogou.map.mapper.tools.CoordSysTransform;

public class GCJ02BD09Transform {
	final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	 
    /*** gcj02 --> bd09 **/
    public static double[] bd_encrypt(double gg_lon, double gg_lat) {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        return new double[] { z * Math.cos(theta) + 0.0065, z * Math.sin(theta) + 0.006 };
    }
 
    /*** bd09 --> gcj02 **/
    public static double[] bd_decrypt(double bd_lon, double bd_lat) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        return new double[] { z * Math.cos(theta), z * Math.sin(theta) };
    }
 
    /*** bd09 --> gcj02 use the reach approach **/
    public static double[] bd_decrypt_reach(double bd_lon, double bd_lat) {
        return ReachTransform.transform(bd_lon, bd_lat, new ReachTransform.ReversTransform() {
            public double[] transform(double lon, double lat) {
                return bd_encrypt(lon, lat);
            }
        });
    }
}
