package com.example.GitHubUsers.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.preferencesDataStore
import com.example.GitHubUsers.setting.DarkModelFactory
import com.example.GitHubUsers.R
import com.example.GitHubUsers.databinding.ActivityDarkBinding
import com.example.GitHubUsers.setting.SettingPreference
import com.google.android.material.switchmaterial.SwitchMaterial

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDarkBinding
    private val viewModel by viewModels<DarkModelFactory> {
        DarkModelFactory.factory(SettingPreference(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

       /* viewModel.getThemeSetting().observe(this) {
            if (it) {
                binding.switchTheme.text = "Dark Mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else {
                binding.switchTheme.text = "Light Mode"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener() { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        } */

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        //val pref = SettingPreference.getInstance(prefDataStore)

        //val mainDarkModel = ViewModelProvider(this, DarkModelFactory(pref))[MainViewModel::class.java]

        viewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener() { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
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