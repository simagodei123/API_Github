package com.example.GitHubUsers.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.GitHubUsers.adapter.PrimaryAdapter
import com.example.GitHubUsers.databinding.ActivityMainBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import com.example.GitHubUsers.*
import com.example.GitHubUsers.setting.DarkModelFactory
import com.example.GitHubUsers.setting.SettingPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //menggunakan KTX
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModel.factory(SettingPreference(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.rvName.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvName.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvName.addItemDecoration(itemDecoration)

        mainViewModel.user.observe(this@MainActivity)  { user ->
            setListUser(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    //menampilkan data dari API
    private fun setListUser (user: List<ItemsItem>) {
        val listUser = ArrayList<ItemsItem>()
        for (user in user) {
            listUser.addAll(listOf(user))
        }
        val adapter = PrimaryAdapter(listUser)
        binding.rvName.adapter = adapter
        binding.rvName.visibility = View.VISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
    }

    //searchView
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        //deklarasi
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        //inisiasi
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        //operasi
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.rvName.visibility = View.GONE
                if (query != null) {
                    mainViewModel.searchUser(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_activity -> {
                Intent(this@MainActivity, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.darkMode -> {
                Intent(this@MainActivity, DarkActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return true
    }

}