package com.excode.marketplace.data.remote

import com.excode.marketplace.data.remote.request.LoginRequest
import com.excode.marketplace.data.remote.request.RegisterRequest
import com.excode.marketplace.data.remote.response.AuthResponse
import com.excode.marketplace.data.remote.response.MarketResponse
import com.excode.marketplace.data.remote.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface MarketApi {

    @POST("login")
    suspend fun login(
        @Body login: LoginRequest
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Body register: RegisterRequest
    ): AuthResponse

    @Multipart
    @POST("user/profile")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Part picture: MultipartBody.Part? = null,
        @Part("username") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
    ): UserResponse


    @GET("user/profile")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserResponse

    @GET("shops")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): MarketResponse
}