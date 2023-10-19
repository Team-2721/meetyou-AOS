package com.team2127.data.di

import android.os.Build
import com.squareup.moshi.Moshi
import com.team2127.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

private const val BASE_URL = "http://meetyou-backend-prod.ap-northeast-2.elasticbeanstalk.com/"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormaNetworkObject

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

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}
