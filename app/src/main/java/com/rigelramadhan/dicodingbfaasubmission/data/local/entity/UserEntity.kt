package com.rigelramadhan.dicodingbfaasubmission.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @field:ColumnInfo(name = "gistsUrl")
    val gistsUrl: String,

    @field:ColumnInfo(name = "reposUrl")
    val reposUrl: String,

    @field:ColumnInfo(name = "followingUrl")
    val followingUrl: String,

    @field:ColumnInfo(name = "starredUrl")
    val starredUrl: String,

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "followersUrl")
    val followersUrl: String,

    @field:ColumnInfo(name = "type")
    val type: String,

    @field:ColumnInfo(name = "url")
    val url: String,

    @field:ColumnInfo(name = "subscriptionsUrl")
    val subscriptionsUrl: String,

    @field:ColumnInfo(name = "score")
    val score: Double,

    @field:ColumnInfo(name = "receivedEventsUrl")
    val receivedEventsUrl: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @field:ColumnInfo(name = "eventsUrl")
    val eventsUrl: String,

    @field:ColumnInfo(name = "htmlUrl")
    val htmlUrl: String,

    @field:ColumnInfo(name = "siteAdmin")
    val siteAdmin: Boolean,

    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "gravatarId")
    val gravatarId: String,

    @field:ColumnInfo(name = "nodeId")
    val nodeId: String,

    @field:ColumnInfo(name = "organizationsUrl")
    val organizationsUrl: String,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)