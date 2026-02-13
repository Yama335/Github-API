package com.example.githubapi

data class Repo(
    val id: Long,
    val name: String,
    val description: String?,
    val html_url: String
)