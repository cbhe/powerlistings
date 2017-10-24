import java.rmi.RemoteException;

import com.go2map.lsp.local.engine.mapservice.fulltextindex.POIManager2;
import com.oracle.webservices.internal.api.databinding.Databinding;

import tools.Tools;

public class Test {
	public static void main(String ...args) throws RemoteException{
		if(Tools.policyStatus == false){
			Tools.setPolicy();
		}
		String dataid = "1_D1000443103746";
		String uid = POIManager2.getUIDbyDataid(dataid);
		System.out.println(uid);
	}
}
