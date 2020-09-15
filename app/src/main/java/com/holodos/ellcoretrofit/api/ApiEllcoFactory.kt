package com.holodos.ellcoretrofit.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiEllcoFactory {
    fun create(baseUrl: String): ApiEllcoService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()

        return retrofit.create(ApiEllcoService::class.java)
    }
}