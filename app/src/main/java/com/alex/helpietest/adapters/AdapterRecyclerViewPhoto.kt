package com.alex.helpietest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.alex.helpietest.R
import com.alex.helpietest.model.Photo
import com.squareup.picasso.Picasso


class AdapterRecyclerViewPhoto(val list: List<Photo>, val itemPhotoClickListener: ItemPhotoClickListener): RecyclerView.Adapter<AdapterRecyclerViewPhoto.ViewHolderPhoto>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPhoto {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)

        return ViewHolderPhoto(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderPhoto: ViewHolderPhoto, position: Int) {
        val photo = list[position]

        holderPhoto.title.text = photo.title

        Picasso.with(holderPhoto.context)
            .load(photo.thumbnailUrl)
            .into(holderPhoto.photoImg)
    }


    inner class ViewHolderPhoto(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        
        val photoImg: ImageView = itemView.findViewById(R.id.photo_img_itemPhoto)
        val title: TextView = itemView.findViewById(R.id.title_txv_itemPhoto)

        private val layoutPhoto: CardView = itemView.findViewById(R.id.layoutPhoto_itemPhoto)

        val context: Context = itemView.context

        init {
            layoutPhoto.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val photo = getData(adapterPosition)
            itemPhotoClickListener.onItemClick(photo)

        }

        private fun getData(id: Int): Photo {
            return list[id]
        }
    }
}


interface ItemPhotoClickListener{
    fun onItemClick(photo: Photo)
}
