package com.andrzejressel.onedrivecollector.resource

import com.andrzejressel.onedrivecollector.onedrive.OneDriveService
import com.andrzejressel.onedrivecollector.usecase.GetAllAccountsUseCase
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam

@ApplicationScoped
@Path("")
class IndexResource(
    val index: Template,
    val oneDriveService: OneDriveService,
    val getAllAccounts: GetAllAccountsUseCase
) {

    @GET
    fun index(
        @QueryParam("alertType") alertType: AlertType?
    ): TemplateInstance {
        val authorizeUri = oneDriveService.authorizeUri
        val accounts = getAllAccounts()
        return index.data("authorizeUri", authorizeUri, "accounts", accounts, "alertType", alertType)
    }

}