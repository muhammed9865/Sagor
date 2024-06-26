package com.salman.sagor.data.di

import android.content.Context
import com.salman.sagor.data.source.RemoteConstants.BASE_URL
import com.salman.sagor.data.source.user.UserLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/26/2024.
 */
@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                header("Content-Type", "application/json")
                url(BASE_URL)
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
        }
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(
        @ApplicationContext context: Context
    ): UserLocalDataSource {
        return UserLocalDataSource(context)
    }
}