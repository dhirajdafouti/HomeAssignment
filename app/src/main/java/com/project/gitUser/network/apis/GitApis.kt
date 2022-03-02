package com.project.gitUser.network.apis

import com.project.gitUser.network.GitSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Github API communication setup via Retrofit.
 *
 */
interface GitApis {
    //The Example Query1:
    // https://api.github.com/search/repositories?sort=stars&q=Android&page=1&page_size=10
    //The suspend function will search the repositories user based on the query.
    @GET("search/repositories?sort=stars")
    suspend fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GitSearchResponse

    //The Example Query2:
    //https://api.github.com/search/users?q=CodingRock-Star&page=1&page_size=10
    //The suspend function will fetch the search user based on the query.
    @GET("search/user")
    suspend fun searchUser(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GitSearchResponse
}