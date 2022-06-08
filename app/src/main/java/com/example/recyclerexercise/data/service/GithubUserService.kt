package com.example.recyclerexercise.data.service

import com.example.recyclerexercise.data.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserService {

    @GET("users")
    suspend fun getUsers(): Response<List<GithubUser>>

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int = 0,
    ): Response<List<GithubUser>>

}