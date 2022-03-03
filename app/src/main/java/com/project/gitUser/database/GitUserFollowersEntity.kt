package com.project.gitUser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json



/**
 * Entity class for the which defines the Git Table in the GitUser DataBase.
 */
@Entity(tableName = "gitUserFollowersTable")
data class GitUserFollowersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "name")
    val loginName: String?,
    @ColumnInfo(name = "url")
    val url: String?,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String?
)
