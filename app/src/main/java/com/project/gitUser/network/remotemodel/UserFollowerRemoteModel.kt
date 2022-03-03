package com.project.gitUser.network.remotemodel
import com.project.gitUser.database.GitUserFollowersEntity
import com.squareup.moshi.Json

/**
 * Network DataTransferObjects converted to the database object. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. Converting  to database objects before
 * storing them.
 */

data class GitUserFollowersResponse(
    @Json(name = "total_count") val total: Int = 0,
    @Json(name = "items") val items: List<GitUserFollowersInfo>,
    val nextPage: Int = 0
)

data class GitUserFollowersInfo(
    @Json(name = "id") val id: Long,
    @Json(name = "login") val name: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "avatar_url") val profile_url: String?
)

data class NetworkGitUserSearchDataObject(var gitSearchResponse: List<GitUserFollowersInfo>)


//Converting the network data object to database objects.
fun NetworkGitUserSearchDataObject.asDatabaseModel(): List<GitUserFollowersEntity> {
    return gitSearchResponse.map {
        GitUserFollowersEntity(
            id = it.id,
           loginName = it.name,
            url=it.url,
            avatar_url = it.profile_url
        )
    }
}
