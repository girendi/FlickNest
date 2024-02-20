package com.girendi.flicknest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.girendi.flicknest.databinding.ActivityGenresBinding

class GenresActivity: AppCompatActivity() {

    private lateinit var binding: ActivityGenresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}