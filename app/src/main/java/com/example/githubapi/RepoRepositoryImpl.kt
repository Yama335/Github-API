package com.example.githubapi

class RepoRepositoryImpl(
    private val api: GitHubApi
) : RepoRepository {

    override suspend fun getRepos(username: String): List<Repo> {
        return api.getRepos(username)
    }
}
