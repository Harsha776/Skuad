package com.harsha.di

import com.harsha.data.RepoService
import com.harsha.data.repositoryImpl.HomeRepositoryImpl
import com.harsha.data.repository.HomeRepository
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {

    @Provides
    fun provideHomeRepo(repoService: RepoService): HomeRepository = HomeRepositoryImpl(repoService)

}