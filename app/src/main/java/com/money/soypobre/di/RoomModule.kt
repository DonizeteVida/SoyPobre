package com.money.soypobre.di

import android.content.Context
import androidx.room.Room
import com.money.soypobre.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "soy_pobre.sqlite"
    ).build()

    @Provides
    fun providesBudgetDao(
        database: AppDatabase
    ) = database.budgetDao
}