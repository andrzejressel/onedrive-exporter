package com.andrzejressel.onedrivecollector.onedrive

import com.andrzejressel.onedrivecollector.onedrive.model.Drive
import com.andrzejressel.onedrivecollector.onedrive.model.MicrosoftUser
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RegisterRestClient
interface OneDriveRestClientService {

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/me/drive")
    @GET
    fun drive(
        @HeaderParam("Authorization") accessToken: String
    ): Drive

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/me/drive")
    @GET
    fun driveString(
        @HeaderParam("Authorization") accessToken: String
    ): String

    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    fun getAccount(
        @HeaderParam("Authorization") accessToken: String
    ): MicrosoftUser

}