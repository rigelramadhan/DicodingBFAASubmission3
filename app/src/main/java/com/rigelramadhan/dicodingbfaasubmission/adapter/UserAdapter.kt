package com.rigelramadhan.dicodingbfaasubmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rigelramadhan.dicodingbfaasubmission.databinding.ItemUserBinding
import com.rigelramadhan.dicodingbfaasubmission.data.remote.response.ItemsItem
import com.rigelramadhan.dicodingbfaasubmission.view.ProfileActivity

class UserAdapter(private val activity: AppCompatActivity, private val users: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvName.text = user.login
        holder.binding.tvUrl.text = user.htmlUrl

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.binding.imgAvatar)

        holder.binding.rlUser.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            intent.putExtra(ProfileActivity.EXTRA_URL, user.login)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = users.size
}