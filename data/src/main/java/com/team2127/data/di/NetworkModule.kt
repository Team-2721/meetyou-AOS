package com.team2127.data.di

import android.util.Log
import com.orhanobut.logger.BuildConfig
import com.squareup.moshi.Moshi
import com.team2127.data.intercepter.AddInterceptor
import com.team2127.data.intercepter.MemoryInterceptor
import com.team2127.data.intercepter.isJsonArray
import com.team2127.data.intercepter.isJsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
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
object NetworkModule {

    @NormaNetworkObject
    @Singleton
    @Provides
    fun provideNormalOkHttpClient(
        memoryInterceptor: MemoryInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() -> {
                    Log.d(
                        "jomi",
                        "JSONObject : " + JSONObject(message).toString(4)
                    )
                }

                message.isJsonArray() -> Log.d(
                    "jomi",
                    "JsonArray : " + JSONArray(message).toString(4)
                )

                else -> try {
                    Log.d("jomi", JSONObject(message).toString(4))
                } catch (e: Exception) {
                    Log.d("jomi", message)
                }
            }
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(memoryInterceptor)
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
        addInterceptor: AddInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() -> Log.d(
                    "jomi",
                    "JSONObject : " + JSONObject(message).toString(4)
                )

                message.isJsonArray() -> Log.d(
                    "jomi",
                    "JsonArray : " + JSONObject(message).toString(4)
                )

                else -> try {
                    Log.d("jomi", JSONObject(message).toString(4))
                } catch (e: Exception) {
                    Log.d("jomi", message)
                }
            }
        }


        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(addInterceptor)
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
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}
