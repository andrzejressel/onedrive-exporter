package com.andrzejressel.onedrivecollector.repository

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
open class Account {
    @Id
    @Column(columnDefinition="VARCHAR")
    open lateinit var id: String
    @Column(columnDefinition="VARCHAR")
    open lateinit var name: String
    @Column(columnDefinition="VARCHAR")
    open lateinit var accessToken: String
    @Column(columnDefinition="VARCHAR")
    open lateinit var refreshToken: String
}