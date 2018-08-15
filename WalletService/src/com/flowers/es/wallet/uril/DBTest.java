package com.flowers.es.wallet.uril;

import java.sql.*;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.*;





public class DBTest {
	//static org.apache.log4j.Logger  m_log = org.apache.log4j.Logger.getLogger(DBTest.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			callUpdateUSP_INSERT_UPDATE_WalletID();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Connection getSQLDBConnection(){
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		
		SQLServerDataSource ds = new SQLServerDataSource();
		
		/*ds.setServerName("10.201.43.101");
		ds.setPortNumber(1433); 
		ds.setDatabaseName("MicroSvcDB");
		ds.setUser("paymentdev");
		ds.setPassword("paymentdev");*/
		String str_new_url="jdbc:sqlserver://msazdbt01.database.windows.net:1433;database=MicroSvcDB;user=webservMicro@msazdbt01;password=webM1%$12rJKlm@01;encrypt=true;trustServerCertificate=false;hostNameInCertificate=eastus1-a.control.database.windows.net;loginTimeout=30";
		//String str_new_url = "jdbc:sqlserver://msazdbt01.database.windows.net:1433;database=PaymentDB;user=webservuser@msazdbt01;password=web12rJKlm@01;encrypt=true;trustServerCertificate=false;hostNameInCertificate=eastus1-a.control.database.windows.net;loginTimeout=30";
		ds.setURL(str_new_url);
		
		try {			
			con = ds.getConnection();
		} catch (SQLServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	public static String callUpdateUSP_INSERT_UPDATE_WalletID() throws Exception{

		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;		
		int iseq =0;
		long i_walletID = 0;
		
		
		//wallet.get

		con = getSQLDBConnection();
		cstmt = con.prepareCall("{call microsvcdb.wallet.USP_INSERT_UPDATE_WalletID(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		//cstmt.setString(1, str_xml);
		
		
		cstmt.setLong("AccountRefID", Long.parseLong("4444341234"));			
		cstmt.setNull("WalletID", Types.BIGINT);	
		cstmt.setString("CardType", "VI");
		cstmt.setString("PayType", "CP");		
		cstmt.setString("Status", "A");		
		cstmt.setNull("Status", Types.VARCHAR);
		cstmt.setString("CardHolderName", "JOYS HHH");
		cstmt.setString("CardNumber", "123456778809");
		cstmt.setString("LastFour", "1234");
		cstmt.setString("Expdate", "12/23");
		cstmt.setString("User", "JOYS");
		cstmt.registerOutParameter("WalletID_New",  Types.BIGINT);		
		cstmt.registerOutParameter("ERRCD", Types.INTEGER);
		cstmt.registerOutParameter("ERRCDDescr", Types.VARCHAR);
		System.out.println("Starting USP_INSERT_UPDATE_WalletID");	

		cstmt.execute();
		System.out.println(" USP_INSERT_UPDATE_WalletID completd");

		if(cstmt.getObject("ERRCD")!= null){
			iseq = (int) cstmt.getObject("ERRCD");
			System.out.println("erroce code  for USP_INSERT_UPDATE_WalletID "+iseq);	
		}
		String str_err_mesage = "";
		if(cstmt.getObject("ERRCDDescr")!= null){
			 str_err_mesage= (String) cstmt.getObject("ERRCDDescr");
			System.out.println("errr message  for USP_INSERT_UPDATE_WalletID "+str_err_mesage);	
		}
		if(cstmt.getObject("WalletID_New")!= null){
			i_walletID = (long) cstmt.getObject("WalletID_New");
			System.out.println("Completd  USP_INSERT_UPDATE_WalletID and Wallet ID: "+i_walletID);	
		}
		

		return null;
	}

	public static void callUSP_GetWalletInfo(String str_externalReferenceID,String str_walletID) throws Exception{

		
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;	
		long long_externalReferenceID=0;
		long long_walletID =Long.parseLong(str_walletID);

		
		if(str_externalReferenceID != null && str_externalReferenceID.trim().length() > 0){
			str_externalReferenceID = str_externalReferenceID.trim();
			long_externalReferenceID = Long.parseLong(str_externalReferenceID);
		}
		System.out.println("Calling   USP_GetWalletInfo for:"+str_externalReferenceID);
		System.out.println("Calling   USP_GetWalletInfo for walletID:"+long_walletID);
		int  errorCode=0;

		con = getSQLDBConnection();
		System.out.println("Connection for   USP_GetWalletInfo completed");
		cstmt = con.prepareCall("{call microsvcdb.Wallet.USP_GetWalletInfo(?,?,?,?)}");
		cstmt.setLong(1, long_externalReferenceID);
		//cstmt.setNull(2, Types.BIGINT);//(2, long_walletID);
		cstmt.setLong(2, long_walletID);//(1, long_walletID);
		cstmt.registerOutParameter(3, Types.INTEGER);
		cstmt.registerOutParameter(4, Types.VARCHAR);
		System.out.println("Executing USP_GetWalletInfo");

		cstmt.execute();
		System.out.println("Executing USP_GetWalletInfo Completed");		
		rs =  cstmt.getResultSet();
		if(rs !=null){
			while(rs.next()){					
				System.out.println("In loop Wallet ID"+rs.getString("CardHolderName"));
			}
		}
		else{
			System.out.println(" Results set is empty...........");
		}
		if(cstmt.getObject(3)!= null){
			errorCode = (int) cstmt.getObject(3);
			System.out.println("erroce code  for USP_GetWalletInfo "+errorCode);	
		}
		if(errorCode==0){


			
		}

		if(cstmt.getObject(4)!= null){
			String str_error_msg = (String) cstmt.getObject(4);
			System.out.println("erroce message  for USP_GetWalletInfo "+str_error_msg);	
		}

		return ;
	}

	

}
