package com.example.githubapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{user}/repos")
    suspend fun getRepos(
        @Path("user") user: String
    ): List<Repo>
}
