package com.project.gitUser.utils

import com.project.gitUser.model.UserData

sealed class GitUserSearchResult {
    data class Success(val data:List<UserData>) : GitUserSearchResult()
    data class Error(val error: Exception) : GitUserSearchResult()
}