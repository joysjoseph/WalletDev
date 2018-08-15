package com.flowers.es.wallet.helper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.flowers.es.wallet.uril.DataLoadPropertyUtil;

public class IPGTransactionHelper {
	static org.apache.log4j.Logger  m_log = org.apache.log4j.Logger.getLogger(IPGTransactionHelper.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String sendRequestToIPG(String str_requestXML) throws Exception{

		if(str_requestXML == null || str_requestXML.length() <=0){
			return "";
		}
		
		String ipgToken = "";
		String str_responseXML=""; 
	//str_requestXML = "<BAT><SRC>FLOATLAS</SRC><SEQ>198</SEQ><CNT>7</CNT><TRX><SVC>Collect</SVC><PRJ>FLOFLOWERS</PRJ><CTY>US</CTY><ORD>WA3444503</ORD><CUR>USD</CUR><NET>5.1</NET><TAX>0.0</TAX><GRS>5.1</GRS><XIE>304</XIE><XRF>2F84T914RVTC1</XRF><TL0>259580</TL0><TL1>1001</TL1></TRX><TRX><SVC>Collect</SVC><PRJ>FLOFLOWERS</PRJ><CTY>US</CTY><ORD>WA3444503</ORD><CUR>USD</CUR><NET>5.1</NET><TAX>0.0</TAX><GRS>5.1</GRS><XIE>305</XIE><XRF>2F84T914RVTC1</XRF><TL0>259580</TL0><TL1>1001</TL1></TRX><TRX><SVC>Collect</SVC><PRJ>FLOFLOWERS</PRJ><CTY>US</CTY><ORD>WA3444503</ORD><CUR>USD</CUR><NET>1.1</NET><TAX>0.0</TAX><GRS>1.1</GRS><XIE>306</XIE><XRF>2F84T914RVTC1</XRF><TL0>259580</TL0><TL1>1002</TL1></TRX><TRX><SVC>Collect</SVC><PRJ>FLOFLOWERS</PRJ><CTY>US</CTY><ORD>WA3444503</ORD><CUR>USD</CUR><NET>1.1</NET><TAX>0.0</TAX><GRS>1.1</GRS><XIE>307</XIE><XRF>2F84T914RVTC1</XRF><TL0>259580</TL0><TL1>1002</TL1></TRX><TRX><SVC>Collect</SVC><PRJ>FLOFLOWERS</PRJ><CTY>US</CTY><ORD>WA3456</ORD><CUR>USD</CUR><NET>5.1</NET><TAX>0.0</TAX><GRS>5.1</GRS><XIE>308</XIE><XRF>Y22YSSJW1GTC5</XRF><TL0>259580</TL0><TL1>1001</TL1></TRX><TRX><SVC>Collect</SVC><PRJ>FLOFLOWERS</PRJ><CTY>US</CTY><ORD>WA345603</ORD><CUR>USD</CUR><NET>5.1</NET><TAX>0.0</TAX><GRS>5.1</GRS><XIE>309</XIE><XRF>DFMJHYCVR3TC1</XRF><TL0>259580</TL0><TL1>1001</TL1></TRX><TRX><SVC>Collect</SVC><PRJ>FLOFLOWERS</PRJ><CTY>US</CTY><ORD>WA345613</ORD><CUR>USD</CUR><NET>5.1</NET><TAX>0.0</TAX><GRS>5.1</GRS><XIE>310</XIE><XRF>84A1FABJCZTC1</XRF><TL0>259580</TL0><TL1>1001</TL1></TRX></BAT>";
		
		String url = DataLoadPropertyUtil.getFuctionalParameterValue("AZ_IPG_URL", "");
		url = url+"/tkn";
				//"https://ips-preprod.ihost.com:50442/bat";//IPGPaymentUtil.getFuctionalParameterValue("IPG_URL","");//"https://ips-preprod.ihost.com:50443/tkn";
		//url="https://tmcpnydev01:8443/TokenManagerEngine/services/TokenService2.0";
		System.setProperty("javax.net.ssl.keyStore","D:\\home\\site\\cert\\brkKeystore.jks");  //CITIAUTH.cer  ANLCert.cer
		System.setProperty("javax.net.ssl.trustStore","D:\\home\\site\\cert\\brktrustKeystore.jks");  //CITIAUTH.cer  ANLCert.cer
		//System.setProperty("javax.net.ssl.keyStore","C:\\joys\\IIB9PJTS\\IBMPayment\\certs\\brkKeystore.jks");
		//System.setProperty("javax.net.ssl.trustStore","C:\\joys\\IIB9PJTS\\IBMPayment\\certs\\brktrustKeystore.jks");
		

		System.setProperty("javax.net.ssl.keyStorePassword","password");  //CITIAUTH.cer  ANLCert.cer
		System.setProperty("javax.net.ssl.trustStorePassword","password");  
		System.setProperty("javax.net.ssl.trustStoreType","jks"); 
		System.setProperty("javax.net.ssl.keyStoreType","jks");

		System.setProperty("https.protocols", "TLSv1.2");
		URL obj;
		try {
			obj = new URL(url);

			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();	

			con.setRequestMethod("POST");	

			con.setRequestProperty("Accept", "application/xml");
			con.setRequestProperty("Content-Type", "application/xml");	

			con.setDoOutput(true);
			con.setUseCaches(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			System.out.println("uuuuuuuuuuuuuuuuuuuu: "+con.getCipherSuite());
			//System.out.println("Request XML:"+str_requestXML);
			wr.writeBytes(str_requestXML);
			wr.flush();
			wr.close();


			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			//System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			if(responseCode == 200){
				System.out.println(" Response Code :----> " + responseCode);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				//print result
				str_responseXML = response.toString();
				System.out.println(response.toString());
				/*if(str_responseXML.contains("<TKI>")){
					ipgToken =  str_responseXML.substring(str_responseXML.indexOf("<TKI>")+"<TKI>".length(), str_responseXML.indexOf("</TKI>"));
				}*/
			}
			else{
				System.out.println(" Response Code :----> " + responseCode);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				//print result
				str_responseXML = response.toString();
				System.out.println(response.toString());
				//card_number =  str_responseXML.substring(str_responseXML.indexOf("<token>")+"<token>".length(), str_responseXML.indexOf("</token>"));
				str_responseXML =  response.toString();//"FALSE~IPG Tokenization Failed with HTTP Status code "+responseCode;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			str_responseXML =  "FALSE~IPG Batch request Failed with Exception "+e.getMessage();
			throw new Exception("Request to IPG Failed with Exception:"+e.getMessage());
		}




		return str_responseXML;

	}
	
	

}
