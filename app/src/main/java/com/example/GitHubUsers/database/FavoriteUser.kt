package com.example.GitHubUsers.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteUser (
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String = "",
): Parcelable
