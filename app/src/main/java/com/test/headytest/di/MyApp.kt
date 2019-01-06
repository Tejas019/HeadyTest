package com.test.headytest.di

import android.app.Application
import com.tejas.helpers.constants.Constants.Companion.BASE_URL
import com.test.helpers.di.AppModule
import com.test.helpers.di.RetrofitModule
import okhttp3.Interceptor
import okhttp3.Request

class MyApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        val interceptor = Interceptor { chain ->
            val newRequest: Request
            val request = chain.request()
            val builder = request.newBuilder()
            newRequest = builder.build()
            chain.proceed(newRequest)
        }

        val responseInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            response
        }

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .retrofitModule(RetrofitModule(BASE_URL, listOf(interceptor), listOf(responseInterceptor)))
                .realmModule(RealmModule(this))
                .build()
    }
}