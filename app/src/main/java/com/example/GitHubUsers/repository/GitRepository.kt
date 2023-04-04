package com.example.GitHubUsers.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.GitHubUsers.database.FavUserDatabase
import com.example.GitHubUsers.database.FavoriteUser
import com.example.GitHubUsers.database.GitDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GitRepository(application: Application) {

    private val mGitDao: GitDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserDatabase.getDatabase(application)
        mGitDao = db.gitDao()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mGitDao.insertFav(favoriteUser) }
    }

    fun delete(user: String) {
        executorService.execute { mGitDao.deleteFav(user) }
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mGitDao.getAllFavorite()

    fun getAllFavByUsername(username: String) = mGitDao.getFavUserByUsername(username)

}