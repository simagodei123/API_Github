package com.example.GitHubUsers.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.GitHubUsers.database.FavoriteUser
import com.example.GitHubUsers.repository.GitRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val mGitRepository: GitRepository = GitRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        mGitRepository.insert(favoriteUser)
    }

    fun delete(user: String) {
        mGitRepository.delete(user)
    }

    fun getAllFav(): LiveData<List<FavoriteUser>> = mGitRepository.getAllFavorite()

    fun getAllFavByUsername(username: String): List<FavoriteUser> = mGitRepository.getAllFavByUsername(username)

}