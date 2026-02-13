package com.example.githubapi

data class User(
    val login: String,
    val name: String?,
    val public_repos: Int
)
