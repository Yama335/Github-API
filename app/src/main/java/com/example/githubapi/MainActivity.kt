package com.example.githubapi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.example.githubapi.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RepoAdapter

    private lateinit var loadingOverlay: View

    private val api = RetrofitClient.instance

    private lateinit var emptyView: TextView

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingOverlay = binding.loadingOverlay
        recyclerView = binding.recyclerView
        val searchInput = binding.searchInput
        val searchButton = binding.searchButton
        emptyView = binding.emptyView

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RepoAdapter(emptyList())
        recyclerView.adapter = adapter

        val repository = RepoRepository()
        val factory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(
            this,
            factory
        )[MainViewModel::class.java]


        searchButton.setOnClickListener {
            val username = searchInput.text.toString()
            if (username.isNotEmpty()) {
                viewModel.loadRepos(username)
            }
        }


        searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val username = searchInput.text.toString()
                if (username.isNotEmpty()) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchInput.windowToken, 0)
                    viewModel.loadRepos(username)
                }
                true
            } else {
                false
            }
        }

        viewModel.uiState.observe(this) { state ->

            when (state) {

                is UiState.Loading -> {
                    binding.loadingOverlay.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.loadingOverlay.visibility = View.GONE
                    adapter.updateData(state.repos)

                    binding.emptyView.visibility =
                        if (state.repos.isEmpty())
                            View.VISIBLE
                        else
                            View.GONE
                }

                is UiState.Error -> {
                    binding.loadingOverlay.visibility = View.GONE
                    adapter.updateData(emptyList())
                    Toast.makeText(this,
                        state.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }


        viewModel.loadRepos("octocat")
    }

}
