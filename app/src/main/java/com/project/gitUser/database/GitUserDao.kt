package com.project.gitUser.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The interface which will interact with Database to get the Git User Search Data from database.
 */
@Dao
interface GitUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gitUserData: List<GitUserDatabase>)
    //The suspend function will query all the data from  the git table.
    @Query(
        "SELECT * FROM gitUserTable"
    )
    suspend fun reposByName(): List<GitUserDatabase>

    //The suspend function will delete the git table.
    @Query("DELETE FROM gitUserTable")
    suspend fun deleteGitRepo()
}