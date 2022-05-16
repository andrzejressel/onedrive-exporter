package com.andrzejressel.onedrivecollector.onedrive.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MicrosoftToken(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)