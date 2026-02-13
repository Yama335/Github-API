package com.example.githubapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.DiffUtil



class RepoAdapter(private var repoList: List<Repo>) :
    RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    fun updateData(newList: List<Repo>) {

        val diffCallback = object : DiffUtil.Callback() {

            override fun getOldListSize(): Int = repoList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return repoList[oldItemPosition].id ==
                        newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return repoList[oldItemPosition] ==
                        newList[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        repoList = newList
        diffResult.dispatchUpdatesTo(this)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.repoName)
        val description: TextView = view.findViewById(R.id.repoDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repoList[position]
        holder.name.text = repo.name
        holder.description.text = repo.description ?: "No description"
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.html_url))
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = repoList.size
}
