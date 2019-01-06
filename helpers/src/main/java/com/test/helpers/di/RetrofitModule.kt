package com.test.helpers.di

import com.test.helpers.googleutils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Module
class RetrofitModule constructor(val baseUrl: String, val interceptor: List<Interceptor>?, val responseInterceptor: List<Interceptor>?) {

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(client)
                .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        interceptor?.forEach { okHttpClient.addInterceptor(it) }
        responseInterceptor?.forEach { okHttpClient.addNetworkInterceptor(it) }
        return okHttpClient.build()
    }
}