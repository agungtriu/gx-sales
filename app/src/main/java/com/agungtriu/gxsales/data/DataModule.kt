package com.agungtriu.gxsales.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindAuthorizationRepository(authorizationRepositoryImp: AuthorizationRepositoryImp): AuthorizationRepository

    @Singleton
    @Binds
    abstract fun bindDashboardRepository(dashboardRepositoryImp: DashboardRepositoryImp): DashboardRepository

    @Singleton
    @Binds
    abstract fun bindLeadsRepository(leadsRepositoryImp: LeadsRepositoryImp): LeadsRepository

}