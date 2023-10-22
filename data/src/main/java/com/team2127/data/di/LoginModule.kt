package com.team2127.data.di

import com.team2127.data.repository.LoginRepositoryImpl
import com.team2127.data.service.LoginService
import com.team2127.data.source.LoginDataSource
import com.team2127.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class LoginModule {
    @ActivityRetainedScoped
    @Provides
    fun provideLoginRepository(dataSource: LoginDataSource): LoginRepository =
        LoginRepositoryImpl(dataSource)

    @ActivityRetainedScoped
    @Provides
    fun provideLoginDataSource(service: LoginService): LoginDataSource =
        LoginDataSource(service)

    @ActivityRetainedScoped
    @Provides
    fun provideLoginService(@SessionNetworkObject retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)
}
