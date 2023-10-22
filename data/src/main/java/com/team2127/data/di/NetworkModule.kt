package com.team2127.data.di

import com.squareup.moshi.Moshi
import com.team2127.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.internal.addHeaderLenient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieManager
import javax.inject.Qualifier
import javax.inject.Singleton

private const val BASE_URL = "http://meetyou-backend-prod.ap-northeast-2.elasticbeanstalk.com/"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormaNetworkObject

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SessionNetworkObject

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    @NormaNetworkObject
    @Singleton
    @Provides
    fun provideNormalOkHttpClient(): OkHttpClient =
        if (BuildConfig.DEBUG){
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }

    @NormaNetworkObject
    @Singleton
    @Provides
    fun provideNormalRetrofit(
        @NormaNetworkObject okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @SessionNetworkObject
    @Singleton
    @Provides
    fun provideSessionOkHttpClient(
        authInterceptor: Interceptor
    ): OkHttpClient =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .cookieJar(JavaNetCookieJar(CookieManager()))
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .cookieJar(JavaNetCookieJar(CookieManager()))
                .build()
        }

    @SessionNetworkObject
    @Singleton
    @Provides
    fun provideAuthRetrofit(
        @SessionNetworkObject okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

//    {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}
