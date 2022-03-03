package com.project.gitUser.utils

import com.project.gitUser.model.UserFollower

sealed class GitUserSearchResult {
    data class Success(val data:List<UserFollower>) : GitUserSearchResult()
    data class Error(val error: Exception) : GitUserSearchResult()
}