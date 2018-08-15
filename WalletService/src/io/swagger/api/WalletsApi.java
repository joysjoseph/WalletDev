package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.WalletsApiService;
import io.swagger.api.factories.WalletsApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.WalletRequest;
import io.swagger.model.WalletResponse;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.ZonedDateTime;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.flowers.es.wallet.uril.DBUtil;
import com.flowers.es.wallet.uril.WalletUtil;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/wallets")

@JsonInclude(JsonInclude.Include.NON_NULL)
@io.swagger.annotations.Api(description = "the wallets API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-06-06T22:42:42.640Z")
public class WalletsApi  {
	   @JsonInclude(JsonInclude.Include.NON_NULL)
   private final WalletsApiService delegate;
   static org.apache.log4j.Logger  m_log = org.apache.log4j.Logger.getLogger(WalletsApi.class);
   public static void initIPgloging(){
		 com.flowers.es.wallet.uril.IPGBatchLogUtil.getCurrent(WalletsApi.class);
		 com.flowers.es.wallet.uril.IPGBatchLogUtil.initLogger("");
			m_log.info(" Starting----  : " );
		}
   public WalletsApi(@Context ServletConfig servletContext) {
      WalletsApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("WalletsApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (WalletsApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = WalletsApiServiceFactory.getWalletsApi();
      }

      this.delegate = delegate;
   }

    @GET
    @Path("/{externalReferenceID}/cards/")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Gets `Wallet` objects.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsGet(@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID
    		
,@Context SecurityContext securityContext)
    throws NotFoundException {
        //return Response.ok().entity("magic!").build();
    	//@JsonInclude(JsonInclude.Include.NON_NULL)
    	initIPgloging();
    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	WalletsResponse res = new WalletsResponse();
    	
    	//Wallets1 result=null;
		try {
			res = DBUtil.callUSP_GetAccountRefInfo(externalReferenceID,"");
		} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletsResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToWalletsResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}    	    	
		//res.setResult(result );
		m_log.info("OUT:\n"+res.toString());
		 second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build();
    	//return Response.ok().entity(res).build();
	
	}
    @GET
    @Path("/{externalReferenceID}/cards/sourceID/{sourceID}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Gets `Wallet` objects.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsGet(@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID,
    		@ApiParam(value = "source id",required=true) @PathParam("sourceID") String sourceID
,@Context SecurityContext securityContext)
    throws NotFoundException {
        //return Response.ok().entity("magic!").build();
    	//@JsonInclude(JsonInclude.Include.NON_NULL)
    	initIPgloging();
    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	WalletsResponse res = new WalletsResponse();
    	
    	//Wallets1 result=null;
		try {
			res = DBUtil.callUSP_GetAccountRefInfo(externalReferenceID,sourceID);
		} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletsResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToWalletsResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}    	    	
		//res.setResult(result );
		m_log.info("OUT:\n"+res.toString());
		 second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build();
    	//return Response.ok().entity(res).build();
	
	}
    @POST
    @Path("/{externalReferenceID}/cards/sourceID/{sourceID}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Add a single 'Wallet' object.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsPost(@ApiParam(value = "add wallet" ,required=true) WalletRequest wallet
,@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID
,@ApiParam(value = "source id",required=true) @PathParam("sourceID") String sourceID
,@Context SecurityContext securityContext)
    throws NotFoundException {

    	long start_time;
    	long end_time;
    	long process_time;
    	long wallet_id =0;
    	WalletResponse res = null;
    	initIPgloging();
    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
		try {
			DBUtil.insertDetails(externalReferenceID);
			//wallet.getWallet().
			if(wallet != null){
				wallet.getWallet().setUser(sourceID);
			}
			res  = DBUtil.callUSP_INSERT_UPDATE_WalletID(externalReferenceID, wallet);
		} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");	 	
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}
		second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status = 200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		}    	
		return Response.status(i_status).entity(res).build();
		//return Response.ok().entity(res).build();
    }
    @POST
    @Path("/{externalReferenceID}/cards")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Add a single 'Wallet' object.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsPost(@ApiParam(value = "add wallet" ,required=true) WalletRequest wallet
,@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID

,@Context SecurityContext securityContext)
    throws NotFoundException {

    	long start_time;
    	long end_time;
    	long process_time;
    	long wallet_id =0;
    	WalletResponse res = null;
    	initIPgloging();
    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
		try {
			DBUtil.insertDetails(externalReferenceID);			//wallet.getWallet().
			
			res  = DBUtil.callUSP_INSERT_UPDATE_WalletID(externalReferenceID, wallet);
		} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");	 	
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}
		second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status = 200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		}    	
		return Response.status(i_status).entity(res).build();
		//return Response.ok().entity(res).build();
    }

    @DELETE
    @Path("/{externalReferenceID}/cards/{walletId}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Delete the wallet with wallet id attached to contact id .", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsWalletIdDelete(@ApiParam(value = "externalRefereneID id",required=true) @PathParam("externalReferenceID") String externalReferenceID
,@ApiParam(value = "wallet id to be deleted",required=true) @PathParam("walletId") String walletId
,@Context SecurityContext securityContext)
    throws NotFoundException {

    	WalletResponse res = null;
    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	initIPgloging();
    	try {
			res = DBUtil.deleteWalletCard(externalReferenceID,walletId);
		} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}
    	second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build(); 
    	//return Response.ok().entity(res).build();
     }
    @GET
    @Path("/{externalReferenceID}/cards/{walletId}/sourceID/{sourceID}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Gets a `Wallet` objects.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsWalletIdGet(@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID
,@ApiParam(value = "wallet id",required=true) @PathParam("walletId") String walletId,
@ApiParam(value = "source id",required=true) @PathParam("sourceID") String sourceID
,@Context SecurityContext securityContext)
    throws NotFoundException {


    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	WalletResponse res = null;
    	initIPgloging();

    	//WalletResult1 result;
    	//Wallets1 result=null;
    	try {
    		res = DBUtil.callUSP_GetWalletInfo(externalReferenceID,walletId,sourceID);
    		//res.s
    		//res.setResult(result );
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}   
    	/*if(res.getStatusCode()!= null && res.getStatusCode().trim().length() >0){
    		int int_status = Integer.parseInt(res.getStatusCode());
    		return Response.status(int_status).entity(res).build();
    	}*/
    	second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build();
    	//return Response.ok().entity(res).build();
    }
    @GET
    @Path("/{externalReferenceID}/cards/{walletId}/")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Gets a `Wallet` objects.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsWalletIdGet(@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID
,@ApiParam(value = "wallet id",required=true) @PathParam("walletId") String walletId
,@Context SecurityContext securityContext)
    throws NotFoundException {


    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	WalletResponse res = null;
    	initIPgloging();

    	//WalletResult1 result;
    	//Wallets1 result=null;
    	try {
    		res = DBUtil.callUSP_GetWalletInfo(externalReferenceID,walletId,"");
    		//res.s
    		//res.setResult(result );
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}   
    	/*if(res.getStatusCode()!= null && res.getStatusCode().trim().length() >0){
    		int int_status = Integer.parseInt(res.getStatusCode());
    		return Response.status(int_status).entity(res).build();
    	}*/
    	second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build();
    	//return Response.ok().entity(res).build();
    }

    @PUT
    @Path("/{externalReferenceID}/cards/{walletId}/")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Update a single 'Wallet' object.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsWalletIdPut(@ApiParam(value = "update wallet" ,required=true) WalletRequest wallet
,@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID
,@ApiParam(value = "wallet id",required=true) @PathParam("walletId") String walletId

,@Context SecurityContext securityContext)
    throws NotFoundException {

    	WalletResponse res = null;
    	initIPgloging();
    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	long wallet_id =0;
    	try {
    		
    		res = DBUtil.callUpdateUSP_INSERT_UPDATE_WalletID(externalReferenceID, wallet);
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}
    	second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build();
    	//return Response.ok().entity(res).build();
    	//return delegate.walletsExternalReferenceIDCardsWalletIdPut(wallet,externalReferenceID,walletId,securityContext);
    }
    @PUT
    @Path("/{externalReferenceID}/cards/{walletId}/sourceID/{sourceID}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Update a single 'Wallet' object.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDCardsWalletIdPut(@ApiParam(value = "update wallet" ,required=true) WalletRequest wallet
,@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID
,@ApiParam(value = "wallet id",required=true) @PathParam("walletId") String walletId
,@ApiParam(value = "source id",required=true) @PathParam("sourceID") String sourceID
,@Context SecurityContext securityContext)
    throws NotFoundException {

    	WalletResponse res = null;
    	initIPgloging();
    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	long wallet_id =0;
    	try {
    		if(wallet!= null){
    			wallet.getWallet().setUser(sourceID);
    		}
    		res = DBUtil.callUpdateUSP_INSERT_UPDATE_WalletID(externalReferenceID, wallet);
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}
    	second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build();
    	//return Response.ok().entity(res).build();
    	//return delegate.walletsExternalReferenceIDCardsWalletIdPut(wallet,externalReferenceID,walletId,securityContext);
    }

    @GET
    @Path("/{externalReferenceID}/subscard/")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Gets `Wallet` object flagged for subscription.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDSubscardGet(@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID
    		
,@Context SecurityContext securityContext)
    throws NotFoundException {


    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	WalletResponse res = null;
    	initIPgloging();

    	//WalletResult1 result;
    	//Wallets1 result=null;
    	try {
    		res = DBUtil.callUSP_GetWalletInfoforSub(externalReferenceID,"");
    		m_log.info(" Starting----  : " );
    		//res.s
    		//res.setResult(result );
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}   
    	/*if(res.getStatusCode()!= null && res.getStatusCode().trim().length() >0){
    		int int_status = Integer.parseInt(res.getStatusCode());
    		return Response.status(int_status).entity(res).build();
    	}*/
    	second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build();
    	//return Response.ok().entity(res).build();
    }
    @GET
    @Path("/{externalReferenceID}/subscard/sourceID/{sourceID}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
	@JsonInclude(JsonInclude.Include.NON_NULL)
    @io.swagger.annotations.ApiOperation(value = "", notes = "Gets `Wallet` object flagged for subscription.", response = WalletResponse.class, tags={ "Wallet APIs", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful response", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 304, message = "Not Modified", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Data", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not found", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 422, message = "Unprocessable Entity", response = WalletResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = WalletResponse.class) })
    public Response walletsExternalReferenceIDSubscardGet(@ApiParam(value = "externalReference ID",required=true) @PathParam("externalReferenceID") String externalReferenceID,
    		@ApiParam(value = "source id",required=true) @PathParam("sourceID") String sourceID
,@Context SecurityContext securityContext)
    throws NotFoundException {


    	ZonedDateTime first = ZonedDateTime.now();
    	ZonedDateTime second = null;
    	WalletResponse res = null;
    	initIPgloging();

    	//WalletResult1 result;
    	//Wallets1 result=null;
    	try {
    		res = DBUtil.callUSP_GetWalletInfoforSub(externalReferenceID,sourceID);
    		m_log.info(" Starting----  : " );
    		//res.s
    		//res.setResult(result );
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		res = DBUtil.setDefaultWalletResponseData(null);
			res.setStatusCode(500+"");		
			res = DBUtil.addErrorObjectsToResponse(res,e.getMessage(),500,"500");			
    		e.printStackTrace();
    		second = ZonedDateTime.now();
    		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
    		res.setServiceTime(str_ServiceTime);
    		return Response.status(500).entity(res).build();
    	}   
    	/*if(res.getStatusCode()!= null && res.getStatusCode().trim().length() >0){
    		int int_status = Integer.parseInt(res.getStatusCode());
    		return Response.status(int_status).entity(res).build();
    	}*/
    	second = ZonedDateTime.now();
		String str_ServiceTime= WalletUtil.getProcessTime(first, second);
		res.setServiceTime(str_ServiceTime);
		String str_status = res.getStatusCode();
		int i_status =200;
		if(str_status!= null && str_status.trim().length() >0){
			i_status = Integer.parseInt(str_status);
		} 
		return Response.status(i_status).entity(res).build();
    	//return Response.ok().entity(res).build();
    }

}
