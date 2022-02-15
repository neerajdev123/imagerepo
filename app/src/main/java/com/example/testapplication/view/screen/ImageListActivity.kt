package com.example.testapplication.view.screen

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testapplication.R
import com.example.testapplication.model.Image
import com.example.testapplication.repository.ImageRepository
import com.example.testapplication.view.adapter.ImageListAdapter
import com.example.testapplication.view.listener.CellClickedListener
import com.example.testapplication.viewmodel.ImageViewModel
import com.example.testapplication.viewmodel.ImageViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class ImageListActivity : AppCompatActivity(), CellClickedListener {

    lateinit var viewModel: ImageViewModel
    lateinit var images : MutableList<Image>
    val adapter = ImageListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initViewModel()
    }

    private fun init(){
        toolbar.title = "Image List"
        setSupportActionBar(toolbar)
        recyclerview.adapter = this.adapter
        if(isTablet()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            recyclerview.layoutManager = GridLayoutManager(this,7)
        }else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            recyclerview.layoutManager = GridLayoutManager(this,3)
        }
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, ImageViewModelFactory(ImageRepository())).get(
            ImageViewModel::class.java)

        viewModel.imageList.observe(this, Observer {
            images = it as MutableList<Image>
            loadList()
        })

        progress_load.visibility = View.VISIBLE
        viewModel.getAllImages(this.applicationContext)

    }

    private fun loadList(){
        progress_load.visibility = View.GONE
        adapter.setImageList(images)
    }

    private fun isTablet() : Boolean {
        return this.getResources()
            .getConfiguration().screenLayout and
                Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    override fun onCellClicked(image: Image) {
        var intent = Intent(this,ImageDetailActivity::class.java)
        intent.putExtra("imageDetails",image)
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchView = menu?.findItem(R.id.action_search)!!
            .actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // filter recycler view when query submitted
                filter(query.toString())
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // filter recycler view when text is changed
                filter(query.toString());
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun filter(query : String){
        var filteredImages = ArrayList<Image>()
        for(image in images){
            if(image.name.toUpperCase().contains(query.toUpperCase())){
                filteredImages.add(image)
            }
        }
        //pass filtered list to adapter
        adapter.filterList(filteredImages)
    }

}
