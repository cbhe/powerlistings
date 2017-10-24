package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.bson.Document;

import com.go2map.dp.Geom;
import com.go2map.dp.geom.PointGeom;
import com.go2map.lsp.local.engine.mapservice.Grade;
import com.go2map.lsp.local.engine.mapservice.InnerResult;
import com.go2map.lsp.local.engine.mapservice.InnerResultSet;
import com.go2map.lsp.local.engine.mapservice.SearchResult;
import com.go2map.lsp.local.engine.mapservice.fulltextindex.POIManager2;
import com.go2map.util.DoublePoint;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.org.apache.bcel.internal.generic.NEW;

import berkeleydb.util.LocalStruct;
import mongoTools.Connection;
import mongoTools.MongoWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.Tools;

@WebServlet("/detail")
public class Detail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//���ȼ������ð�ȫ����
		if(Tools.policyStatus == false){
			Tools.setPolicy();
		}

		
		//��request�õ�����
		String uid = request.getParameter("id");

		//��ѯ		
		JSONObject resultJson = null;
		try {
			SearchResult searchResult     = POIManager2.getSearchResult_by_UID(uid,  String.valueOf(System.currentTimeMillis()));
			InnerResultSet innerResultSet = searchResult.getInnerResultSet();
			
			if(innerResultSet != null && innerResultSet.getSize() != 0){
				InnerResult innerResult = innerResultSet.getResult(0);
				LocalStruct localStruct = innerResult.getLocalStruct();
			
				resultJson = structToJson(localStruct);
			}
			else{
				response.sendError(404);//yext�涨�����id��ѯ�������ݣ��򷵻�404����
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
		//����
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		PrintWriter printWriter = response.getWriter();
		printWriter.println(resultJson.size() == 0 ? null : resultJson);//�޸ķ��أ�����ǿ�list����ʾnull������[null]
		
		
		//����ͷ�����Ϣ����mongo
		MongoWriter mongoWriter = new MongoWriter();
		mongoWriter.writeLog("detail", uid, resultJson.toString());
	}
	
	
	private JSONObject structToJson(LocalStruct localStruct){
		String id            = localStruct.dataid == null ? "null": localStruct.dataid;
		String status        = "AVAILABLE";
		String name          = localStruct.caption == null? "null": localStruct.caption;
		String address       = localStruct.address == null? "null": localStruct.address;
		String address2      = "null";
		String sublocality   = "null";
		String city          = localStruct.city == null? "null": localStruct.city;
		String state         = localStruct.province == null? "null": localStruct.province;
		String zip           = "null";
		String countryCode   = "CN";		
		String phone         = localStruct.phone == null? "null": localStruct.phone;
		String url           = localStruct.POIURL== null? "null": localStruct.POIURL;
		String rating        = "null";
		String total_reviews = "null";
		
		/*categories �ֶ�������Ҫ��
		 * 1.���yext�ύ����Ӧdataid��category��Ϣ���ͷ���֮ǰ�ύ��
		 * 2.��Ҫת���� "categories": [{"name": "��������;;һ���в�"}]
		 */
        JSONArray cateArr = new JSONArray();
        JSONObject cateJs = new JSONObject();
        String categories;
        if((categories = (String)Tools.dataidToYextCatesMap.getMap().get(id)) == null)
            categories = (new StringBuilder(String.valueOf(localStruct.category))).append(";;").append(localStruct.subcategory).toString();
        cateJs.element("name", categories);
        cateArr.add(cateJs);

        
		String website = "null";
		String description = "null";
		
		
		//photos is "array"[]
		ArrayList photos = null;
		String yearEstablished = localStruct.createdate;
		String specialOfferMessage = "null";
		String specialOfferUrl = "null";
		String contact_email = "null";
		
		//latitude & longitude
		String latitude = "null";
		String longitude = "null";		
		Geom geom = localStruct.geometry;
		if(geom.getGeomType() == Geom.GTYPE_POINT){
			PointGeom pointGeom = (PointGeom)geom;
			DoublePoint doublePoint = pointGeom.dps.getDoublePoint(0);
			double[] google = Tools.sogouToGoogle(new double[]{doublePoint.x, doublePoint.y});
			longitude = String.valueOf(google[0]);
			latitude = String.valueOf(google[1]);
		}
		else{
			System.out.print("geom.getGeomType() != Geom.GTYPE_POINT");
		}
		String video = "null";
		//payment_options is "array" type
		ArrayList payment_options = null;
		String facebook = "null";
		String twitter = "null";
		
		JSONObject json = new JSONObject();
		json.element("id"                 , id);
		json.element("status"             , status);
		json.element("name"               , name);
		json.element("address"            , address);
		json.element("address2"           , address2);
		json.element("sublocality"        , sublocality);
		json.element("city"               , city);
		json.element("state"              , state);
		json.element("zip"                , zip);
		json.element("countryCode"        , countryCode);
		json.element("phone"              , phone);
		json.element("url"                , url);
		json.element("rating"             , rating);
		json.element("total_reviews"      , total_reviews);
		json.element("categories"         , cateArr == null? "null": cateArr);
		json.element("website"            , website);
		json.element("description"        , description);
		json.element("photos"             , (photos==null || photos.size()==0)?"null":photos);
		json.element("yearEstablished"    , yearEstablished);
		json.element("specialOfferMessage", specialOfferMessage);
		json.element("specialOfferUrl"    , specialOfferUrl);
		json.element("contact_email"      , contact_email);
		json.element("latitude"           , latitude);
		json.element("longitude"		  , longitude);
		json.element("video"			  , video);
		json.element("payment_options"    , (payment_options==null || payment_options.size()==0)?"null":payment_options);
		json.element("facebook"           , facebook);
		json.element("twitter"            , twitter);
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
