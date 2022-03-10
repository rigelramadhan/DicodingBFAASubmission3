package com.rigelramadhan.dicodingbfaasubmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rigelramadhan.dicodingbfaasubmission.databinding.ItemFollowUserBinding
import com.rigelramadhan.dicodingbfaasubmission.model.FollowersResponseItem
import com.rigelramadhan.dicodingbfaasubmission.view.ProfileActivity

class UserFollowersAdapter(private val activity: AppCompatActivity, private val users: List<FollowersResponseItem>) : RecyclerView.Adapter<UserFollowersAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemFollowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFollowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvName.text = user.login

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.binding.imgAvatar)

        holder.binding.cardUser.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            intent.putExtra(ProfileActivity.EXTRA_URL, user.login)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount() = users.size
}