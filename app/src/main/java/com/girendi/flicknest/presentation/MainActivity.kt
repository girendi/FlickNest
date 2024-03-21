package com.girendi.flicknest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.girendi.flicknest.R
import com.girendi.flicknest.databinding.ActivityMainBinding
import com.girendi.flicknest.presentation.genres.GenreFragment
import com.girendi.flicknest.presentation.home.HomeFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupBottomNavigation()
        handleOnClick()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(1, resources.getString(R.string.home), R.drawable.ic_home_white)
        )
        binding.bottomNavigation.add(
            CurvedBottomNavigation.Model(2, resources.getString(R.string.genre), R.drawable.ic_dashboard_white)
        )
    }

    private fun handleOnClick() {
        binding.bottomNavigation.setOnClickMenuListener {
            when(it.id) {
                1 -> {
                    replaceFragment(HomeFragment())
                }
                2 -> {
                    replaceFragment(GenreFragment())
                }
            }
        }

        replaceFragment(HomeFragment())
        binding.bottomNavigation.show(1)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.contentFrame.id, fragment)
            .commit()
    }
}