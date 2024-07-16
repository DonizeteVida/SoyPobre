package com.money.soypobre.di

import com.money.soypobre.data.repository.BudgetEntryRepositoryImpl
import com.money.soypobre.data.repository.BudgetRepositoryImpl
import com.money.soypobre.domain.repository.BudgetEntryRepository
import com.money.soypobre.domain.repository.BudgetRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindsModule {
    @Binds
    fun bindsBudgetRepositoryImpl(impl: BudgetRepositoryImpl): BudgetRepository

    @Binds
    fun bindsBudgetEntryRepositoryImpl(impl: BudgetEntryRepositoryImpl): BudgetEntryRepository
}