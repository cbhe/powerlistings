package com.sogou.map.mapper.tools.CoordSysTransform;

public class WGS84MecatorTransform {
	static final double PI = 3.14159265358979323846;
    static final double COORD_MIN = -1000000000;
    static final double COORD_MAX = 1000000000;
    /*** min decimal exponent **/
    static final int DBL_MIN_10_EXP = -307;
    /*** max decimal exponent **/
    static final int DBL_MAX_10_EXP = 308;
 
    static double DEG_TO_RAD(double a) {
        return ((a) * PI / 180.0);
    }
 
    static double RAD_TO_DEG(double a) {
        return ((a) * 180.0 / PI);
    }
 
    static double CLIP(double x, double min, double max) {
        return ((x) < (min) ? (min) : ((x) > (max) ? (max) : (x)));
    }
 
    /** Note: We make sure that the X and Y coordinates are within proper bounds. **/
    /** This ensures that we don't get a PLOSS error when we use pDest in sin **/
    /** and cos, etc. **/
    static double[] DegreesToRadians(double x, double y) {
        return new double[]{ DEG_TO_RAD(CLIP(x, -360.0, 360.0)), DEG_TO_RAD(CLIP(y, -90.0, 90.0))};
    }
 
    static double[] RadiansToDegrees(double x, double y) {
        return new double[]{CLIP(RAD_TO_DEG(x), -360.0, 360.0), CLIP(RAD_TO_DEG(y), -90.0, 90.0)};
    }
 
 
    /** Convert a point from Long/Lat to Mercator coordinates. **/
    public static double[] MercatorLLToProj(double x, double y) {
        double sinphi, esinphi, temp1, temp2;
 
        double[] pDest = DegreesToRadians(x, y);
        /** See equation 7-6 on page 44. **/
        pDest[0] = 6378206.4 * pDest[0];
 
        /** See equation 7-7a on page 44. We have to avoid dividing by zero or **/
        /** taking log(0), so we use COORD_MAX and COORD_MIN to represent **/
        /** infinity and negative infinity, respectively. We don't need to **/
        /** worry about esinphi == 1 or -1, because e is always < 1. **/
        sinphi = Math.sin(pDest[1]);
        if ((temp1 = 1 + sinphi) == 0.0)
            pDest[1] = (double) COORD_MIN;
        else if ((temp2 = 1 - sinphi) == 0.0)
            pDest[1] = (double) COORD_MAX;
        else {
            esinphi = 0.082271854224939184 * sinphi;
            pDest[1] = 3189103.2 * Math.log(temp1 / temp2
                    * Math.pow((1 - esinphi) / (1 + esinphi), 0.082271854224939184));
        }
        return pDest;
    }

    /** Convert a point from Mercator coordinates to Long/Lat. **/
    public static double[] MercatorProjToLL(double x, double y) {
        double temp, chi, chi2, coschi2;
        double[] pDest = {0, 0};
        /** See equation 7-12 on page 45. **/
        pDest[0] = x / 6378206.4;
 
        /** See equations 7-10 and 7-13 on pages 44 and 45. We don't need to **/
        /** worry about dividing by zero, because pSys->EllipsoidInfo.a cannot **/
        /** be zero. However, we do need to worry about overflow and underflow **/
        /** errors when calling exp(). **/
        temp = -y / 6378206.4;
        if (temp < (double) DBL_MIN_10_EXP)
            chi = PI / 2;
        else if (temp > (double) DBL_MAX_10_EXP)
            chi = -PI / 2;
        else
            chi = PI / 2 - 2 * Math.atan(Math.exp(temp));
 
        /** See equation 3-5 on page 45. To speed up the equation, we use the **/
        /** procedure described on page 19. See equation 3-35 on page 19. **/
        chi2 = 2 * chi;
        coschi2 = Math.cos(chi2);
        pDest[1] = chi + Math.sin(chi2) * (0.0033938814110493522 + coschi2 * (1.3437644537757259e-005
                + coschi2 * (7.2964865099246009e-008 + coschi2 * 4.4551470401894685e-010)));
        return RadiansToDegrees(pDest[0], pDest[1]);
    }
}
