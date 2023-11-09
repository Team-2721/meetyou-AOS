package com.team2127.data.di

import android.content.Context
import com.team2127.data.intercepter.AddInterceptor
import com.team2127.data.intercepter.MemoryInterceptor
import com.team2127.data.source.CookieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CookieDataStoreModule {

    @Provides
    @Singleton
    fun provideCookieDataSource(@ApplicationContext context: Context): CookieDataSource =
        CookieDataSource(context)

    @Provides
    @Singleton
    fun provideMemoryInterceptor(dataStore: CookieDataSource): MemoryInterceptor =
        MemoryInterceptor(dataStore)

    @Provides
    @Singleton
    fun provideAddInterceptor(dataStore: CookieDataSource): AddInterceptor =
        AddInterceptor(dataStore)
}
