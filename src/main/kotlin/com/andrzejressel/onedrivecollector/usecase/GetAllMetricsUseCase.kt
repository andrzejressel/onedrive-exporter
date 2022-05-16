package com.andrzejressel.onedrivecollector.usecase

import com.andrzejressel.onedrivecollector.onedrive.OneDriveRestClientService
import com.andrzejressel.onedrivecollector.repository.AccountRepository
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GetAllMetricsUseCase(
    val repository: AccountRepository,
    @RestClient
    val oneDriveClient: OneDriveRestClientService
) {
}