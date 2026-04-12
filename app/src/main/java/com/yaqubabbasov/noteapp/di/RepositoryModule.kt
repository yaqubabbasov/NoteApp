package com.yaqubabbasov.noteapp.di

import com.yaqubabbasov.noteapp.data.domain.Repository
import com.yaqubabbasov.noteapp.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

}