package com.example.GitHubUsers.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.GitHubUsers.R
import com.example.GitHubUsers.adapter.SectionPagerAdapter
import com.example.GitHubUsers.ViewModelFactory
import com.example.GitHubUsers.database.FavoriteUser
import com.example.GitHubUsers.databinding.ActivityDetailUserBinding
import com.example.GitHubUsers.ui.insert.FavoriteViewModel
import com.example.GitHubUsers.ui.main.FavoriteActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var favUser: FavoriteUser? = null

    var checkFav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        favoriteViewModel = obtainViewModel(this@DetailUser)

        val username = intent.getStringExtra(EXTRA_USER).toString()
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        viewModel = ViewModelProvider(this@DetailUser, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)

        val userLogin = intent.getStringExtra(EXTRA_USER)
        binding.tvNamaUser.text = userLogin

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = userLogin.toString()

        val viewPager: ViewPager2 = findViewById(R.id.vp_detail)
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tab_detail)
        TabLayoutMediator(tabs, viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        if (userLogin != null) {
            showLoading(true)
            viewModel.getUserDetail(userLogin)
            showLoading(false)
        }

        viewModel.detailUser.observe(this) { detailUser ->
            setDetailUser(detailUser)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setDetailUser(Name: DetailUserResponse) {
        Glide.with(this@DetailUser)
            .load(Name.avatarUrl)
            .into(binding.imgUser)
        binding.tvNamaUser.text = Name.login
        binding.tvUsername.text = Name.name
        binding.tvFollowersNumber.text = Name.followers.toString()
        binding.tvFollowingNumber.text = Name.following.toString()

        favUser = FavoriteUser(Name.login.toString(), Name.avatarUrl.toString())

        CoroutineScope(Dispatchers.IO).launch {
            val username = intent.getStringExtra(EXTRA_USER).toString()
            val mcheckFav = favoriteViewModel.getAllFavByUsername(username)
            withContext(Dispatchers.Main) {
                if (mcheckFav.isNotEmpty()) {
                    checkFav = true
                    binding.fabFav.isSelected = true
                    binding.fabFav.setImageResource(R.drawable.baseline_favorite_24)
                }
                else {
                    checkFav = false
                    binding.fabFav.isSelected = false
                    binding.fabFav.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }

        binding.fabFav.setOnClickListener{
            if (!checkFav) {
                checkFav = true
                binding.fabFav.setImageResource(R.drawable.baseline_favorite_24)
                binding.fabFav.isSelected = true
                favoriteViewModel.insert(favUser as FavoriteUser)
                Toast.makeText(this,"${Name.name} ditambahkan ke Favorit", Toast.LENGTH_SHORT).show()
            }
            else {
                checkFav = false
                binding.fabFav.setImageResource(R.drawable.baseline_favorite_border_24)
                binding.fabFav.isSelected = false
                favoriteViewModel.delete(Name.login.toString())
                Toast.makeText(this,"${Name.name} dihapus dari Favorit", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbLoading.visibility = View.VISIBLE
        }
        else {
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_fav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.fav -> {
                Intent(this@DetailUser, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}