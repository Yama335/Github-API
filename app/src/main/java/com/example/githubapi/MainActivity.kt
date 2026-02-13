package com.example.githubapi

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.example.githubapi.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RepoAdapter

    private lateinit var loadingOverlay: View

    private val api = RetrofitClient.instance

    private lateinit var emptyView: TextView

    private lateinit var binding: ActivityMainBinding

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

        searchButton.setOnClickListener {
            val username = searchInput.text.toString()
            if (username.isNotEmpty()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(searchInput.windowToken, 0)
                loadRepos(username)
            }
        }

        searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val username = searchInput.text.toString()
                if (username.isNotEmpty()) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchInput.windowToken, 0)
                    loadRepos(username)
                }
                true
            } else {
                false
            }
        }

        loadRepos("octocat")
    }

    private fun loadRepos(username: String) {

        loadingOverlay.visibility = View.VISIBLE

        lifecycleScope.launch {

            try {
                val repos = api.getRepos(username)

                adapter.updateData(repos)

                if (repos.isEmpty()) {
                    emptyView.visibility = View.VISIBLE
                } else {
                    emptyView.visibility = View.GONE
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "ユーザーが存在しません",
                    Toast.LENGTH_SHORT
                ).show()

                adapter.updateData(emptyList())
            } finally {
                loadingOverlay.visibility = View.GONE
            }
        }
    }

}
