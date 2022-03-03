package com.project.gitUser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GitUserFollowersEntity::class,GitUserInfoEntity::class],
    exportSchema = false,
    version = 1
)
abstract class GitUserRoomDataBase : RoomDatabase() {
    abstract val gitUserDao: GitUserDao

}

//Late Init Instance of Room Data Base
private lateinit var INSTANCE: GitUserRoomDataBase

/**
 * This database will be the Single Source of Truth.
 * The method will provide the instance of GitUserRoom Database.
 * The instance will be initialized once it is called first time.
 * After the repeatable calls will provide the same instance, as creating the instance of Database is expensive.
 */
fun getDataBase(context: Context): GitUserRoomDataBase {
    synchronized(GitUserRoomDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                GitUserRoomDataBase::class.java, "gitUserFollower"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}