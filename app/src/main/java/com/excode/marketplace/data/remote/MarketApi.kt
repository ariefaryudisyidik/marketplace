package com.excode.marketplace.data.remote

import com.excode.marketplace.data.remote.request.LoginRequest
import com.excode.marketplace.data.remote.request.RegisterRequest
import com.excode.marketplace.data.remote.response.AuthResponse
import com.excode.marketplace.data.remote.response.ProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MarketApi {

    @POST("login")
    suspend fun login(
        @Body login: LoginRequest
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Body register: RegisterRequest
    ): AuthResponse

    @GET("list-shops")
    suspend fun getProducts(): ProductResponse
}