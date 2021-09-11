package com.example.flamemathnew.di

import com.example.flamemathnew.backend.my.LocalDataProvider
import com.example.flamemathnew.backend.my.RoomDataProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalModule {
    @Binds
    @Singleton
    fun bindLocalDataProvider(roomDataProvider: RoomDataProvider)
            : LocalDataProvider
}
