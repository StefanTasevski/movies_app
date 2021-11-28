package com.tasevski.moviesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.tasevski.moviesapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            Glide.with(this@DetailsActivity)
                .load("http://image.tmdb.org/t/p/w500"+intent.getStringExtra("posterPath"))
                .into(imageViewLogo)
            title.text = intent.getStringExtra("title")
            overview.text = intent.getStringExtra("plotSynopsis")
        }
    }
}