package com.nramos.bemobilemockito.data.di

import com.nramos.bemobilemockito.data.service.DataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideRatesService(retrofit: Retrofit): DataService = retrofit.create(DataService::class.java)

}
