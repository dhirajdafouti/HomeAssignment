package com.project.gitUser.network.remotemodel

import com.project.gitUser.database.GitUserInfoEntity
import com.squareup.moshi.Json

data class UserInfoRemoteModel(
    val avatar_url: String?,
    val company: String,
    val created_at: String,
    val email: String?,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    val type: String,
    val updated_at: String,
    val url: String
)


