package com.project.gitUser.database

import androidx.room.*
import retrofit2.http.DELETE

/**
 * The interface which will interact with Database to get the Git User Search Data from database.
 */
@Dao
interface GitUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFollowers(gitUserData: List<GitUserFollowersEntity>)
    //The suspend function will query all the data from  the git table.
    @Query(
        "SELECT * FROM gitUserFollowersTable")
    suspend fun queryUserFollowers(): List<GitUserFollowersEntity>

    //The suspend function will delete the git table.
    @Query("DELETE FROM gitUserFollowersTable")
    suspend fun deleteUserFollowers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: GitUserInfoEntity)

    @Query("DELETE FROM UserInfoTable")
    suspend fun deleteUserInfo()
}