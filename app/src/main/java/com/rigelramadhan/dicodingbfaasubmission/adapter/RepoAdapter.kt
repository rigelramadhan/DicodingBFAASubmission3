package com.rigelramadhan.dicodingbfaasubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rigelramadhan.dicodingbfaasubmission.databinding.ItemRepoBinding
import com.rigelramadhan.dicodingbfaasubmission.model.RepoResponseItem

class RepoAdapter(private val repos: List<RepoResponseItem>) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repos[position]

        holder.binding.tvName.text = repo.fullName
        holder.binding.tvDescription.text = repo.description
        holder.binding.tvLanguage.text = repo.language
    }

    override fun getItemCount() = repos.size
}