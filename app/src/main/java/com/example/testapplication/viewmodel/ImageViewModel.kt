package com.example.testapplication.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapplication.model.Image
import com.example.testapplication.repository.ImageRepository

class ImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    val imageList = MutableLiveData<List<Image>>()

    fun getAllImages(appContext : Context){
        val images = imageRepository.getAllImages(appContext)
        imageList.value = images
    }
}