package com.example.githubapi

sealed class UiState {

    object Loading : UiState()

    data class Success(
        val repos: List<Repo>
    ) : UiState()

    data class Error(
        val message: String
    ) : UiState()
}
