package com.andrzejressel.onedrivecollector.resource

import com.andrzejressel.onedrivecollector.onedrive.OneDriveService
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.DELETE
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo

@ApplicationScoped
@Path("/onedrive")
class OneDriveResource(
    val oneDriveService: OneDriveService,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @GET
    fun handleCallback(
        @QueryParam("code")
        code: String,
        @Context
        uriInfo: UriInfo
    ): Response {
        val result = try {
            oneDriveService.handleSignIn(code)
        } catch (e: Exception) {
            logger.error("Exception when handling onedrive callback", e)
            AlertType.ERROR
        }

        val uri = UriBuilder.fromUri(uriInfo.baseUri)
            .queryParam("alertType", result)
            .build()

        return Response.seeOther(uri).entity("Go to <a href=\"$uri\">$uri</a>").build()
    }

    @POST
    @Path("/delete")
    fun deleteAccount(
        @FormParam("accountId")
        accountId: String,
        @Context
        uriInfo: UriInfo
    ): Response {
        val uri = UriBuilder.fromUri(uriInfo.baseUri)
            .queryParam("alertType", AlertType.REMOVED)
            .build()

        oneDriveService.deleteAccount(accountId)

        return Response.seeOther(uri).entity("Go to <a href=\"$uri\">$uri</a>").build()
    }

}