package com.example.flamemathnew.di

import android.content.Context
import androidx.room.Room
import com.example.flamemathnew.local.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideHistoryDatabase(@ApplicationContext context : Context) : HistoryDatabase =
        Room.databaseBuilder(context, HistoryDatabase::class.java, "history-db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideLexemeDao(database: HistoryDatabase) = database.lexemeDao()

}