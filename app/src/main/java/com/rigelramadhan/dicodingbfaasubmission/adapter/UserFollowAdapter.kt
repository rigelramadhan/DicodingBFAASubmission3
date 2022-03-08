package com.rigelramadhan.dicodingbfaasubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rigelramadhan.dicodingbfaasubmission.databinding.ItemFollowUserBinding

class UserFollowAdapter() : RecyclerView.Adapter<UserFollowAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemFollowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFollowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}