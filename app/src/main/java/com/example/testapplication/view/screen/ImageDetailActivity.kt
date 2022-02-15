package com.example.testapplication.view.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.testapplication.R
import com.example.testapplication.databinding.ActivityImageDetailBinding
import com.example.testapplication.model.Image
import kotlinx.android.synthetic.main.activity_image_detail.view.*

class ImageDetailActivity : AppCompatActivity() {

    lateinit var image : Image
    lateinit var binding : ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_image_detail)
        getIntentDetails()
        initUI()
    }

    private fun getIntentDetails(){
        image = intent.extras?.get("imageDetails") as Image
        binding.image = image
        binding.imgFullScreen.setImageResource(image.imageSrc)
    }

    private fun initUI(){
        binding.toolbarDetail.title = "Image Details"
        setSupportActionBar( binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            super.onBackPressed()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
