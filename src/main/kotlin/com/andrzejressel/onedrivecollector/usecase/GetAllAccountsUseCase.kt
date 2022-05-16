package com.andrzejressel.onedrivecollector.usecase

import com.andrzejressel.onedrivecollector.repository.Account
import com.andrzejressel.onedrivecollector.repository.AccountRepository
import com.andrzejressel.onedrivecollector.onedrive.OneDriveRestClientService
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.rest.client.inject.RestClient
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GetAllAccountsUseCase(
    val repository: AccountRepository
) {

    operator fun invoke(): List<Account> {
        return repository.listAll()
    }

}
