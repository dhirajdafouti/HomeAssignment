package com.project.gitUser.model

import android.os.Parcelable
import com.project.gitUser.database.GitUserFollowersEntity
import kotlinx.android.parcel.Parcelize

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 * The below data class represent the UserData shown to the UI.
 */
@Parcelize
data class UserFollower(
    val id: Long,
    val loginName: String?,
    val url: String?,
    val profileUrl: String?
) : Parcelable


data class DataBaseDomainToUserDomain(var databaseData: List<GitUserFollowersEntity>)

//Converting the database data object to domain objects.
fun DataBaseDomainToUserDomain.asDomainModel(): List<UserFollower> {
    return databaseData.map {
        UserFollower(
            id = it.id,
            loginName = it.loginName,
            url = it.url,
            profileUrl = it.avatar_url
        )
    }
}
