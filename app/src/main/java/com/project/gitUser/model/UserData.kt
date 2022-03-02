package com.project.gitUser.model

import android.os.Parcelable
import com.project.gitUser.database.GitUserDatabase
import kotlinx.android.parcel.Parcelize

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 * The below data class represent the UserData shown to the UI.
 */
@Parcelize
data class UserData(
    val id: Long,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val url: String?,
    val web_url: String?,
    val stars: Int,
    val forks: Int,
    val language: String?,
    val watcherCount: String?,
    val createdAt: String?,
    val updated: String?,
    val profileUrl: String?
) : Parcelable

data class DataBaseDomainToUserDomain(var databaseData: List<GitUserDatabase>)

//Converting the database data object to domain objects.
fun DataBaseDomainToUserDomain.asDomainModel(): List<UserData> {
    return databaseData.map {
        UserData(
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
            profileUrl = it.avatar_url
        )
    }
}
