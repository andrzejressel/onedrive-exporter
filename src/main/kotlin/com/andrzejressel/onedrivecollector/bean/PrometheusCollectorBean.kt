package com.andrzejressel.onedrivecollector.bean

import com.andrzejressel.onedrivecollector.onedrive.OneDriveRestClientService
import com.andrzejressel.onedrivecollector.onedrive.model.Drive
import com.andrzejressel.onedrivecollector.repository.Account
import com.andrzejressel.onedrivecollector.repository.AccountRepository
import io.prometheus.client.Collector
import io.prometheus.client.GaugeMetricFamily
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.slf4j.LoggerFactory
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class PrometheusCollectorBean(
    private val repository: AccountRepository,
    @RestClient
    private val oneDriveClient: OneDriveRestClientService
): Collector() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun collect(): List<MetricFamilySamples> {
        logger.info("Running collection")
        val accounts = repository.listAll()

        val accountDrives = accounts.associateWith { account ->
            val token = "Bearer ${account.accessToken}"
            val driveInfo = oneDriveClient.drive(token)
            driveInfo
        }

        return listOf(
            getUsedSpaceMetric(accountDrives),
            getFreeSpaceMetric(accountDrives),
            getTotalSpaceMetric(accountDrives),
            getTrashSpaceMetric(accountDrives)
        )
    }


    private fun getUsedSpaceMetric(accounts: Map<Account, Drive>): MetricFamilySamples {
        val metricFamily = GaugeMetricFamily(
            "onedrive_space_used_bytes",
            "Used space on OneDrive drive (in bytes)",
            listOf("account")
        )

        for ((account, drive) in accounts) {

            metricFamily.addMetric(
                listOf(account.name),
                drive.quota.used.toDouble()
            )
        }
        return metricFamily
    }

    private fun getTotalSpaceMetric(accounts: Map<Account, Drive>): MetricFamilySamples {
        val metricFamily = GaugeMetricFamily(
            "onedrive_space_total_bytes",
            "Total space on OneDrive drive (in bytes)",
            listOf("account")
        )

        for ((account, drive) in accounts) {
            metricFamily.addMetric(
                listOf(account.name),
                drive.quota.total.toDouble()
            )
        }
        return metricFamily
    }

    private fun getFreeSpaceMetric(accounts: Map<Account, Drive>): MetricFamilySamples {
        val metricFamily = GaugeMetricFamily(
            "onedrive_space_remaining_bytes",
            "Remaining space on OneDrive drive (in bytes)",
            listOf("account")
        )

        for ((account, drive) in accounts) {
            metricFamily.addMetric(
                listOf(account.name),
                drive.quota.remaining.toDouble()
            )
        }
        return metricFamily
    }

    private fun getTrashSpaceMetric(accounts: Map<Account, Drive>): MetricFamilySamples {
        val metricFamily = GaugeMetricFamily(
            "onedrive_recycle_bin_used_bytes",
            "Used space by recycle bin on OneDrive drive (in bytes)",
            listOf("account")
        )

        for ((account, drive) in accounts) {
            metricFamily.addMetric(
                listOf(account.name),
                drive.quota.deleted.toDouble()
            )
        }
        return metricFamily
    }

}