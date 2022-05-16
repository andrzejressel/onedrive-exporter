package com.andrzejressel.onedrivecollector.onedrive.model

data class DriveQuota(
    val deleted: Long,
    val fileCount: Long,
    val remaining: Long,
    val total: Long,
    val used: Long
)