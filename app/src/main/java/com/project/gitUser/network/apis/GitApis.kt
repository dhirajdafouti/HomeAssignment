package com.project.gitUser.network.apis

import com.project.gitUser.network.remotemodel.GitUserFollowersResponse
import com.project.gitUser.network.remotemodel.UserInfoRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Github API communication setup via Retrofit.
 *
 */
interface GitApis {

    //The Example Api1:
    //https://api.github.com/search/users?q=followers:>1000&page=1&page_size=10&sort=asc
    @GET("search/users?q=followers:>1000")
    suspend fun searchUserFollowers(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GitUserFollowersResponse

    //The Example Api2:
    //https://api.github.com/users/torvalds
    @GET("users/{user}")
    suspend fun userInfo(@Path("user") user: String):UserInfoRemoteModel
}