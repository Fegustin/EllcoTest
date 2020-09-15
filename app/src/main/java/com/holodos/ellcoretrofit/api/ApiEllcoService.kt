package com.holodos.ellcoretrofit.api

import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiEllcoService {
    @GET("bug_tracker/")
    fun getTicket(
        @Header("X-AUTH-TOKEN") token: String,
        @Header("Content-Type") type: String = "application/json",
        @Header("Application") application: String = "application/json"
    ): Observable<ResponseBody>
}