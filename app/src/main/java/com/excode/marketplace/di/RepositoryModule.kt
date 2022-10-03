package com.excode.marketplace.di

import android.content.Context
import com.excode.marketplace.data.remote.MarketApi
import com.excode.marketplace.data.repository.AuthRepository
import com.excode.marketplace.data.repository.MarketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        api: MarketApi
    ): AuthRepository {
        return AuthRepository(context, api)
    }

    @Provides
    @Singleton
    fun provideMarketRepository(
        @ApplicationContext context: Context,
        api: MarketApi
    ): MarketRepository {
        return MarketRepository(context, api)
    }
}