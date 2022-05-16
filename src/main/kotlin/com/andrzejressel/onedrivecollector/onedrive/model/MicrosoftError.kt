package com.andrzejressel.onedrivecollector.onedrive.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MicrosoftError(
    val error: String,
    @JsonProperty("error_description")
    val errorDescription: String
)