package com.synrgy.travelid.di

import android.app.Application
import android.content.Context
import com.synrgy.travelid.data.repository.RemoteRepository
import com.synrgy.travelid.data.remote.service.AuthApi
import com.synrgy.travelid.domain.repository.AuthRepository
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
        authApi: AuthApi
    ): RemoteRepository {
        return RemoteRepository(
            authApi
        )
    }

    @Singleton
    @Provides
    fun provideAuthRepository(remoteRepository: RemoteRepository): AuthRepository {
        return remoteRepository
    }
}