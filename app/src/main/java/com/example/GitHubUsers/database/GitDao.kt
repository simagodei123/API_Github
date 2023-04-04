package com.example.GitHubUsers.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(fav: FavoriteUser)

    @Query("DELETE FROM favorite WHERE username = :username")
    fun deleteFav(username: String)

    @Query("SELECT * FROM favorite ORDER BY username DESC")
    fun getAllFavorite(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavUserByUsername(username: String): List<FavoriteUser>
}
