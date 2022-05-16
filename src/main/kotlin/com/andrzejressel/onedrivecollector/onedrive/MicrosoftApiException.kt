package com.andrzejressel.onedrivecollector.onedrive

import com.andrzejressel.onedrivecollector.onedrive.model.MicrosoftError

class MicrosoftApiException(
    val error: MicrosoftError
): RuntimeException(error.toString())