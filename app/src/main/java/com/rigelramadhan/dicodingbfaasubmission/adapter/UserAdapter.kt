package com.rigelramadhan.dicodingbfaasubmission.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserEntity
import com.rigelramadhan.dicodingbfaasubmission.databinding.ItemUserBinding

class UserAdapter (
    private val onClickListener: (UserEntity) -> Unit
) : ListAdapter<UserEntity, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding.tvName.text = user.login
        holder.binding.tvUrl.text = user.htmlUrl

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.binding.imgAvatar)

        holder.binding.rlUser.setOnClickListener { onClickListener(user) }
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem.login == newItem.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
}