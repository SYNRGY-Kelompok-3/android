package com.synrgy.travelid.di

import android.app.Application
import android.content.Context
import com.synrgy.travelid.data.remote.service.AuthAPI
import com.synrgy.travelid.data.repository.RemoteRepository
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
        authAPI: AuthAPI
    ): RemoteRepository {
        return RemoteRepository(
            authAPI
        )
    }

    @Singleton
    @Provides
    fun provideAuthRepository(remoteRepository: RemoteRepository): AuthRepository {
        return remoteRepository
    }
}