package com.sogou.map.mapper.tools.CoordSysTransform;



public class Geom {
	
	/*
	 * 判断两个线段的bound是否有交集
	 */	
	public static boolean isInterset(double xMin, double yMin, double xMax, double yMax,
						      		 double xtMin, double ytMin, double xtMax, double ytMax)
	{
		return !(xMin > xtMax || xMax < xtMin || yMin > ytMax || yMax < ytMin);
	}

	/*
	 * 判断两条线是否有交集
	 */
	public static boolean isCaseLine(double x1, double y1, double x2, double y2,
							  		 double x3, double y3, double x4, double y4)
	{
		return ((Math.max(x1, x2) >= Math.min(x3, x4)) && (Math.max(x3, x4) >= Math.min(x1, x2)) 
				&& (Math.max(y1, y2) >= Math.min(y3, y4)) && (Math.max(y3, y4) >= Math.min(y1, y2)) 
				&& multiply(x3, y3, x2, y2, x1, y1) * multiply(x2, y2, x4, y4, x1, y1) >= 0
				&& multiply(x1, y1, x4, y4, x3, y3) * multiply(x4, y4, x2, y2, x3, y3) >= 0);
	}
	
	/*
	 * 求叉乘
	 */
	public static double multiply(double x1, double y1, double x2, double y2, double x0, double y0)
	{
		return ((x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0));
	}
	
	/*
	 *点是否在线上
	 */
	public static boolean isOnline(double x, double y, double x1, double y1, double x2, double y2)
	{   
		return (((x - x1) * (x - x2) <= 0) && ((y - y1) * (y - y2) <= 0)
				&&(Math.abs(multiply(x1, y1, x2, y2, x, y)) < 0.00001));     
	}   
	
	/*
	 * 获得两点间的距离
	 */
	public static double getDist(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	
	/*
	 * 获得最小矩形
	 */
	public static void getMBR(double x[], double y[], double []mbr)
	{
		double xMin = 0, yMin = 0, xMax = 0, yMax = 0;
		for (int i = 0; i < x.length; i++)
		{
			xMin = Math.min(xMin, x[i]);
			xMax = Math.max(xMax, x[i]);
			yMin = Math.min(yMin, y[i]);
			yMax = Math.max(yMax, y[i]);
		}
		mbr[0] = xMin;
		mbr[1] = yMin;
		mbr[2] = xMax;
		mbr[3] = yMax;
	}
	/*
	public static boolean isInRegion_2(double dx, double dy, Feature fea)
	{
		PointList pointList = null;
		try {
			System.out.println(fea.getAttributeCount());
			System.out.println(fea.getAttribute(0).getString());
			Geometry geom_ = fea.getGeometry();
			System.out.println(geom_.getType());
			VectorGeometry geom = (VectorGeometry)geom_;
			
			//VectorGeometry geom = (VectorGeometry) fea.getGeometry();
			//int ListCount = geom.getPointListCount();
			pointList = geom.getNextPointList();
			System.out.println(pointList.getPointCount());
			int numPoints = pointList.getPointCount();
			double []points = new double[numPoints * 2];
			pointList.getNextPoints(points, 0, numPoints);
			
			//int listIndex = 1;
//			for (int i = 0; i < ListCount; i++) {
			//int numPoints = pointList.getPointCount();
			//double[] listPointsXY = new double[numPoints * 2];
			//pointList.getNextPoints(listPointsXY, 0, numPoints);
			if (isInRegion_l(dx, dy, points))
				return true;
//				pointList = geom.getNextPointList();
//			}
			
		} catch (Exception ex)
		{
			return false;
		}
		return false;
	}
	*/
	public static boolean isInRegion2(double dx, double dy, double [][] cPoints)
	{
		int iCot = 0;
		for (int i=0; i<cPoints.length; i++)
		{
			double []Points = cPoints[i];
			iCot += isInRegion_(dx, dy, Points);
		}
		return (iCot % 2 == 1);
	}
	public static boolean isInRegion2(double dx, double dy, Object[] cPoints)
	{	
		int iCot = 0;
		for (int i=0; i<cPoints.length; i++)
		{
			double []Points = (double[])cPoints[i];
			iCot += isInRegion_(dx, dy, Points);
		}
		return (iCot % 2 == 1);
	}
	
	public static int isInRegion_(double dx, double dy, double [] points)
	{
		int iCot = 0;
		double lx = -1, ly = dy;
		double px, py, qx, qy;
		for (int i = 0; i < points.length - 3; i += 2)
		{
			px = points[i];
			py = points[i + 1];
			qx = points[i + 2];
			qy = points[i + 3];
			if (Math.abs(py - qy) < 0.00001)			
				continue;
			if ((px == qx) && (py == qy))				
				continue;
			if (isOnline(dx, dy, px, py, qx, qy))   	
				return 1; // iCot=1表示在里面
			if (isOnline(px, py, dx, dy, lx, ly))
			{
				if (py > qy)		
					iCot++;
			}  
			else if(isOnline(qx, qy, dx, dy, lx, ly))
			{     
				if (qy > py)		
					iCot++;     
			}   
			else if(isCaseLine(px, py, qx, qy, lx, ly, dx, dy))
			{     
				iCot++; 
			}
		}
		return iCot;
	}
	
	public static boolean isInRegion_l(double dx, double dy, double []points)
	{
		int iCot = 0;
		double lx = -1, ly = dy;
		double px, py, qx, qy;
		for (int i = 0; i < points.length - 3; i += 2)
		{
			px = points[i];
			py = points[i + 1];
			qx = points[i + 2];
			qy = points[i + 3];
			if (Math.abs(py - qy) < 0.00001)			
				continue;
			if ((px == qx) && (py == qy))				
				continue;
			if (isOnline(dx, dy, px, py, qx, qy))   	
				return true;
			if (isOnline(px, py, dx, dy, lx, ly))
			{
				if (py > qy)		
					iCot++;
			}  
			else if(isOnline(qx, qy, dx, dy, lx, ly))
			{     
				if (qy > py)		
					iCot++;     
			}   
			else if(isCaseLine(px, py, qx, qy, lx, ly, dx, dy))
			{     
				iCot++; 
			}
		}
		return (iCot % 2 == 1);
	}
	
	/*
	 * 判断点在面内
	 */
	public static boolean isInRegion(double dx, double dy, double []x, double []y)
	{
		int iCot = 0;
		double mbr[] = new double[4];
		getMBR(x, y, mbr);
		if(dx < mbr[0] || dy < mbr[1] || dx > mbr[2] || dy > mbr[3])
			return false;
		double lx = (mbr[0] - 1), ly = dy;
		int num = x.length;
		for (int i = 0; i < num; i++)
		{
			double px = x[i];
			double py = y[i];
			double qx = x[(i + 1) % num];
			double qy =	y[(i + 1) % num];
			if (Math.abs(py - qy) < 0.00001)			
				continue;
			if ((px == qx) && (py == qy))				
				continue;
			if (isOnline(dx, dy, px, py, qx, qy))   	
				return true;
			if (isOnline(px, py, dx, dy, lx, ly))
			{
				if (py > qy)		
					iCot++;
			}  
			else if(isOnline(qx, qy, dx, dy, lx, ly))
			{     
				if (qy > py)		
					iCot++;     
			}   
			else if(isCaseLine(px, py, qx, qy, lx, ly, dx, dy))
			{     
				iCot++; 
			}
		}
		return (iCot % 2 == 1);
	}
}
