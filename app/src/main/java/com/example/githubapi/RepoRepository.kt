package com.example.githubapi

class RepoRepository {

    private val api = RetrofitClient.instance

    suspend fun getRepos(username: String): List<Repo> {
        return api.getRepos(username)
    }
}
