package com.flowers.es.wallet.uril;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.flowers.es.wallet.helper.IPGTransactionHelper;
import com.microsoft.sqlserver.jdbc.*;

import io.swagger.model.Wallet;
import io.swagger.model.WalletRequest;
import io.swagger.model.WalletResponse;


import io.swagger.model.WalletResult;
import io.swagger.model.Wallets;
import io.swagger.model.WalletsResponse;
import io.swagger.model.WalletsResult;


public class DBUtil {
	static org.apache.log4j.Logger  m_log = org.apache.log4j.Logger.getLogger(DBUtil.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			//callUSP_GetAccountRefInfo("3123");
			//deleteWalletCard("4444341234","8");
			//callUpdateUSP_INSERT_UPDATE_WalletID(strAccountRefID, walletRequest)
			//WalletRequest2 req = new WalletRequest2();
			//WalletResult1Wallet wallet = new ;
			//req.setWallet(wallet );
			//callUSP_GetWalletInfoforSub("123456");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static WalletResponse deleteWalletCard(String strAccountRefID, String strwalledId) throws SQLException{
		Connection con = null;
		CallableStatement cstmt = null;
		WalletResponse res = setDefaultWalletResponseData(null);
		con = getSQLDBConnection();
		cstmt = con.prepareCall("{call microsvcdb.wallet.USP_UPDATE_WalletID(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		//cstmt.setString(1, str_xml);
		cstmt.setLong("AccountRefID", Long.parseLong(strAccountRefID));			
		cstmt.setLong("WalletID", Long.parseLong(strwalledId));	
		cstmt.setString("CardType", null);
		cstmt.setString("PayType", null);
		cstmt.setString("Status", "I");	
		cstmt.setString("CardHolderName", null);
		cstmt.setString("CardNumber", null);
		cstmt.setString("LastFour", null);
		cstmt.setString("Expdate", null);
		cstmt.setString("User", "ES");
		cstmt.setShort("SubscrFlag", Short.parseShort("0"));
		//cstmt.registerOutParameter("WalletID_New",  Types.BIGINT);
		/*cstmt.setLong(1, Long.parseLong(strAccountRefID));			
		cstmt.setString(2, null);
		cstmt.setString(3, null);
		cstmt.setShort(4, Short.parseShort("1"));
		cstmt.setString(5, null);
		cstmt.setString(6, null);
		cstmt.setNull(7, Types.TINYINT);
		cstmt.setString(8, null);
		cstmt.setString(9, "JOYS");*/
		//cstmt.sets
		//cstmt.setString(9, "JOYS");
		//cstmt.setLong("WalletID", Long.parseLong(strwalledId));
		cstmt.registerOutParameter("ERRCD", Types.INTEGER);
		cstmt.registerOutParameter("ERRCDDetail", Types.INTEGER);
		cstmt.registerOutParameter("ERRCDDescr", Types.VARCHAR);
		m_log.info("Starting USP_INSERT_UPDATE_WalletID for deleting walled id: "+strwalledId+"for: "+strAccountRefID);	

		cstmt.execute();
		m_log.info("Starting USP_INSERT_UPDATE_WalletID completd for deleting walled id ");
		int errorcode=0;
		
		if(cstmt.getObject("ERRCD")!= null){
			errorcode = (int) cstmt.getObject("ERRCD");
			m_log.info("erroce code  for USP_INSERT_UPDATE_WalletID "+errorcode);	
		}
		int det_errorcode=0;
		if(cstmt.getObject("ERRCDDetail")!= null){
			det_errorcode = (int) cstmt.getObject("ERRCDDetail");
			m_log.info("detailed code  for USP_INSERT_UPDATE_WalletID "+det_errorcode);	
		}
		/*if(cstmt.getObject(12)!= null){
			String str_err_mesage= (String) cstmt.getObject(11);
			m_log.info("errr message  for USP_INSERT_UPDATE_WalletID "+str_err_mesage);	
		}*/
		String str_err_mesage = "";
		if(cstmt.getObject("ERRCDDescr")!= null){
			str_err_mesage = (String) cstmt.getObject("ERRCDDescr");
			m_log.info("errr message  for USP_INSERT_UPDATE_WalletID "+str_err_mesage);	
		}
		if(errorcode != 0){
			addErrorObjectsToResponse(res, str_err_mesage, det_errorcode, errorcode+"");
		}
		
		return res;
		
	}
	public static WalletResponse callUpdateUSP_INSERT_UPDATE_WalletID(String strAccountRefID    , WalletRequest walletRequest) throws Exception{

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;		
		int iseq =0;
		long i_walletID = 0;
		WalletResponse res = setDefaultWalletResponseData(null);
		Wallet wallet = walletRequest.getWallet();
		
		//wallet.get

		con = getSQLDBConnection();
		//cstmt = con.prepareCall("{call microsvcdb.wallet.USP_INSERT_UPDATE_WalletID(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cstmt = con.prepareCall("{call microsvcdb.wallet.USP_UPDATE_WalletID(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		//cstmt.setString(1, str_xml);
		
		if(wallet.getExpiryDate() != null && "HIGHH".equalsIgnoreCase(wallet.getExpiryDate() )){ //joys need to remove
			wallet.setExpiryDate("");
			wallet.setSubsIndicator("1");
		}
		cstmt.setLong("AccountRefID", Long.parseLong(strAccountRefID));			
		cstmt.setLong("WalletID", Long.parseLong(wallet.getWalletId()));	
		cstmt.setString("CardType", wallet.getCardType());
		cstmt.setString("PayType", wallet.getPayType());
		if(wallet.getStatus() != null && wallet.getStatus().trim().length() > 0){
			cstmt.setString("Status", wallet.getStatus().trim());
		}
		else{
			cstmt.setNull("Status", Types.VARCHAR);
		}			
		cstmt.setString("CardHolderName", wallet.getCardHolderName());
		String str_re_enc_cardnumber = getFlwsEncryptedCCFromMobileRequest(wallet.getCardNumber(),wallet.getSourceID());
		cstmt.setString("CardNumber", str_re_enc_cardnumber);
		cstmt.setString("LastFour", wallet.getLastFourDigits());
		cstmt.setString("Expdate", wallet.getExpiryDate());
		String str_user=wallet.getUser();
		if(str_user!= null && str_user.trim().length() > 0){
			
		}
		else{
			str_user = "ES";
		}
		cstmt.setString("User", str_user);
		///cstmt.setShort("SubscrFlag", Short.parseShort("0"));
		//New Pricdure cstmt.registerOutParameter("WalletID_New",  Types.BIGINT);
		/*cstmt.setLong(1, Long.parseLong(strAccountRefID));			
		cstmt.setString(2, null);
		cstmt.setString(3, null);
		cstmt.setShort(4, Short.parseShort("1"));
		cstmt.setString(5, null);
		cstmt.setString(6, null);
		cstmt.setNull(7, Types.TINYINT);
		cstmt.setString(8, null);
		cstmt.setString(9, "JOYS");*/
		//cstmt.sets
		//cstmt.setString(9, "JOYS");
		//cstmt.setLong("WalletID", Long.parseLong(strwalledId));
		/*cstmt.registerOutParameter("ERRCD", Types.BIGINT);
		cstmt.registerOutParameter("ERRCDDescr", Types.VARCHAR);
		
		cstmt.setLong(1, Long.parseLong(strAccountRefID));			
		cstmt.setString(2, wallet.getCardType());
		cstmt.setString(3, wallet.getPayType());
		cstmt.setShort(4, Short.parseShort("0"));
		cstmt.setString(5, wallet.getCardHolderName());
		cstmt.setString(6, wallet.getCardNumber());
		cstmt.setShort(7, Short.parseShort(wallet.getLastFourDigits()));
		cstmt.setString(8, wallet.getExpiryDate());
		cstmt.setString(9, "JOYS");
		//cstmt.setString(9, "JOYS");
		cstmt.registerOutParameter(10, Types.BIGINT);
		cstmt.registerOutParameter(11, Types.INTEGER);*/
		//cstmt.registerOutParameter(12, Types.VARCHAR);
		if(wallet.getSubsIndicator() != null && wallet.getSubsIndicator().length() >0){
			cstmt.setShort("SubscrFlag", Short.parseShort(wallet.getSubsIndicator()));
		}
		else{
			cstmt.setShort("SubscrFlag", Short.parseShort("0"));
		}
		cstmt.registerOutParameter("ERRCD", Types.INTEGER);
		cstmt.registerOutParameter("ERRCDDetail", Types.INTEGER);
		cstmt.registerOutParameter("ERRCDDescr", Types.VARCHAR);
		m_log.info("Starting USP_INSERT_UPDATE_WalletID for "+strAccountRefID);	

		cstmt.execute();
		m_log.info("Starting USP_INSERT_UPDATE_WalletID completd for "+strAccountRefID);

		if(cstmt.getObject("ERRCD")!= null){
			iseq = (int) cstmt.getObject("ERRCD");
			m_log.info("erroce code  for USP_INSERT_UPDATE_WalletID "+iseq);	
		}
		int det_errorcode=0;
		if(cstmt.getObject("ERRCDDetail")!= null){
			det_errorcode = (int) cstmt.getObject("ERRCDDetail");
			m_log.info("detailed code  for USP_INSERT_UPDATE_WalletID "+det_errorcode);	
		}
		String str_err_mesage = "";
		if(cstmt.getObject("ERRCDDescr")!= null){
			 str_err_mesage= (String) cstmt.getObject("ERRCDDescr");
			m_log.info("errr message  for USP_INSERT_UPDATE_WalletID "+str_err_mesage);	
		}
		
		/*if(cstmt.getObject("WalletID_New")!= null){
			i_walletID = (long) cstmt.getObject("WalletID_New");
			m_log.info("Completd  USP_INSERT_UPDATE_WalletID and Wallet ID: "+i_walletID);	
		}*/
		if(iseq != 0){
			addErrorObjectsToResponse(res, str_err_mesage, det_errorcode, iseq+"");
			return res;
		}
		WalletResult result = new WalletResult();
		Wallet walletre = new Wallet();
		walletre.setWalletId(wallet.getWalletId()+"");
		result.setWallet(walletre );
		res.setResult(result );

		return res;
	}
	
	public static void insertDetails(String strAccountRefID){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String str_insert_querry ="insert into [Wallet].[AccountRefIDWallet] (AccountRefID, Status, DateCreated, UserCreated, DateModified, UserModified) VALUES (?, 1, convert(datetime,'23-03-18 10:34:09 PM',5), ('temp') , NULL, NULL )";
		con = getSQLDBConnection();
		try {
			pstmt = con.prepareStatement(str_insert_querry);
			pstmt.setLong(1,Long.parseLong(strAccountRefID));
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m_log.error("Error"+e.getMessage());
		}
		m_log.info("Connection Completed");
	}
	
	public static WalletResponse callUSP_INSERT_UPDATE_WalletID(String strAccountRefID    , WalletRequest walletRequest) throws Exception{

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;		
		int iseq =0;
		long i_walletID = 0;
		WalletResponse res = setDefaultWalletResponseData(null);
		
		Wallet wallet = walletRequest.getWallet();
		
		String str_request_card_number = wallet.getCardNumber();
		m_log.info("Starting Call IPG for "+str_request_card_number);
		
		/*if(WalletUtil.isValidCCNumber(str_request_card_number)){
			String IPG_XML = WalletUtil.getIPGTokenRequestXML(str_request_card_number);
			String IPG_response_XML = IPGTransactionHelper.sendRequestToIPG(IPG_XML);
			if(IPG_response_XML.contains("<TKI>")){
				str_request_card_number = IPG_response_XML.substring(IPG_response_XML.indexOf("<TKI>")+"<TKI>".length(),IPG_response_XML.indexOf("</TKI>"));
			}
		}*/
		m_log.info("Completed Call IPG for "+str_request_card_number);
		con = getSQLDBConnection();
		m_log.info("Connection Completed");
		//cstmt = con.prepareCall("{call microsvcdb.wallet.USP_INSERT_UPDATE_WalletID(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cstmt = con.prepareCall("{call microsvcdb.wallet.USP_INSERT_WalletID(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		//cstmt.setString(1, str_xml);
		/*cstmt.setLong(1, Long.parseLong(strAccountRefID));			
		cstmt.setString(2, wallet.getCardType());
		cstmt.setString(3, wallet.getPayType());
		cstmt.setShort(4, Short.parseShort("0")); 
		cstmt.setString(5, wallet.getCardHolderName());
		cstmt.setString(6, wallet.getCardNumber());
		cstmt.setShort(7, Short.parseShort(wallet.getLastFourDigits()));
		cstmt.setString(8, wallet.getExpiryDate());
		cstmt.setString(9, "JOYS");
		//cstmt.setString(9, "JOYS");
		cstmt.registerOutParameter(10, Types.BIGINT);
		cstmt.registerOutParameter(11, Types.INTEGER);*/
		
		cstmt.setLong("AccountRefID", Long.parseLong(strAccountRefID));			
		//cstmt.setNull("WalletID", Types.BIGINT);	
		cstmt.setString("CardType", wallet.getCardType());
		cstmt.setString("PayType", wallet.getPayType());
		if(wallet.getStatus() != null && wallet.getStatus().trim().length() > 0){
			cstmt.setString("Status", wallet.getStatus().trim());
		}
		else{
			cstmt.setString("Status", "A");
		}			
		cstmt.setString("CardHolderName", wallet.getCardHolderName());
		
		String str_re_enc_cardnumber = getFlwsEncryptedCCFromMobileRequest(wallet.getCardNumber(),wallet.getSourceID());
		cstmt.setString("CardNumber", str_re_enc_cardnumber);
		cstmt.setString("LastFour", wallet.getLastFourDigits());
		cstmt.setString("Expdate", wallet.getExpiryDate());
		String str_user=wallet.getUser();
		if(str_user!= null && str_user.trim().length() > 0){
			
		}
		else{
			str_user = "ES";
		}
		cstmt.setString("User", str_user);
		if(wallet.getSubsIndicator() != null && wallet.getSubsIndicator().length() >0){
			cstmt.setShort("SubscrFlag", Short.parseShort(wallet.getSubsIndicator()));
		}
		else{
			cstmt.setShort("SubscrFlag", Short.parseShort("0"));
		}
		cstmt.registerOutParameter("WalletID_New",  Types.BIGINT);
		cstmt.registerOutParameter("ERRCD", Types.INTEGER);
		cstmt.registerOutParameter("ERRCDDetail", Types.INTEGER);
		cstmt.registerOutParameter("ERRCDDescr", Types.VARCHAR);
		
		//cstmt.registerOutParameter(12, Types.VARCHAR);
		m_log.info("Starting USP_INSERT_WalletID for "+strAccountRefID);	

		cstmt.execute();
		m_log.info("Starting USP_INSERT_WalletID completd for "+strAccountRefID);
		if(cstmt.getObject("WalletID_New")!= null){
			i_walletID = (long) cstmt.getObject("WalletID_New");
			m_log.info("Completd  USP_INSERT_WalletID and Wallet ID: "+i_walletID);	
		}
		if(cstmt.getObject("ERRCD")!= null){
			iseq = (int) cstmt.getObject("ERRCD");
			m_log.info("erroce code  for USP_INSERT_WalletID "+iseq);	
		}
		int det_errorcode = 0;
		if(cstmt.getObject("ERRCDDetail")!= null){
			det_errorcode = (int) cstmt.getObject("ERRCDDetail");
			m_log.info("detailed code  for USP_INSERT_WalletID "+det_errorcode);	
		}
		String str_err_mesage="";
		if(cstmt.getObject("ERRCDDescr")!= null){
			 str_err_mesage= (String) cstmt.getObject("ERRCDDescr");
			m_log.info("errr message  for USP_INSERT_WalletID "+str_err_mesage);	
		}
		
		if(iseq != 0){
			addErrorObjectsToResponse(res, str_err_mesage, det_errorcode, iseq+"");
			return res;
		}
		WalletResult result = new WalletResult();
		Wallet walletre = new Wallet();
		walletre.setWalletId(i_walletID+"");
		//walletre.
		result.setWallet(walletre );
		res.setResult(result );

		return res;
	}
	public static Connection getSQLDBConnection(){
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		/*String hostname = DataLoadPropertyUtil.getFuctionalParameterValue("DB_HOST", "");
		String portNumber = DataLoadPropertyUtil.getFuctionalParameterValue("DB_PORT", "");
		String str_DB_Name = DataLoadPropertyUtil.getFuctionalParameterValue("DB_NAME", "");
		String str_DB_user= DataLoadPropertyUtil.getFuctionalParameterValue("DB_USER", "");
		String str_DB_pass = DataLoadPropertyUtil.getFuctionalParameterValue("DB_PASS", "");*/
		SQLServerDataSource ds = new SQLServerDataSource();
		//ds.setIntegratedSecurity(true);
		/*ds.setServerName("10.201.43.101");
		ds.setPortNumber(1433); 
		ds.setDatabaseName("MicroSvcDB");
		ds.setUser("paymentdev");
		ds.setPassword("paymentdev");*/

		/*ds.setServerName(hostname);
		ds.setPortNumber(Integer.parseInt(portNumber)); 
		ds.setDatabaseName(str_DB_Name);
		ds.setUser(str_DB_user);
		ds.setPassword(str_DB_pass);*/
		
		//String str_new_url="jdbc:sqlserver://msazdbt01.database.windows.net:1433;database=MicroSvcDB;user=webservMicro@msazdbt01;password=webM1%$12rJKlm@01;encrypt=true;trustServerCertificate=false;hostNameInCertificate=eastus1-a.control.database.windows.net;loginTimeout=30";
		String str_new_url = DataLoadPropertyUtil.getFuctionalParameterValue("AZ_DB_URL", "");
		String str_az_url=System.getenv("SQLAZURECONNSTR_MS_WalletDbConnection");
		m_log.info("SQLAZURECONNSTR_--------- "+str_az_url);
		//str_az_url = str_az_url.replaceAll("Data Source=","");
		m_log.info("new SQLAZURECONNSTR_--------- "+str_az_url);
		//String str_new_url = "jdbc:sqlserver://msazdbt01.database.windows.net:1433;database=PaymentDB;user=webservuser@msazdbt01;password=web12rJKlm@01;encrypt=true;trustServerCertificate=false;hostNameInCertificate=eastus1-a.control.database.windows.net;loginTimeout=30";
		//Data Source=tcp:msazdbt01.database.windows.net,1433;Initial Catalog=MicroSvcDB;User ID=webservMicro@msazdbt01;Password=webM1%$12rJKlm@01
		String dbArr[] = str_az_url.split(";");
		String hostName = dbArr[0].replaceAll("Data Source=tcp:", "");
		hostName =hostName.replaceAll(",1433", "");
        String dbName = dbArr[1].replaceAll("Initial Catalog=", "");
        String user = dbArr[2].replaceAll("User ID=", "");;
        String password = dbArr[3].replaceAll("Password=", "");;
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
        m_log.info("NEW URL:--------"+url);
        ds.setURL(url);
		//ds.setURL(str_new_url);
		/*ds.setURL(str_az_url);
		ds.setEncrypt(true);
		ds.setHostNameInCertificate("eastus1-a.control.database.windows.net");
		*/
        //ds.set
		ds.setTrustServerCertificate(true);
		try {
			//DBConnectionUtil.getDBConnection("jdbc:apache:commons:dbcp:/PaymentDB");
			//m_log.info("DB Connection URL:"+str_new_url);
			m_log.info("DB Connection Starting...........:");			
			con = ds.getConnection();
			m_log.info("DB Connection Completed........:");
		} catch (SQLServerException e) {
			// TODO Auto-generated catch block
			m_log.info("DB Connection failed........:"+e.getMessage());
			getErrorStackTrace(e);
			e.printStackTrace();
		}
		return con;
	}
	public static WalletsResponse callUSP_GetAccountRefInfo(String str_externalReferenceID, String user) throws Exception{
		
		WalletsResponse walletsResponse = setDefaultWalletsResponseData(null);
		//Wallets1 result ;
		//WalletsResponse.setResult(result );

		Wallets wallets = new Wallets();
		//List<WalletsResult> wallet =  new ArrayList<WalletsResult>();
		//wallet =  new List();
		//List<WalletResult1Wallet> wallet = new List<WalletResult1Wallet>;
		//walletssetWallet(wallet);
		
		//walletsList.asetWallet(wallet);
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;	
		long long_externalReferenceID=0;
		long long_walletID =0;
		
		m_log.info("Calling   USP_GetAccountRefInfo for:"+str_externalReferenceID);
		if(str_externalReferenceID != null && str_externalReferenceID.trim().length() > 0){
			str_externalReferenceID = str_externalReferenceID.trim();
			long_externalReferenceID = Long.parseLong(str_externalReferenceID);
		}
		int  errorCode=0;

		con = getSQLDBConnection();
		m_log.info("Connection for   USP_GetAccountRefInfo completed");
		cstmt = con.prepareCall("{call microsvcdb.Wallet.USP_GetWalletInfo(?,?,?,?,?)}");
		cstmt.setLong(1, long_externalReferenceID);
		cstmt.setNull(2, Types.BIGINT);//(2, long_walletID);
		//cstmt.setLong(2, long_walletID);//(1, long_walletID);
		cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.registerOutParameter(5, Types.INTEGER);

		cstmt.execute();
		m_log.info("Executing  USP_GetAccountRefInfo Completed");	
		rs =  cstmt.getResultSet();
		if(rs!=null){
			while(rs.next()){
				Wallet walletItem = new Wallet();
				walletItem.setCardHolderName(rs.getString("CardHolderName"));
				String str_re_enc_cardnumber = getMobileEncryptedCCFromWallet(rs.getString("CardNumber"),user);
				m_log.info("new Card nummber"+str_re_enc_cardnumber);
				walletItem.setCardNumber(str_re_enc_cardnumber);
				walletItem.setCardType(rs.getString("CardTypeID"));
				walletItem.setExpiryDate(rs.getString("Expdate"));
				//walletItem.setFirstTwoDigits(rs.getString("CardTypeID"));
				walletItem.setLastFourDigits(rs.getString("LastFour"));
				walletItem.setPayType(rs.getString("PayTypeID"));
				walletItem.setWalletId(rs.getString("WalletId"));
				walletItem.setStatus(rs.getString("Status"));
				walletItem.setSubsIndicator(rs.getString("SubscriptionFlag"));
				wallets.addWalletItem(walletItem);
				//wallet.
			}
		}

		if(cstmt.getObject(3)!= null){
			errorCode = (int) cstmt.getObject(3);
			m_log.info("error code  for USP_GetAccountRefInfo "+errorCode);	
		}
		int det_errorcode = 0;
		if(cstmt.getObject(5)!= null){
			det_errorcode = (int) cstmt.getObject(5);
			m_log.info("detailed code  for USP_GetAccountRefInfo "+det_errorcode);	
		}
		String str_error_msg ="";
		if(cstmt.getObject(4)!= null){
			 str_error_msg = (String) cstmt.getObject(4);
			m_log.info("errore message  for USP_GetAccountRefInfo "+str_error_msg);	
		}
		if(errorCode != 0){
			addErrorObjectsToWalletsResponse(walletsResponse,str_error_msg,det_errorcode,errorCode+"");
			return walletsResponse;
		}
		walletsResponse.setResult(wallets );
		return walletsResponse;
	}

	public static WalletResponse callUSP_GetWalletInfo(String str_externalReferenceID,String str_walletID,String user) throws Exception{

		WalletResponse res = setDefaultWalletResponseData(null);
		WalletResult walletResult = new WalletResult();
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;	
		long long_externalReferenceID=0;
		long long_walletID =Long.parseLong(str_walletID);

		m_log.info("Calling   USP_GetWalletInfo for:"+str_externalReferenceID);
		if(str_externalReferenceID != null && str_externalReferenceID.trim().length() > 0){
			str_externalReferenceID = str_externalReferenceID.trim();
			long_externalReferenceID = Long.parseLong(str_externalReferenceID);
		}
		int  errorCode=0;

		con = getSQLDBConnection();
		m_log.info("Connection for   USP_GetWalletInfo completed");
		cstmt = con.prepareCall("{call microsvcdb.Wallet.USP_GetWalletInfo(?,?,?,?,?)}");
		cstmt.setLong(1, long_externalReferenceID);
		//cstmt.setNull(2, Types.BIGINT);//(2, long_walletID);
		cstmt.setLong(2, long_walletID);//(1, long_walletID);
		cstmt.registerOutParameter(3, Types.INTEGER);
		cstmt.registerOutParameter(4, Types.VARCHAR);
		cstmt.registerOutParameter(5, Types.INTEGER);
		m_log.info("Executing USP_GetWalletInfo");

		cstmt.execute();
		m_log.info("Executing USP_GetWalletInfo Completed");		


		rs =  cstmt.getResultSet();
		if(rs !=null){
			while(rs.next()){
				Wallet walletItem = new Wallet();
				walletItem.setCardHolderName(rs.getString("CardHolderName"));//CardHolderName
				String str_re_enc_cardnumber = getMobileEncryptedCCFromWallet(rs.getString("CardNumber"),user);				
				walletItem.setCardNumber(str_re_enc_cardnumber);
				//walletItem.setCardNumber(rs.getString("CardNumber"));
				walletItem.setCardType(rs.getString("CardTypeID"));
				walletItem.setExpiryDate(rs.getString("Expdate"));
				//walletItem.setFirstTwoDigits(rs.getString("CardTypeID"));
				walletItem.setLastFourDigits(rs.getString("LastFour"));
				walletItem.setPayType(rs.getString("PayTypeID"));
				walletItem.setWalletId(rs.getString("WalletId"));
				walletItem.setStatus(rs.getString("Status")+"");
				walletItem.setSubsIndicator(rs.getString("SubscriptionFlag"));
				///WalletResult1Wallet walletItem22;
				walletResult.setWallet(walletItem);
				m_log.info("In loop Wallet"+walletItem.getWalletId());
			}
		}
		if(cstmt.getObject(3)!= null){
			errorCode = (int) cstmt.getObject(3);
			m_log.info("error code  for USP_GetWalletInfo "+errorCode);	
		}
		if(errorCode==0){	

			//m_log.info("error code  for USP_GetWalletInfo "+errorCode);	
		}
		int det_errorcode = 0;
		if(cstmt.getObject(5)!= null){
			det_errorcode = (int) cstmt.getObject(5);
			m_log.info("detailed code  for USP_GetWalletInfo "+det_errorcode);	
		}
		String str_error_msg ="";
		if(cstmt.getObject(4)!= null){
			str_error_msg = (String) cstmt.getObject(4);
			m_log.info("error message  for USP_GetWalletInfo "+str_error_msg);	
		}
		if(errorCode!=0){
			addErrorObjectsToResponse(res,str_error_msg,det_errorcode,errorCode+"");
			return res;
		}
		res.setResult(walletResult);
		return res;
	}
	
	public static WalletResponse callUSP_GetWalletInfoforSub(String str_externalReferenceID,String user) throws Exception{

		WalletResponse res = setDefaultWalletResponseData(null);
		WalletResult walletResult = new WalletResult();
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;	
		long long_externalReferenceID=0;
		//long long_walletID =Long.parseLong(str_walletID);

		m_log.info("Calling   USP_GetWalletInfo for:"+str_externalReferenceID);
		if(str_externalReferenceID != null && str_externalReferenceID.trim().length() > 0){
			str_externalReferenceID = str_externalReferenceID.trim();
			long_externalReferenceID = Long.parseLong(str_externalReferenceID);
		}
		int  errorCode=0;

		con = getSQLDBConnection();
		m_log.info("Connection for   USP_GetSubscriptionInfo completed");
		cstmt = con.prepareCall("{call microsvcdb.Wallet.USP_GetSubscriptionInfo(?,?,?,?)}");
		cstmt.setLong(1, long_externalReferenceID);
		//cstmt.setNull(2, Types.BIGINT);//(2, long_walletID);
		//cstmt.setLong(2, long_walletID);//(1, long_walletID);
		cstmt.registerOutParameter(2, Types.INTEGER);
		cstmt.registerOutParameter(3, Types.VARCHAR);
		cstmt.registerOutParameter(4, Types.INTEGER);
		m_log.info("Executing USP_GetSubscriptionInfo");

		cstmt.execute();
		m_log.info("Executing USP_GetSubscriptionInfo Completed");		


		rs =  cstmt.getResultSet();
		if(rs !=null){
			while(rs.next()){
				Wallet walletItem = new Wallet();
				walletItem.setCardHolderName(rs.getString("CardHolderName"));//CardHolderName
				String str_re_enc_cardnumber = getMobileEncryptedCCFromWallet(rs.getString("CardNumber"),user);				
				walletItem.setCardNumber(str_re_enc_cardnumber);
				//walletItem.setCardNumber(rs.getString("CardNumber"));
				walletItem.setCardType(rs.getString("CardTypeID"));
				walletItem.setExpiryDate(rs.getString("Expdate"));
				//walletItem.setFirstTwoDigits(rs.getString("CardTypeID"));
				walletItem.setLastFourDigits(rs.getString("LastFour"));
				walletItem.setPayType(rs.getString("PayTypeID"));
				walletItem.setWalletId(rs.getString("WalletId"));
				walletItem.setStatus(rs.getString("Status")+"");
				walletItem.setSubsIndicator(rs.getString("SubscriptionFlag"));
				///WalletResult1Wallet walletItem22;
				walletResult.setWallet(walletItem);
				m_log.info("In loop Wallet"+walletItem.getWalletId());
			}
		}
		if(cstmt.getObject(2)!= null){
			errorCode = (int) cstmt.getObject(2);
			m_log.info("error code  for USP_GetSubscriptionInfo "+errorCode);	
		}
		if(errorCode==0){	

			//m_log.info("error code  for USP_GetWalletInfo "+errorCode);	
		}
		int det_errorcode = 0;
		if(cstmt.getObject(4)!= null){
			det_errorcode = (int) cstmt.getObject(4);
			m_log.info("detailed code  for USP_GetSubscriptionInfo "+det_errorcode);	
		}
		String str_error_msg ="";
		if(cstmt.getObject(3)!= null){
			str_error_msg = (String) cstmt.getObject(3);
			m_log.info("error message  for USP_GetSubscriptionInfo "+str_error_msg);	
		}
		
		
		/*if(cstmt.getObject(2)!= null){
			//errorCode = (int) cstmt.getObject(3);
			errorCode =111;
			m_log.info("error code  for USP_GetSubscriptionInfo "+errorCode);	
		}
		if(errorCode==0){	

			//m_log.info("error code  for USP_GetWalletInfo "+errorCode);	
		}
		int det_errorcode = 0;
		if(cstmt.getObject(4)!= null){
			//det_errorcode = (int) cstmt.getObject(5);
			det_errorcode = 234;
			m_log.info("detailed code  for USP_GetSubscriptionInfo "+det_errorcode);	
		}
		String str_error_msg ="";
		if(cstmt.getObject(3)!= null){
			str_error_msg = (String) cstmt.getObject(3);
			m_log.info("error message  for USP_GetSubscriptionInfo "+str_error_msg);	
		}
*/if(errorCode!=0){
			addErrorObjectsToResponse(res,str_error_msg,det_errorcode,errorCode+"");
			return res;
		}
		res.setResult(walletResult);
		return res;
	}

	public static WalletResponse addErrorObjectsToResponse(WalletResponse res, String str_error_msg, int erorCode, String statusCode){
		if(res == null){
			res =   setDefaultWalletResponseData(null);
		}
		io.swagger.model.Error error = new io.swagger.model.Error();
		error.setErrorCode(erorCode+"");
		error.setErrorDesc(str_error_msg);
		res.setError(error);
		res.setStatusCode(statusCode);
		//res.setResult(null);
		return res;
		
	}
	public static WalletsResponse addErrorObjectsToWalletsResponse(WalletsResponse res, String str_error_msg, int erorCode, String statusCode){
		if(res == null){
			res = setDefaultWalletsResponseData(null);
		}
		io.swagger.model.Error error = new io.swagger.model.Error();
		error.setErrorCode(erorCode+"");
		error.setErrorDesc(str_error_msg);
		res.setError(error);
		res.setStatusCode(statusCode);
		return res;
		
	}
	
	public static WalletResponse setDefaultWalletResponseData(WalletResponse walletResponse1){
		if(walletResponse1 == null){
			walletResponse1 = new WalletResponse();
		}
    	walletResponse1.setUserAgent("1-800-Flowers, Inc./api Enterprise Services");
    	walletResponse1.setVersion("1.1.1");
    	walletResponse1.setStatusCode("200");
    	walletResponse1.setServiceTime("Yet To calculate");
    	return walletResponse1;
	}
	public static WalletsResponse setDefaultWalletsResponseData(WalletsResponse walletResponse){
		if(walletResponse == null){
			walletResponse = new WalletsResponse();
		}		
		walletResponse.setUserAgent("1-800-Flowers, Inc./api Enterprise Services");
		walletResponse.setVersion("1.1.1");
		walletResponse.setStatusCode("200");
		walletResponse.setServiceTime("Yet To calculate");
    	return walletResponse;
	}
	public static boolean isCryptoReArrangeRequired(String str_sourcid){
		boolean isRequired = false;
		String str_valid_source_ids = DataLoadPropertyUtil.getFuctionalParameterValue("VALID_SOURCE_IDS","");
		String[] arr_str_source_ids = str_valid_source_ids.split(",");
		if(arr_str_source_ids!= null && arr_str_source_ids.length > 0){
			for (int isrc = 0;isrc<arr_str_source_ids.length;isrc++){
				String str_src = arr_str_source_ids[isrc];
				if(str_src != null && str_src.equalsIgnoreCase(str_sourcid)){
					isRequired = true;
					break;
				}
			}
		}
		
		
		return isRequired;			
		
	}
	public static boolean isCryptoActive(){
		boolean isRequired = false;
		String str_crypto_active = DataLoadPropertyUtil.getFuctionalParameterValue("CC_CRYPTO_ACTIVE","");
		if(str_crypto_active!= null && str_crypto_active.equalsIgnoreCase("TRUE")){
			return isRequired = true;
		}
		
		return isRequired;			
		
	}
	public static String getMobileEncryptedCCFromWallet(String str_request_card, String str_sourc_id){
		String str_mobile_encrypted_card= str_request_card;
		String str_enckey = ""; 
		String flws_key = "";
		String mobile_key = "";
		m_log.info("getMobileEncryptedCCFromWallet()\t:"+isCryptoActive());
		if(str_request_card==null ||str_request_card.trim().length() <=0){
			return str_request_card;
		}
		m_log.info("isCryptoActive()\t:"+isCryptoActive());
		m_log.info("isCryptoReArrangeRequired(str_sourc_id)()\t:"+isCryptoReArrangeRequired(str_sourc_id));
		if(isCryptoActive() && isCryptoReArrangeRequired(str_sourc_id)){
			
			flws_key = DataLoadPropertyUtil.getFuctionalParameterValue("MOBILE_CC_KEY","");
			mobile_key= DataLoadPropertyUtil.getFuctionalParameterValue(str_sourc_id.toUpperCase()+"_CC_KEY","");
			m_log.info("key----------\t:"+mobile_key);
			m_log.info("flws----------\t:"+flws_key);
			String str_decrypted_card= ByteShiftExample.decryptCard(flws_key, str_request_card);
			m_log.info("decrypted card----------\t:"+str_decrypted_card);
			str_mobile_encrypted_card = ByteShiftExample.encryptCard(mobile_key, str_decrypted_card);
			m_log.info("str mobile encrypted card----------\t:"+str_mobile_encrypted_card);
		}
		return str_mobile_encrypted_card;
	}
	public static String getFlwsEncryptedCCFromMobileRequest(String str_request_card, String str_sourc_id){
		String str_flws_encrypted_card= str_request_card;
		String str_enckey = ""; 
		String flws_key = "";
		String mobile_key = "";
		if(str_request_card==null ||str_request_card.trim().length() <=0){
			return str_request_card;
		}
		
		if(isCryptoActive() && isCryptoReArrangeRequired(str_sourc_id)){
			
			flws_key = DataLoadPropertyUtil.getFuctionalParameterValue("MOBILE_CC_KEY","");
			mobile_key= DataLoadPropertyUtil.getFuctionalParameterValue(str_sourc_id.toUpperCase()+"_CC_KEY","");
			m_log.info("key----------\t:"+mobile_key);
			m_log.info("flws----------\t:"+flws_key);
			String str_decrypted_card= ByteShiftExample.decryptCard(mobile_key, str_request_card);
			str_flws_encrypted_card = ByteShiftExample.encryptCard(flws_key, str_decrypted_card);
			m_log.info("decrypted card----------\t:"+str_decrypted_card);
			m_log.info("str flws encrypted card----------\t:"+str_flws_encrypted_card);
		}
		return str_flws_encrypted_card;
	}
	
	public static void getErrorStackTrace(Exception e){
		String str_error_message = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString(); // stack trace as a string
		m_log.error(sStackTrace);
		
	}
	
}
