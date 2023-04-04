package com.example.GitHubUsers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavUserDatabase: RoomDatabase() {
    abstract fun gitDao(): GitDao

    companion object {
        @Volatile
        private var INSTACE: FavUserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavUserDatabase {
            if (INSTACE == null) {
                synchronized(FavUserDatabase::class.java) {
                    INSTACE = Room.databaseBuilder(context.applicationContext,
                            FavUserDatabase::class.java, "git_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTACE as FavUserDatabase
        }
    }

}