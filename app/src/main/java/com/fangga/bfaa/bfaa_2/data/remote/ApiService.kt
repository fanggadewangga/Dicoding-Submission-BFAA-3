package com.fangga.bfaa.bfaa_2.data.remote

import com.fangga.bfaa.bfaa_2.data.model.SearchResponse
import com.fangga.bfaa.bfaa_2.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Search user by query
    @GET("search/users")
    suspend fun searchUser(
        @Query("q")
        query: String
    ): SearchResponse

    // Get user detail
    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username")
        username: String
    ): User

    // Get user's followers
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username")
        username: String
    ): List<User>

    // Get user's followings
    @GET("users/{username}/following")
    suspend fun getUserFollowings(
        @Path("username")
        username: String
    ): List<User>
}