package com.test.headytest.repository

import com.test.headytest.ui.home.JsonData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("json/")
    fun getData(): Call<JsonData?>
}