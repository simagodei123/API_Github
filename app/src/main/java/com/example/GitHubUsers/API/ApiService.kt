package com.example.GitHubUsers.API

import com.example.GitHubUsers.detail.DetailUserResponse
import com.example.GitHubUsers.GitHubResponse
import com.example.GitHubUsers.ItemsItem
import retrofit2.Call
import retrofit2.http.*

//persiapan retrofit
interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getListUser(
        @Query("q") query: String
    ): Call<GitHubResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}