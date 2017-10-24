package tools;

import berkeleydb.util.LocalStruct;
import com.go2map.dp.Geom;
import com.go2map.util.DoublePoint;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.sogou.map.mapper.tools.CoordSysTransform.CoordSysTransform;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import mongoTools.Connection;
import mongoTools.DataidToYextCatesMap;

import org.apache.catalina.tribes.util.StringManager;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.bson.Document;

public class Tools {
	private static class Bean {
		String uniqueid;
		String dataid;
	}

	public static boolean policyStatus = false;
	public static String docPath = null;
	
	public static HashMap<String, String> cityLayerMap = null;
	public static HashMap<String, String> categoryMap = null;
	public static DataidToYextCatesMap dataidToYextCatesMap = new DataidToYextCatesMap();
	// private static MongoCollection requests;

	public static String getDocPath() {
		if (docPath == null) {
			docPath = (new PathHelper().getPath());
		}
		return docPath;
	}

	private static class PathHelper {
		public String getPath() {
			return getClass().getResource("").getPath();
		}
	}

	// public static void logRequest(String entrance, String reqInfo)
	// {
	// if(requests == null)
	// requests = Connection.getMongoCollection("requests");
	// Document document = new Document();
	// document.append("time", formatedTimeString()).append("entrance",
	// entrance).append("reqestInfo", reqInfo);
	// requests.insertOne(document);
	// }
	// public static void logRequest(String entrance, String reqInfo, String
	// result)
	// {
	// if(requests == null)
	// requests = Connection.getMongoCollection("requests");
	// Document document = new Document();
	// document.append("time", formatedTimeString()).append("entrance",
	// entrance).append("reqestInfo", reqInfo).append("result", result);
	// requests.insertOne(document);
	// }

	
	
	
	public static HashMap<String, String> getCategoryMap() {
		if (categoryMap == null) {
			try {
				generateCategoryMap();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return categoryMap;
	}
	private static void generateCategoryMap() throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		categoryMap = new HashMap();
		
		String excelPath = getDocPath() + "categoryMapping.xlsx";
		File   excelFile = new File(excelPath);
		
		FileInputStream fileInputStream = new FileInputStream(excelFile);
		Workbook workbook = WorkbookFactory.create(fileInputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		int rowCount = sheet.getPhysicalNumberOfRows();
		for(int r=0;r<rowCount;r++){
			Row row = sheet.getRow(r);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.delete(0, stringBuilder.length());
			
			for(int c=0;c<3;c++){  //前三列是yext三级分类
				Cell cell = row.getCell(c);
				if(cell == null){
					stringBuilder.append("").append(";;");
					continue;
				}
				
				String cellValue = null;
				int cellType = cell.getCellType();
				switch (cellType) {
				case Cell.CELL_TYPE_STRING:
					cellValue = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					cellValue = cell.getStringCellValue();
				default:
					break;
				}
				
				stringBuilder.append(cellValue).append(";;");
			}
			
			String yextCates = stringBuilder.toString();
			while (yextCates.endsWith(";;")) {
				int endIndex = yextCates.length()-2;
				yextCates = yextCates.substring(0, endIndex);
			}
			
			stringBuilder.delete(0, stringBuilder.length());
			
			for(int c=3;c<5;c++){ //sougo 两级分类
				Cell cell = row.getCell(c);
				
				if(cell == null){
					stringBuilder.append("").append(";;");
					continue;
				}
				
				String cellValue = null;
				int cellType = cell.getCellType();				
				switch (cellType) {
				case Cell.CELL_TYPE_STRING:
					cellValue = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					cellValue = cell.getStringCellValue();
				default:
					break;
				}
				stringBuilder.append(cellValue).append(";;");
			}
			stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
			
			String sogouCates = stringBuilder.toString();
			
			categoryMap.put(yextCates, sogouCates);
		}
	}

	
	
	
	
	public static void setPolicy() {
		String policyPath = getDocPath() + "policy.txt";
		
		System.setProperty("java.security.policy", policyPath);
		System.setSecurityManager(new SecurityManager());
		
		policyStatus = true;
	}

	
	
	
	
	
	public static String getContent(HttpServletRequest request) throws IOException {
		
		ServletInputStream servletInputStream = request.getInputStream();
		InputStreamReader  inputStreamReader  = new InputStreamReader(servletInputStream, "utf-8");
		BufferedReader     bufferedReader     = new BufferedReader(inputStreamReader);
		
		StringBuilder result = new StringBuilder();
		
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
		
		servletInputStream.close();
		inputStreamReader .close();
		bufferedReader    .close();
		
		return result.toString();
	}

	
	
	
	
	
	public static double[] googleToSogou(double google[]) {
		double wgs84[] = CoordSysTransform.googleToWGS84(google[0], google[1]);
		double sogou[] = CoordSysTransform.wgs84ToSogou(wgs84[0], wgs84[1]);
		return sogou;
	}

	public static double[] sogouToGoogle(double sogou[]) {
		double wgs84[] = CoordSysTransform.sogouToWGS84(sogou[0], sogou[1]);
		double google[] = CoordSysTransform.wgs84ToGoogle(wgs84[0], wgs84[1]);
		return google;
	}
	public static String[] getLnglat(LocalStruct localStruct) {
		String res[] = new String[2];
		double x = localStruct.geometry.getNodes()[0].x;
		double y = localStruct.geometry.getNodes()[0].y;
		res[0] = String.valueOf(x);
		res[1] = String.valueOf(y);
		return res;
	}

	
	
	
	
	public static void getCityLayerMap() {
		
		cityLayerMap = new HashMap<String, String>();
		
		try {
			String filePath = getDocPath() + "rpcLayerList.txt";
			File   file     = new File(filePath);
			
			FileInputStream   fileInputStream   = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
			BufferedReader    bufferedReader    = new BufferedReader(inputStreamReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String layerCity[] = line.split("\\s+");
				
				String city = layerCity[1];
				String layer= layerCity[0];
				
				cityLayerMap.put(city, layer);
			}
			
			bufferedReader   .close();
			inputStreamReader.close();
			fileInputStream  .close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} 
	}

	
	
	
	
	public static Object jsonToBean(String jsonString, Class class1) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, class1);
	}

	
	
	
	public static String formatedTimeString(String formater) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formater);
		Date date = new Date();
		String time = simpleDateFormat.format(date);
		return time;
	}
	public static String formatedTimeString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = simpleDateFormat.format(date);
		return time;
	}

	
	
	
	public static String decoder(String parameter) {
		if (parameter == null)
			return null;
		try {
			parameter = URLDecoder.decode(parameter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return parameter;
	}

	
	
	
	
	
	public static String[] getDataIdAndUid(String name, double x, double y, String category, String subcategory) {
		
		try {
			name        = URLEncoder.encode(name, "utf-8");
			category    = URLEncoder.encode(category, "utf-8");
			subcategory = URLEncoder.encode(subcategory, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		String urlStr = "http://10.146.33.48:8080/EditPlatformServer/generateid?"
				   +"name="+name+"&"
				   +"x="+x+"&"
				   +"y="+y+"&"
				   +"category="+category+"&"
				   +"subcategory="+subcategory+"&"
				   +"usertag=haomaiyys" + "&"
				   +"returnmode=2";
		
		String responseStr = sendGet(urlStr);
		
		Bean bean = (Bean)jsonToBean(responseStr, Bean.class);
		
		String[] strings = new String[2];
		strings[0] = bean.dataid;
		strings[1] = bean.uniqueid;
		
		return strings;
	}
	public static String sendGet(String urlStr) {
		String result = "";
		try {
			System.out.println(urlStr);
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null)
				result = (new StringBuilder(String.valueOf(result))).append(line).toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			result = new String(result.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println((new StringBuilder("sendGet(192)response content: ")).append(result).toString());
		return result;
	}

	
	
	
	
	
	private static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	public static final boolean hasChinese(String strName) {
		char ch[] = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c))
				return true;
		}

		return false;
	}

	
	
	
	
}
