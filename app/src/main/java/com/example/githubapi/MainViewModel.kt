package com.example.githubapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class MainViewModel(
    private val repository: RepoRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadRepos(username: String) {

        _uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val result = repository.getRepos(username)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value =
                    UiState.Error("ユーザーが存在しません")
            }
        }
    }
}


