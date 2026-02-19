package com.example.githubapi

class FakeRepoRepository : RepoRepository {

    override suspend fun getRepos(username: String): List<Repo> {

        return listOf(
            Repo(
                id = 1,
                name = "FakeRepo1",
                description = "これはテスト用データです",
                html_url = "https://github.com/fake1"
            ),
            Repo(
                id = 2,
                name = "FakeRepo2",
                description = "ネットワークは使っていません",
                html_url = "https://github.com/fake2"
            )
        )
    }
}