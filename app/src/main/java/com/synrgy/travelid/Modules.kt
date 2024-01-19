package com.synrgy.travelid

//private val generalModule =
//    module {
//        single { ChuckerInterceptor(get()) }
//        single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
//        single { provideOkhttpClient(get(), get()) }
//        single { "https://travelid-backend-java-dev.up.railway.app/v1/" }
//        single { GsonConverterFactory.create() }
//        single { provideRetrofit(get(), get(), get()) }
//        single { provideOkhttpClient(get(), get()) }
//        single { provideAPIService(get()) }
//        single { DataStoreManager(get()) }
//        single <CoroutineContext>{ Dispatchers.IO}
//
//        //repository
//        single<AuthRepository>  { RemoteRepository (get(), get()) }
//        single<TokenRepository> { RemoteRepository (get(), get()) }
//
//        //usecase
//        single { LupaPasswordUseCase(get(),get()) }
//        single { InsertTokenUseCase(get()) }
//    }
//private val viewModelModule =
//    module{
//        viewModel { LupaPasswordViewModel(get()) }
//        viewModel { AturUlangPasswordViewModel(get()) }
//    }
//
//val appModules = listOf(generalModule, viewModelModule)
//
//private fun provideRetrofit(
//    okHttpClient: OkHttpClient,
//    baseUrl: String,
//    gsonConverterFactory: GsonConverterFactory,
//): Retrofit {
//    return Retrofit.Builder()
//        .client(okHttpClient)
//        .baseUrl(baseUrl)
//        .addConverterFactory(gsonConverterFactory)
//        .build()
//}
//
//private fun provideOkhttpClient(
//    chuckerInterceptor: ChuckerInterceptor,
//    httpLoggingInterceptor: HttpLoggingInterceptor,
//): OkHttpClient {
//    return OkHttpClient.Builder()
//        .addInterceptor(chuckerInterceptor)
//        .addInterceptor(httpLoggingInterceptor)
//        .build()
//}
//
//private fun provideAPIService(retrofit: Retrofit): APIService {
//    return retrofit.create(APIService::class.java)
//}