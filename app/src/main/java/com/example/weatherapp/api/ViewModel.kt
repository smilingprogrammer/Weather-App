package com.example.weatherapp.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.Data.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val weatherRespository: weatherRespository): ViewModel() {

    val weatherResponse:MutableLiveData<City> = MutableLiveData()

    //Allows the search of weather and giving response
    @ExperimentalCoroutinesApi
    private val searchChannel = ConflatedBroadcastChannel<String>()

    @ExperimentalCoroutinesApi
    fun setSearchQuery(search: String) {
        searchChannel.offer(search)
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getCityData() {
        viewModelScope.launch {
            searchChannel.asFlow()
                .flatMapLatest { search ->
                    // Receiving data from the weatherRepository
                    weatherRespository.getCityData(search)
                }.catch { e ->
                    Log.d("main", "${e.message}")
                }.collect { response ->
                    weatherResponse.value = response
                }
        }
    }

}