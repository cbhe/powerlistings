package mongoTools;

import java.io.StringWriter;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.bson.Document;

import com.go2map.lsp.local.engine.mapservice.fulltextindex.POIManager2;
import com.mongodb.client.MongoDatabase;

import beans.Email;
import beans.Image;
import beans.OrderInfoBean;
import beans.PhoneInfo;
import beans.SuppressInfoBean;
import beans.Url;
import berkeleydb.util.LocalStruct;
import tools.Tools;

public class MongoWriter {
	private MongoDatabase powerlistings;

	public MongoWriter() {
		this.powerlistings = Connection.getDatabase();
		if (Tools.policyStatus == false) {
			Tools.setPolicy();
		}
	}

	
	
	
	
	public void writeCancel(String dataid) {

		// uniqueid & localStruct
		String uniqueid = null;
		LocalStruct localStruct = null;
		try {
			uniqueid = POIManager2.getUIDbyDataid(dataid);
			localStruct = POIManager2.getSearchResult_by_UID(uniqueid).getInnerResultSet().getResult(0)
					.getLocalStruct();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		// write to mongo
		Document document = new Document();
		document.append("operation", "C").append("uniqueid", uniqueid).append("dataid", dataid)
				.append("modity_time", Tools.formatedTimeString("yyyy-MM-dd HH:mm:ss")).append("lai_yuan", "YEXT")
				.append("province", localStruct.province).append("city", localStruct.city)
				.append("county", localStruct.county);

		powerlistings.getCollection("lists").insertOne(document);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	public void writeOrder(String requestStr) {

		// 1.解析request
		OrderInfoBean bean = (OrderInfoBean) Tools.jsonToBean(requestStr, OrderInfoBean.class);
		String yextCates = bean.categories.get(0).name;
		String sogouCates = Tools.getCategoryMap().get(yextCates);

		/*
		 * bean--------(有 partnerId)-------"U" | | -----(没有 partnerId)-----"A"
		 */

		// 2.根据情况求解 dataid 和 uid （uid 即 uniqueid）
		String dataid = null;
		String uniqueid = null;
		boolean isUpdate = bean.partnerId != null && bean.partnerId.trim().length() > 0;

		if (isUpdate) {
			// 如果是更新操作，需要用dataid来查询uid

			dataid = bean.partnerId;
			try {
				uniqueid = POIManager2.getUIDbyDataid(dataid);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			// 去掉dataid前面的"1_"
			if (dataid.length() > 2 && dataid.substring(0, 2).equals("1_")) {
				dataid = dataid.substring(2);
			}
		} else {
			// 如果是添加操作，则需要使用 <名字，坐标，分类>信息来生成dataid 和 uid

			String[] categories = sogouCates.split(";;");
			String[] dataidAndUid = Tools.getDataIdAndUid(bean.name, bean.geoData.displayLongitude,
					bean.geoData.displayLatitude, categories[0], categories[1]);
			dataid = dataidAndUid[0];
			uniqueid = dataidAndUid[1];
		}

		// 3.分别求解每一项
		// 3.1.
		StringBuilder stringBuilder = new StringBuilder();
		String faxStr = null;
		if (bean.phones != null) {
			for (PhoneInfo phoneInfo : bean.phones) {
				stringBuilder.append(phoneInfo.number.number).append(";");
				if (phoneInfo.type.equals("FAX")) {
					faxStr = phoneInfo.number.number;
				}
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}

		String phone = stringBuilder.toString();
		String fax = faxStr;

		//
		String urlStr = null;
		if (bean.urls != null) {
			for (Url url : bean.urls) {// --------
				if (url.type.equals("WEBSITE")) {
					urlStr = url.url;
					break;
				}
			}
		}

		// KEYWORDS , 多个以半角逗号分割
		stringBuilder.delete(0, stringBuilder.length());
		if (bean.keywords != null) {
			for (String keyword : bean.keywords) {// --------
				stringBuilder.append(keyword).append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		String keywords = stringBuilder.toString();

		// EMAIL
		stringBuilder.delete(0, stringBuilder.length());
		if (bean.emails != null) {
			for (Email e : bean.emails) {// -----------------
				String emailStr = e.address;
				stringBuilder.append(emailStr).append(",");
			}
			if (stringBuilder.length() > 0) {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}
		}
		String email = stringBuilder.toString();

		// PICTRUE 要提取yextInfoBean.image中描述为“LOGO”的照片url
		String picUrlStr = null;
		if (bean.images != null) {
			for (Image image : bean.images) {// ------------
				if (image.type.equals("LOGO")) {
					picUrlStr = image.url;
				}
			}
		}

		// brands/brandkn
		stringBuilder.delete(0, stringBuilder.length());
		if (bean.brands != null) {
			for (String brand : bean.brands) {// ---------
				stringBuilder.append(brand).append(",");
			}
			if (stringBuilder.length() > 0) {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}
		}
		String brands = stringBuilder.toString();

		// 拼接CDATA
		String deepCdata = "<![CDATA[<additional><data><items>" + "<item source=\"YEXT\" lastmodify=\""
				+ Tools.formatedTimeString() + "\">"
				+ objToXML(bean).replaceAll("<\\?.*?\\?>", "").replaceAll("\r|\n|  ", "")
				+ "</item></items></data></additional>]]>";

		Document document = new Document();
		document.append("dataid", dataid).append("uniqueid", uniqueid).append("operation", isUpdate ? "U" : "A")
				.append("modify_time", Tools.formatedTimeString()).append("lai_yuan", "YEXT")
				.append("state", bean.address.state).append("city", bean.address.city).append("name", bean.name)
				.append("name_chn", Tools.hasChinese(bean.name) ? bean.name : null)
				.append("longitude", bean.geoData.displayLongitude).append("latitude", bean.geoData.displayLatitude)
				.append("category", sogouCates).append("phone", phone).append("fax", fax)
				.append("postcode", bean.address.postalCode).append("url", urlStr).append("email", email)
				.append("keywords", keywords).append("branks", brands).append("pictrue", picUrlStr)
				.append("deepCDATE", deepCdata);
		powerlistings.getCollection("lists").insertOne(document);
	}
	private String objToXML(Object obj) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(new Class[] { obj.getClass() });
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
			marshaller.setProperty("jaxb.encoding", "UTF-8");
			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	public void writeSuppress(String requestStr) {
		SuppressInfoBean bean = (SuppressInfoBean) Tools.jsonToBean(requestStr, SuppressInfoBean.class);

		LocalStruct localStruct = null;
		String uid = null;
		try {
			uid = POIManager2.getUIDbyDataid(bean.listingId);
			localStruct = POIManager2.getSearchResult_by_UID(uid).getInnerResultSet().getResult(0).getLocalStruct();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		/*
		 * supress_____(true)____>canonicalListingId___(存在)___> R | | |
		 * |___(为空)___> D | |__(false)___> S
		 */
		if (bean.suppress == true) {
			if (bean.canonicalListingId == null) {
				dtype(localStruct, bean, requestStr);
			} else {
				rtype(localStruct, bean, requestStr);
			}
		} else {
			stype(localStruct, bean, requestStr);
		}
	}
	private void dtype(LocalStruct localStruct, SuppressInfoBean bean, String requestStr) {
		Document document = new Document();

		document.append("operation", "D").append("uniqueid", localStruct.uid)
				.append("dataid", bean.listingId.substring(2)).append("modify_time", Tools.formatedTimeString())
				.append("lai_yuan", "YEXT").append("province", localStruct.province).append("city", localStruct.city)
				.append("county", localStruct.county).append("name", localStruct.caption)
				.append("address_chn", Tools.hasChinese(localStruct.address) ? localStruct.address : "")
				.append("longitude", Tools.getLnglat(localStruct)[0])
				.append("latitude", Tools.getLnglat(localStruct)[1])
				.append("type", localStruct.category + ";;" + localStruct.subcategory)
				.append("keywords", localStruct.keywords).append("request", requestStr);

		powerlistings.getCollection("lists").insertOne(document);
	}

	private void rtype(LocalStruct localStruct, SuppressInfoBean bean, String requestStr) {
		Document document = new Document();
		document.append("operation", "R").append("uniqueid", localStruct.uid)
				.append("dataid", bean.listingId.substring(2)).append("parentid", bean.canonicalListingId.substring(2))
				.append("modify_time", Tools.formatedTimeString()).append("lai_yuan", "YEXT")
				.append("province", localStruct.province).append("city", localStruct.city)
				.append("county", localStruct.county).append("name", localStruct.caption)
				.append("longitute", Tools.getLnglat(localStruct)[0])
				.append("latitude", Tools.getLnglat(localStruct)[1])
				.append("type", localStruct.category + ";;" + localStruct.subcategory).append("request", requestStr);
		powerlistings.getCollection("lists").insertOne(document);
	}

	private void stype(LocalStruct localStruct, SuppressInfoBean bean, String requestStr) {
		Document document = new Document();
		document.append("operation", "S").append("uniqueid", localStruct.uid)
				.append("dataid", bean.listingId.substring(2)).append("modify_time", Tools.formatedTimeString())
				.append("lai_yuan", "YEXT").append("province", localStruct.province).append("city", localStruct.city)
				.append("county", localStruct.county).append("name", localStruct.caption)
				.append("address_chn", Tools.hasChinese(localStruct.address) ? ((Object) (localStruct.address)) : "")
				.append("longitute", Tools.getLnglat(localStruct)[0])
				.append("latitude", Tools.getLnglat(localStruct)[1])
				.append("type", localStruct.category + ";;" + localStruct.subcategory)
				.append("phone", localStruct.phone).append("keywords", localStruct.keywords)
				.append("request", requestStr);
		powerlistings.getCollection("lists").insertOne(document);
	}

	
	
	
	
	
	
	
	
	public void writeLog(String entrance, String req, String res) {
		Document document = new Document();
		document.append("entrance", entrance).append("time", Tools.formatedTimeString()).append("request", req)
				.append("result", res);

		powerlistings.getCollection("requests").insertOne(document);
	}
}
