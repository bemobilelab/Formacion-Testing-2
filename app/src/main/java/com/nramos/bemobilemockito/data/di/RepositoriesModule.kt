package com.nramos.bemobilemockito.data.di

import com.nramos.bemobilemockito.data.repo.DataRepoImpl
import com.nramos.bemobilemockito.domain.repo.DataRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    internal abstract fun bindDataRepo(impl: DataRepoImpl): DataRepo

}