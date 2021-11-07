package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.api.ViewModel
import com.example.weatherapp.api.weatherRespository
import com.example.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModel by viewModels()
    @FlowPreview
    @ExperimentalCoroutinesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.getCityData()
        initListener()
        viewModel.weatherResponse.observe(this, Observer { response ->

            if (response.weather[0].description == "clear sky" || response.weather[0].description == "mist'") {
                Glide.with(this)
                    .load(R.drawable.clouds)
                    .into(binding.image)
            } else if (response.weather[0].description == "haze" || response.weather[0].description == "overcast clouds" || response.weather[0].description == "fog") {
                Glide.with(this)
                    .load(R.drawable.haze)
                    .into(binding.image)
            }else if (response.weather[0].description == "rain") {
                Glide.with(this)
                    .load(R.drawable.rain)
                    .into(binding.image)
            }
            binding.description.text=response.weather[0].description
            binding.name.text=response.name
            binding.degree.text=response.wind.deg.toString()
            binding.speed.text=response.wind.speed.toString()
            binding.temp.text=response.main.temp.toString()
            binding.humidity.text=response.main.humidity.toString()
        })
    }

    @ExperimentalCoroutinesApi
    private fun initListener(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.setSearchQuery(it) }
                Log.d("main", "OnQueryTextChange: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
    }
}