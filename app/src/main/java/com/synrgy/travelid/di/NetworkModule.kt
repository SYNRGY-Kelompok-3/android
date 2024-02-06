package com.synrgy.travelid.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.synrgy.travelid.data.remote.service.AuthAPIService
import com.synrgy.travelid.data.remote.service.MainAPIService
import com.synrgy.travelid.data.remote.service.SideAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    companion object{
        const val BASE_URL_AUTH = "https://travelid-backend-java-dev.up.railway.app/v1/"
        const val BASE_URL_MAIN = "https://travelid-backend-java-dev.up.railway.app/"
        const val BASE_URL_SIDE = "https://api-artikel.fly.dev/"
        const val RETROFIT_AUTH = "RetrofitAuth"
        const val RETROFIT_MAIN = "RetrofitMain"
        const val RETROFIT_SIDE = "RetrofitSide"
    }

    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        chucker: ChuckerInterceptor,
        logging: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(chucker)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    @Named(RETROFIT_AUTH)
    fun provideRetrofitAuth(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_AUTH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named(RETROFIT_MAIN)
    fun provideRetrofitMain(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_MAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named(RETROFIT_SIDE)
    fun provideRetrofitSide(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_SIDE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthAPI(@Named(RETROFIT_AUTH) retrofit: Retrofit) : AuthAPIService {
        return retrofit.create(AuthAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideMainAPI(@Named(RETROFIT_MAIN) retrofit: Retrofit) : MainAPIService {
        return retrofit.create(MainAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideSideAPI(@Named(RETROFIT_SIDE) retrofit: Retrofit) : SideAPIService {
        return retrofit.create(SideAPIService::class.java)
    }
}