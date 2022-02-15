package com.example.testapplication.repository

import android.content.Context
import com.example.testapplication.R
import com.example.testapplication.model.Image

class ImageRepository {

    fun getAllImages(context : Context) : List<Image>{
        //getting static images. ideally this will be a api call
        var images : List<Image> = getStaticImages(context)
        return images
    }

    private fun getStaticImages(context : Context) : List<Image>{
        var imageList = ArrayList<Image>()
        val imageResList = context.resources.obtainTypedArray(R.array.image_list)
        (0 until imageResList.length()).forEach {
            // get resource id of each drawable
            val image = Image("Image No : " + it,
                imageResList.getResourceId(it, -1))
            imageList.add(image)
        }
        return imageList
    }
}