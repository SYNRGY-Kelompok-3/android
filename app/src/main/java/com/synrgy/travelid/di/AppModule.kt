package com.synrgy.travelid.di

import android.app.Application
import android.content.Context
import com.synrgy.travelid.data.local.DataStoreManager
import com.synrgy.travelid.data.remote.service.AuthAPIService
import com.synrgy.travelid.data.remote.service.MainAPIService
import com.synrgy.travelid.data.remote.service.SideAPIService
import com.synrgy.travelid.data.repository.RemoteRepository
import com.synrgy.travelid.domain.repository.AuthRepository
import com.synrgy.travelid.domain.repository.MainRepository
import com.synrgy.travelid.domain.repository.SideRepository
import com.synrgy.travelid.domain.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        authAPIService: AuthAPIService,
        mainAPIService: MainAPIService,
        sideAPIService: SideAPIService,
        dataStoreManager: DataStoreManager
    ): RemoteRepository {
        return RemoteRepository(
            dataStoreManager,
            authAPIService,
            mainAPIService,
            sideAPIService
        )
    }

    @Singleton
    @Provides
    fun provideAuthRepository(remoteRepository: RemoteRepository): AuthRepository {
        return remoteRepository
    }

    @Singleton
    @Provides
    fun provideTokenRepository(remoteRepository: RemoteRepository): TokenRepository {
        return remoteRepository
    }

    @Singleton
    @Provides
    fun provideMainRepository(remoteRepository: RemoteRepository): MainRepository {
        return remoteRepository
    }

    @Singleton
    @Provides
    fun provideSideRepository(remoteRepository: RemoteRepository): SideRepository {
        return remoteRepository
    }
}