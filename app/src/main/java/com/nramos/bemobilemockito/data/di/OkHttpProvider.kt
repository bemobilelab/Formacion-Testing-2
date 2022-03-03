package com.nramos.bemobilemockito.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpProvider {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient =  OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

}