package com.project.gitUser.network.apis

import com.project.gitUser.network.GitSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Github API communication setup via Retrofit.
 *
 */
interface GitApis {

    //The Example Query2:
    // https://api.github.com/search/users?q=followers:>1000&page=1&page_size=10&sort=asc
    //The suspend function will fetch the search user based on the query.
    @GET("search/user?q=followers:>1000")
    suspend fun searchUser(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GitSearchResponse
}