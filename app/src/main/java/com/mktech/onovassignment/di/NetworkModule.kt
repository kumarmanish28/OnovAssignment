package com.mktech.onovassignment.di

import com.mktech.onovassignment.data.api.ApiService
import com.mktech.onovassignment.data.repository.CharacterRepository
import com.mktech.onovassignment.data.repository.CharacterRepositoryImpl
import com.mktech.onovassignment.util.MyConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiImpl(apiService: ApiService): CharacterRepository =
        CharacterRepositoryImpl(apiService)

}