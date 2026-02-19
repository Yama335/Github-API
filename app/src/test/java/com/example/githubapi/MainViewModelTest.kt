package com.example.githubapi
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        val fakeRepository = FakeRepoRepository()
        viewModel = MainViewModel(fakeRepository)
    }

    @Test
    fun `loadRepos returns Success state`() = runTest {

        viewModel.loadRepos("any")

        val state = viewModel.uiState.getOrAwaitValue()

        assertTrue(state is UiState.Success)
    }
}

