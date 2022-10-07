package com.excode.marketplace.data.remote

import com.excode.marketplace.data.remote.request.LoginRequest
import com.excode.marketplace.data.remote.request.RegisterRequest
import com.excode.marketplace.data.remote.response.*
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

    @GET("items/s")
    suspend fun searchProduct(
        @Query("q") productName: String
    ): MarketResponse

    @GET("items")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): MarketResponse

    @GET("carts")
    suspend fun getCarts(
        @Header("Authorization") token: String
    ): CartResponse

    @FormUrlEncoded
    @POST("carts")
    suspend fun addCart(
        @Header("Authorization") token: String,
        @Field("item_id") id: Int
    ): CartPostResponse

    @GET("wishlists")
    suspend fun getWishlist(
        @Header("Authorization") token: String
    ): WishlistResponse

    @FormUrlEncoded
    @POST("wishlists")
    suspend fun addWishlist(
        @Header("Authorization") token: String,
        @Field("item_id") id: Int
    ): WishlistPostResponse

    @DELETE("carts/{id}")
    suspend fun deleteCart(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): CartPostResponse

    @DELETE("wishlists/{id}")
    suspend fun deleteWishlist(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): WishlistResponse
}