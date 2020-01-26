package com.alex.helpietest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alex.helpietest.R
import com.alex.helpietest.model.Post


class AdapterRecyclerViewPost(private val list: List<Post>): RecyclerView.Adapter<AdapterRecyclerViewPost.ViewHolderPost>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPost {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)

        return ViewHolderPost(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderPost: ViewHolderPost, position: Int) {
        val post = list[position]
        holderPost.title.text = post.title
        holderPost.body.text = post.body
    }


    inner class ViewHolderPost(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.title_txv_itemPost)
        val body: TextView = itemView.findViewById(R.id.body_txv_itemPost)
    }

}

