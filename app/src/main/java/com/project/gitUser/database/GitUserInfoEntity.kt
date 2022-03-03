package com.project.gitUser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfoTable")
data class GitUserInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String,
    @ColumnInfo(name = "company")
    val company: String,
    @ColumnInfo(name = "createdAt")
    val created_at: String,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "updatedAt")
    val updated_at: String,
    @ColumnInfo(name = "url")
    val url: String
)
