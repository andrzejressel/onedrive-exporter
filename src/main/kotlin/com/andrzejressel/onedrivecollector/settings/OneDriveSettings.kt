package com.andrzejressel.onedrivecollector.settings

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
data class OneDriveSettings(
    @ConfigProperty(name = "onedrive.client_id")
    val clientId: String,
    @ConfigProperty(name = "onedrive.client_secret")
    val clientSecret: String,
    @ConfigProperty(name = "onedrive.redirect")
    val redirect: String
)