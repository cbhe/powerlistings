package servlets;

import java.util.*;
import com.sogou.map.mapper.tools.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormat;

import com.go2map.dp.Geom;
import com.go2map.dp.geom.LineGeom;
import com.go2map.dp.geom.PointGeom;
import com.go2map.lsp.local.engine.mapservice.Category;
import com.go2map.lsp.local.engine.mapservice.Grade;
import com.go2map.lsp.local.engine.mapservice.InnerResult;
import com.go2map.lsp.local.engine.mapservice.InnerResultSet;
import com.go2map.lsp.local.engine.mapservice.SearchResult;
import com.go2map.lsp.local.engine.mapservice.fulltextindex.POIManager2;
import com.go2map.util.DoublePoint;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import com.sogou.map.mapper.tools.CoordSysTransform.CoordSysTransform;
import com.sun.corba.se.spi.ior.ObjectId;
import com.sun.net.httpserver.Filter;
import com.sun.org.apache.bcel.internal.generic.F2D;
import com.sun.org.apache.bcel.internal.generic.NEW;

import berkeleydb.util.LocalStruct;
import mongoTools.Connection;
import mongoTools.MongoWriter;
import net.sf.json.*;
import sun.awt.SunHints.LCDContrastKey;
import tools.Tools;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

@WebServlet("/search")
public class SearchRPC extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		if(Tools.cityLayerMap == null){
			Tools.getCityLayerMap();
		}
		
		
		//从request得到数据	
		String name        = Tools.decoder(request.getParameter("name"));
		String address     = Tools.decoder(request.getParameter("address"));
		String address2    = Tools.decoder(request.getParameter("address2"));
		String sublocality = Tools.decoder(request.getParameter("sublocality"));
		String city        = Tools.decoder(request.getParameter("city"));
		String state       = Tools.decoder(request.getParameter("state"));
		String zip         = request.getParameter("zip");
		String countryCode = request.getParameter("countryCode");
		String latlng      = request.getParameter("latlng");
		String radius      = request.getParameter("radius");
		String phone       = request.getParameter("phone");
		String type        = request.getParameter("type");


					
		String[] latlngStr = latlng.split(",");
		double lat = Double.parseDouble(latlngStr[0]);
		double lng = Double.parseDouble(latlngStr[1]);
		double[] sogou = Tools.googleToSogou(new double[]{lng, lat});
		lng = sogou[0];
		lat = sogou[1];

		
		double[] radiusArray = {500.0, 1000.0};
		
		ArrayList<String> layersList = new ArrayList<String>();
		if(city != null && Tools.cityLayerMap.containsKey(city)){
			layersList.add(Tools.cityLayerMap.get(city));
		}
		else{
			layersList.addAll(Tools.cityLayerMap.values());
		}

		String currentTime = String.valueOf(System.currentTimeMillis());
		ArrayList<Grade> grade = new ArrayList<Grade>();
		
		//查询		
		JSONArray resultJsonArray = new JSONArray();
		try {
			SearchResult searchResult = POIManager2.getSearchResult(name, 0, 10, 0.0, lng, lat, radiusArray, layersList, grade, false, 0, currentTime);
			InnerResultSet innerResultSet = searchResult.getInnerResultSet();
			
			if(innerResultSet != null){
				int count = innerResultSet.getSize();
				for(int i=0;i<count;i++){
					InnerResult innerResult = innerResultSet.getResult(i);
					LocalStruct localStruct = innerResult.getLocalStruct();
					JSONObject jsonObject = structToJson(localStruct);
					resultJsonArray.add(jsonObject);
				}
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		//返回
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter printWriter = response.getWriter();
		printWriter.println(resultJsonArray);
		
		//将请求和返回信息写入mongo
		MongoWriter mongoWriter = new MongoWriter();
		mongoWriter.writeLog("search", request.getQueryString(), resultJsonArray.toString());
	}
	private JSONObject structToJson(LocalStruct localStruct){
		String id = localStruct.dataid == null ? "null": localStruct.dataid;
		String status = "AVAILABLE";
		String name = localStruct.caption == null? "null": localStruct.caption;
		String address = localStruct.address == null? "null": localStruct.address;
		String address2 = "null";
		String sublocality = "null";
		String city = localStruct.city == null? "null": localStruct.city;
		String state = localStruct.province == null? "null": localStruct.province;
		String zip = "null";
		String countryCode = "CN";
		String latitude = "null";
		String longitude = "null";
		
		//latitude & longitude
		Geom geom = localStruct.geometry;
		if(geom.getGeomType() == Geom.GTYPE_POINT){
			PointGeom pointGeom = (PointGeom)geom;
			DoublePoint doublePoint = pointGeom.dps.getDoublePoint(0);
			double[] google = Tools.sogouToGoogle(new double[]{doublePoint.x, doublePoint.y});
			longitude = String.valueOf(google[0]);
			latitude = String.valueOf(google[1]);
		}
		else{
			
		}
		
		String phone = localStruct.phone == null? "null": localStruct.phone;
		String url = localStruct.POIURL == null? "null": localStruct.POIURL;
		String type = "Location";
		String websiteUrl = "null";
		String reviewCount = "null";
		String averageReviewRating = "null";
		
		//------categories-------------------------------------------------------------------
//		2017-8-2号yext提出的需求：
//		还有个地方希望你们修改一下，就是category字段的内容能不能返回一个json格式的。
//		比如这样
//		"categories": [
//			{
//				"name": "餐饮服务;;一般中餐"
//			}
//		]
		String categories, cate, subCate;
		JSONArray cateArr = new JSONArray();
		JSONObject cateJs = new JSONObject();
		if((cate=localStruct.category)!=null && (subCate=localStruct.subcategory)!=null){
			categories = localStruct.category+";;"+localStruct.subcategory;
			cateJs.element("name", categories);
			cateArr.add(cateJs);
		}
		else if(cate != null){
			categories = cate;
			cateJs.element("name", categories);
			cateArr.add(cateJs);
		} 
		else{
			categories = null;
			cateArr = null;
		}
//-----------------------------------------------------------------------------------		
		String listingCreationDate = localStruct.createdate;
		
		JSONObject json = new JSONObject();
		json.element("id", id);
		json.element("status", status);
		json.element("name", name);
		json.element("address", address);
		json.element("address2", address2);
		json.element("sublocality", sublocality);
		json.element("city", city);
		json.element("state", state);
		json.element("zip", zip);
		json.element("countryCode", countryCode);
		json.element("latitude", latitude);
		json.element("longitude", longitude);
		json.element("phone", phone);
		json.element("url", url);
		json.element("type", type);
		json.element("websiteUrl", websiteUrl);
		json.element("reviewCount", reviewCount);
		json.element("averageReviewRating", averageReviewRating);
		json.element("categories", cateArr==null?"null": cateArr);
		json.element("listingCreationDate", listingCreationDate);
		return json;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}







































