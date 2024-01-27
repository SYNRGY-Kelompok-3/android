package com.synrgy.travelid.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.febi.myapplication.ArticleViewModel
import com.synrgy.travelid.data.remote.service.APIService
import com.synrgy.travelid.data.repository.RemoteRepo
import com.synrgy.travelid.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

private val generalModule =
    module {
        single { ChuckerInterceptor(get()) }
        single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
        single { provideOkhttpClient(get(), get()) }
        single { "https://api-artikel.fly.dev/" }
        single { GsonConverterFactory.create() }
        single { provideRetrofit(get(), get(), get()) }
        single { provideOkhttpClient(get(), get()) }
        single { provideAPIService(get()) }
        single <CoroutineContext>{ Dispatchers.IO}

        //repository
        single<ArticleRepository> { RemoteRepo(get()) }

    }
private val viewModelModule =
    module{
        viewModel { ArticleViewModel(get()) }
    }

val appModules = listOf(generalModule, viewModelModule)

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    baseUrl: String,
    gsonConverterFactory: GsonConverterFactory,
): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

private fun provideOkhttpClient(
    chuckerInterceptor: ChuckerInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor,
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(chuckerInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

private fun provideAPIService(retrofit: Retrofit): APIService {
    return retrofit.create(APIService::class.java)
}