package com.example.flamemathnew.di

import com.example.flamemathnew.repository.HistoryRepository
import com.example.flamemathnew.repository.HistoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindHistoryRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository
}