package com.example.GitHubUsers.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GitHubUsers.ItemsItem
import com.example.GitHubUsers.ViewModelFactory
import com.example.GitHubUsers.adapter.PrimaryAdapter
import com.example.GitHubUsers.database.FavoriteUser
import com.example.GitHubUsers.databinding.ActivityFavoriteBinding
import com.example.GitHubUsers.ui.insert.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private var list = ArrayList<ItemsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getFavorite()
    }

    private fun getFavorite() {
        val mFavorite = obtainViewModel(this@FavoriteActivity)
        mFavorite.getAllFav().observe(this) { userFav ->
            if (userFav != null) {
                binding.rvFav.visibility = View.VISIBLE
                setDataFavorite(userFav)
            }
        }
    }

    private fun setDataFavorite(userFav: List<FavoriteUser>) {
        list.clear()
        for (data in userFav) {
            val mFollow = ItemsItem(
                data.username ?: "",
                data.avatarUrl ?: ""
            )
            list.add(mFollow)
        }
        showRecyleList()
    }

    private fun showRecyleList() {
        binding.apply {
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            val listFavAdapter = PrimaryAdapter (list)
            rvFav.adapter = listFavAdapter
            binding.pbFav.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}