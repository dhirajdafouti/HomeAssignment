package com.project.gitUser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class for the which defines the Git Table in the GitUser DataBase.
 */
@Entity(tableName = "gitUserTable")
data class GitUserDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "full_name")
    val fullName: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "url")
    val url: String?,
    @ColumnInfo(name = "html_url")
    val web_url: String?,
    @ColumnInfo(name = "stargazers_count")
    val stars: Int,
    @ColumnInfo(name = "forks_count")
    val forks: Int,
    @ColumnInfo(name = "language")
    val language: String?,
    @ColumnInfo(name = "watchers_count")
    val watcherCount: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: String?,
    @ColumnInfo(name = "updated_at")
    val updated: String?,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String?
)
