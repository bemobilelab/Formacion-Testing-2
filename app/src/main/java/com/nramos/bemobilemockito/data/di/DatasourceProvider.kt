package com.nramos.bemobilemockito.data.di

import com.nramos.bemobilemockito.data.datasource.NetDatasource
import com.nramos.bemobilemockito.data.service.DataService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatasourceProvider {
    @Provides
    @Singleton
    fun provideNetDatasource(
        service: DataService
    ): NetDatasource = NetDatasource(dataService = service)
}