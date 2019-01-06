package com.test.headytest.di

import com.test.headytest.ui.home.MainActivity
import com.test.helpers.di.AppModule
import com.test.helpers.di.RetrofitModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, RetrofitModule::class, RealmModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}