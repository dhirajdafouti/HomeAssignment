package com.project.gitUser.network

import com.project.gitUser.database.GitUserDatabase
import com.squareup.moshi.Json

/**
 * Network DataTransferObjects converted to the database object. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. Converting  to database objects before
 * storing them.
 */

data class GitSearchResponse(
    @Json(name = "total_count") val total: Int = 0,
    @Json(name = "items") val items: List<GitUserSearchInfo>,
    val nextPage: Int = 0
)

data class GitUserSearchInfo(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "full_name") val fullName: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "html_url") val web_url: String?,
    @Json(name = "stargazers_count") val stars: Int,
    @Json(name = "forks_count") val forks: Int,
    @Json(name = "language") val language: String?,
    @Json(name = "watchers_count") val watcherCount: String?,
    @Json(name = "created_at") val createdAt: String?,
    @Json(name = "updated_at") val updated: String?,
    @Json(name = "type") val profile_url: String?
)


data class NetworkGitUserSearchDataObject(var gitSearchResponse: List<GitUserSearchInfo>)


//Converting the network data object to database objects.
fun NetworkGitUserSearchDataObject.asDatabaseModel(): List<GitUserDatabase> {
    return gitSearchResponse.map {
        GitUserDatabase(
            id = it.id,
            name = it.name,
            fullName = it.fullName,
            description = it.description,
            url = it.url,
            web_url = it.web_url,
            stars = it.stars,
            forks = it.forks,
            language = it.language,
            watcherCount = it.watcherCount,
            createdAt = it.createdAt,
            updated = it.updated,
            avatar_url = it.profile_url
        )
    }
}