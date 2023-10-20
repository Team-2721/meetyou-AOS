package com.team2127.data.di

import com.team2127.data.service.MainService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ActivityRetainedComponent::class)
class MainModule {

//    @ActivityRetainedScoped
//    @Provides
//    fun provideMainRepository(dataSource: MainDataSource): MainRepository =
//        MainRepositoryImpl(dataSource)
//
//    @ActivityRetainedScoped
//    @Provides
//    fun provideMainDataSource(service: MainService): MainDataSource =
//        MainDataSource(service)

    @ActivityRetainedScoped
    @Provides
    fun provideMainService(@NormaNetworkObject retrofit: Retrofit): MainService =
        retrofit.create(MainService::class.java)
}
