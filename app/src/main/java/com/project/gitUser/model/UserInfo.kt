package com.project.gitUser.model

import com.project.gitUser.database.GitUserInfoEntity
import com.project.gitUser.network.remotemodel.UserInfoRemoteModel


data class UserInfo(
    val company: String?,
    val created_at: String?,
    val email: String?,
    val id: Int,
    val location: String?,
    val login: String?,
    val name: String?,
    val type: String?,
    val updated_at: String?,
    val url: String?
)
data class NetworkGitUserSearchDataObject(var gitSearchResponse: UserInfoRemoteModel)


//Converting the network data object to database objects.
fun NetworkGitUserSearchDataObject.asDatabaseModel(): GitUserInfoEntity {
    return GitUserInfoEntity(
        id=gitSearchResponse.id,
        avatar_url = gitSearchResponse.avatar_url,
       company= gitSearchResponse.company,
        created_at = gitSearchResponse.created_at,
        email = gitSearchResponse.email,
        location = gitSearchResponse.location,
        login=gitSearchResponse.login,
        name=gitSearchResponse.name,
       type= gitSearchResponse.type,
        updated_at = gitSearchResponse.updated_at,
        url=gitSearchResponse.url
    )
    }
