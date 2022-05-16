package com.andrzejressel.onedrivecollector.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AccountRepository: PanacheRepositoryBase<Account, String>