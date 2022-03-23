package com.rigelramadhan.dicodingbfaasubmission.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserProfileEntity

@Database(entities = [UserProfileEntity::class], version = 1)
abstract class UserProfileDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var instance: UserProfileDatabase? = null

        fun getInstance(context: Context): UserProfileDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, UserProfileDatabase::class.java, "profiles.db").build()
            }.also { instance = it }
    }
}