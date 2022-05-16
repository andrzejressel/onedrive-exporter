package com.andrzejressel.onedrivecollector.onedrive

import com.andrzejressel.onedrivecollector.repository.Account
import com.andrzejressel.onedrivecollector.repository.AccountRepository
import com.andrzejressel.onedrivecollector.resource.AlertType
import com.andrzejressel.onedrivecollector.settings.OneDriveSettings
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.slf4j.LoggerFactory
import java.net.URI
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.core.UriBuilder
import io.quarkus.scheduler.Scheduled

@ApplicationScoped
class OneDriveService(
    private val repository: AccountRepository,
    private val oneDriveSettings: OneDriveSettings,
    @RestClient
    private val oneDriveClient: OneDriveRestClientService,
    @RestClient
    private val microsoftClient: MicrosoftRestClientService
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val scope = "User.Read,offline_access,Files.Read"

    private val redirectUri: URI
        get() = UriBuilder
            .fromUri(URI.create(oneDriveSettings.redirect))
            .path("onedrive")
            .build()

    val authorizeUri: URI
        get() = UriBuilder
            .fromUri("https://login.live.com/oauth20_authorize.srf")
            .queryParam("response_type", "code")
            .queryParam("client_id", oneDriveSettings.clientId)
            .queryParam("scope", scope)
            .queryParam("redirect_uri", redirectUri)
            .build()


    @Transactional
    fun handleSignIn(code: String): AlertType {

        val token =
            microsoftClient.getToken(
                clientId = oneDriveSettings.clientId,
                redirectUri = redirectUri.toString(),
                clientSecret = oneDriveSettings.clientSecret,
                code = code,
                scope = scope,
                grantType = "authorization_code"
            )

        val authBeader = "Bearer ${token.accessToken}"
        val user = oneDriveClient.getAccount(authBeader)
        val existingAccount = repository.findById(user.id)

        return if (existingAccount == null) {
            val account = Account()
            account.id = user.id
            account.name = user.userPrincipalName
            account.accessToken = token.accessToken
            account.refreshToken = token.refreshToken
            repository.persist(account)
            AlertType.SUCCESS
        } else {
            AlertType.ALREADY_EXISTS
        }
    }

    @Suppress("unused")
    @Transactional
    @Scheduled(every = "1m")
    protected fun refreshTokens() {

        logger.info("Refreshing tokens")

        for (account in repository.listAll()) {
            logger.info("Refreshing token for account: {}", account.name)
            try {
                val token = microsoftClient.refreshToken(
                    clientId = oneDriveSettings.clientId,
                    redirectUri = redirectUri.toString(),
                    clientSecret = oneDriveSettings.clientSecret,
                    scope = scope,
                    grantType = "refresh_token",
                    refreshToken = account.refreshToken
                )
                account.accessToken = token.accessToken
                account.refreshToken = token.refreshToken
                repository.persist(account)
                logger.info("Refreshing token for account {} ended successfully", account.name)
            } catch (e: Exception) {
                logger.error("Refreshing token for account {} failed", account.name, e)
            }
        }

        logger.info("Refreshing tokens ended")

    }

    @Transactional
    fun deleteAccount(accountId: String) {
        repository.deleteById(accountId)
    }

}