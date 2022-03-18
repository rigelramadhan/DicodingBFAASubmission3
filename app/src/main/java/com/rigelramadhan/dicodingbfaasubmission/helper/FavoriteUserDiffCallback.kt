package com.rigelramadhan.dicodingbfaasubmission.helper

import androidx.recyclerview.widget.DiffUtil
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.FavoriteUserEntity

class FavoriteUserDiffCallback(
    private val oldFavoriteUserList: List<FavoriteUserEntity>,
    private val newFavoriteUserList: List<FavoriteUserEntity>
    ): DiffUtil.Callback() {
    override fun getOldListSize() = oldFavoriteUserList.size

    override fun getNewListSize() = newFavoriteUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteUserList[oldItemPosition].id == newFavoriteUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUserContent = oldFavoriteUserList[oldItemPosition]
        val newUserContent = newFavoriteUserList[newItemPosition]
        return oldUserContent.login == newUserContent.login &&
                oldUserContent.url == newUserContent.url
    }
}