package com.example.githubapi

interface RepoRepository {
    suspend fun getRepos(username: String): List<Repo>
}

