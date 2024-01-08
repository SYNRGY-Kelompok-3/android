package com.febi.projek

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.febi.projek.data.local.DataStoreManager
import com.febi.projek.data.remote.service.APIService
import com.febi.projek.data.repository.RemoteRepository
import com.febi.projek.domain.repo.AuthRepository
import com.febi.projek.domain.repo.TokenRepository
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
        single { "https://easy-school-uniform-ant.cyclic.app/api/v1/" }
        single { GsonConverterFactory.create() }
        single { provideRetrofit(get(), get(), get()) }
        single { provideOkhttpClient(get(), get()) }
        single { provideAPIService(get()) }
        single { DataStoreManager(get()) }
        single <CoroutineContext>{ Dispatchers.IO}

        //repository
        single<AuthRepository>  { RemoteRepository (get(), get()) }
        single<TokenRepository> { RemoteRepository (get(), get()) }

        //usecase
        single { LupaPasswordUseCase(get(),get()) }
        single { InsertTokenUseCase(get()) }
    }
private val viewModelModule =
    module{
        viewModel { LupaPasswordViewModel(get()) }
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