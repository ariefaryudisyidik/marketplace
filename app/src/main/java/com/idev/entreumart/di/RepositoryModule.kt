package com.idev.entreumart.di

import android.content.Context
import com.idev.entreumart.data.remote.MarketApi
import com.idev.entreumart.data.repository.AuthRepository
import com.idev.entreumart.data.repository.MarketRepository
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