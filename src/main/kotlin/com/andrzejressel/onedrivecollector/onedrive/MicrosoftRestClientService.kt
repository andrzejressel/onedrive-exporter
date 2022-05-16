package com.andrzejressel.onedrivecollector.onedrive

import com.andrzejressel.onedrivecollector.onedrive.model.MicrosoftToken
import com.andrzejressel.onedrivecollector.onedrive.model.MicrosoftUser
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RegisterRestClient
@RegisterProvider(MicrosoftExceptionMapper::class)
interface MicrosoftRestClientService {

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/common/oauth2/v2.0/token")
    @POST
    fun getToken(
        @FormParam("client_id") clientId: String,
        @FormParam("redirect_uri") redirectUri: String,
        @FormParam("client_secret") clientSecret: String,
        @FormParam("code") code: String,
        @FormParam("scope") scope: String,
        @FormParam("grant_type") grantType: String
    ): MicrosoftToken

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/common/oauth2/v2.0/token")
    @POST
    fun refreshToken(
        @FormParam("client_id") clientId: String,
        @FormParam("redirect_uri") redirectUri: String,
        @FormParam("client_secret") clientSecret: String,
        @FormParam("refresh_token") refreshToken: String,
        @FormParam("scope") scope: String,
        @FormParam("grant_type") grantType: String
    ): MicrosoftToken

}