package com.project.gitUser.utils

import com.project.gitUser.model.UserFollower
import com.project.gitUser.model.UserInfo

sealed class GitUserSearchResult {
    data class Success(val data: List<UserFollower>) : GitUserSearchResult()
    data class Error(val error: Exception) : GitUserSearchResult()
}

sealed class GitUserDetailsResult {
    data class Success(val data: UserInfo) : GitUserDetailsResult()
    data class Error(val error: Exception) : GitUserDetailsResult()
}