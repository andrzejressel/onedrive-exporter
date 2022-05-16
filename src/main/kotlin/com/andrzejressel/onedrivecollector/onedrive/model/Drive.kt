package com.andrzejressel.onedrivecollector.onedrive.model

data class Drive(
    val driveType: String,
    val id: String,
    val quota: DriveQuota
)
