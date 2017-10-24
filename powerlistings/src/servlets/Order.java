/**
 * 
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Thread;
import org.bson.Document;

import com.mongodb.client.MongoCollection;

import beans.OrderInfoBean;
import mongoTools.Connection;
import mongoTools.MongoWriter;
import net.sf.json.JSONObject;
import tools.Tools;

/**
 * Servlet implementation class Order
 */
@WebServlet("/order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Order() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	
            if (!Tools.policyStatus) {
                Tools.setPolicy();
            }
            
            String requestStr = Tools.decoder(Tools.getContent(request));        
            OrderInfoBean orderInfoBean = (OrderInfoBean)Tools.jsonToBean(requestStr, OrderInfoBean.class);
            String dataid = orderInfoBean.partnerId;
            
            //获取dataid
            boolean isUpdate = dataid != null && dataid.trim().length() > 0;
            if(isUpdate){
                dataid = dataid.substring(2);
            } else{
                String yextCates = (orderInfoBean.categories.get(0)).name;
                String sogouCates = (String)Tools.getCategoryMap().get(yextCates);

                String categories[] = sogouCates.split(";;");
                String dataidAndUid[] = Tools.getDataIdAndUid(orderInfoBean.name, orderInfoBean.geoData.displayLongitude, orderInfoBean.geoData.displayLatitude, categories[0], categories[1]);
                dataid = dataidAndUid[0];
            }
            
            //返回结果，当前仅为模拟结果
            JSONObject responseJson = new JSONObject();
            responseJson.element("status", "LIVE")
            			.element("id"    , "1_" + dataid)
            			.element("url"   , "http://map.sogou.com/poi/1_" + dataid);
            response.getWriter().println(responseJson);
            
            //xml 和 log 信息写入数据库
            MongoWriter mongoWriter = new MongoWriter();
            mongoWriter.writeOrder(requestStr);
            mongoWriter.writeLog(request.getMethod() == "POST" ? "order" : "update", requestStr, responseJson.toString());
        }

        protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
        {
            doPost(request, response);
        }
}
