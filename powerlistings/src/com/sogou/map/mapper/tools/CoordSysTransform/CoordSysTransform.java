package com.sogou.map.mapper.tools.CoordSysTransform;

public class CoordSysTransform {
	public static double[] wgs84ToSogou(double lon, double lat) {
		return WGS84MecatorTransform.MercatorLLToProj(lon, lat);
	}

	public static double[] sogouToWGS84(double lon, double lat) {
		return WGS84MecatorTransform.MercatorProjToLL(lon, lat);
	}

	public static double[] wgs84ToGoogle(double lon, double lat) {
		return WGSGCJ02Transform.transformGS2MGS(lon, lat);
	}

	public static double[] googleToWGS84(double lon, double lat) {
		return WGSGCJ02Transform.transformMGS2GS(lon, lat);
	}

	public static double[] wgs84ToBaidu(double lon, double lat) {
		double[] gxy = WGSGCJ02Transform.transformGS2MGS(lon, lat);
		return GCJ02BD09Transform.bd_encrypt(gxy[0], gxy[1]);

	}

	public static double[] googleToBaidu(double lon, double lat) {
		return GCJ02BD09Transform.bd_encrypt(lon, lat);
	}

	public static double[] baiduToWGS84(double lon, double lat) {
		double[] gxy = GCJ02BD09Transform.bd_decrypt_reach(lon, lat);
		return WGSGCJ02Transform.transformMGS2GS(gxy[0], gxy[1]);
	}

	public static void main(String[] args) {
		double[] wgsxy = { 116.32664812d, 39.91505345d };
		// double[] wgsxy = {116.32162388, 40.06641628};
		System.out.format("orig wgs\t%.8f\t%.8f\n", wgsxy[0], wgsxy[1]);

		double[] sogouxy = CoordSysTransform.wgs84ToSogou(wgsxy[0], wgsxy[1]);
		System.out.format("wgs-sogou\t%.8f\t%.8f\n", sogouxy[0], sogouxy[1]);

		wgsxy = CoordSysTransform.sogouToWGS84(sogouxy[0], sogouxy[1]);
		System.out.format("sogou-wgs\t%.8f\t%.8f\n", wgsxy[0], wgsxy[1]);

		double[] googlexy = CoordSysTransform.wgs84ToGoogle(wgsxy[0], wgsxy[1]);
		System.out.format("wgs-google\t%.8f\t%.8f\n", googlexy[0], googlexy[1]);

		wgsxy = CoordSysTransform.googleToWGS84(googlexy[0], googlexy[1]);
		System.out.format("google-wgs\t%.8f\t%.8f\n", wgsxy[0], wgsxy[1]);

		double[] baiduxy = CoordSysTransform.wgs84ToBaidu(wgsxy[0], wgsxy[1]);
		System.out.format("wgs-baidu\t%.8f\t%.8f\n", baiduxy[0], baiduxy[1]);

		wgsxy = CoordSysTransform.baiduToWGS84(baiduxy[0], baiduxy[1]);
		System.out.format("baidu-wgs\t%.8f\t%.8f\n", wgsxy[0], wgsxy[1]);
	}
}
