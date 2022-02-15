package com.example.testapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.model.Image
import com.example.testapplication.view.listener.CellClickedListener

class ImageListAdapter (private val cellClickedListener: CellClickedListener) : RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {

    var images = mutableListOf<Image>()

    class ImageViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img_display)
        val description : TextView = itemView.findViewById(R.id.txt_description)
    }

    fun setImageList(images: List<Image>) {
        this.images = images.toMutableList()
        notifyDataSetChanged()
    }

    fun filterList(images : List<Image>){
        this.images = images.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list, parent, false)

        return ImageViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.description.text = image.name
        holder.image.setImageResource(image.imageSrc)
        holder.itemView.setOnClickListener {
            cellClickedListener.onCellClicked(image)
        }
    }
}