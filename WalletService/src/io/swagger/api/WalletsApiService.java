package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.WalletRequest;
import io.swagger.model.WalletResponse;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-06-06T22:42:42.640Z")
public abstract class WalletsApiService {
    public abstract Response walletsExternalReferenceIDCardsGet(String externalReferenceID,SecurityContext securityContext) throws NotFoundException;
    public abstract Response walletsExternalReferenceIDCardsPost(WalletRequest wallet,String externalReferenceID,SecurityContext securityContext) throws NotFoundException;
    public abstract Response walletsExternalReferenceIDCardsWalletIdDelete(String externalReferenceID,String walletId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response walletsExternalReferenceIDCardsWalletIdGet(String externalReferenceID,String walletId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response walletsExternalReferenceIDCardsWalletIdPut(WalletRequest wallet,String externalReferenceID,String walletId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response walletsExternalReferenceIDSubscardGet(String externalReferenceID,SecurityContext securityContext) throws NotFoundException;
}
