package com.alex.helpietest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.alex.helpietest.R
import com.alex.helpietest.model.User


class AdapterRecyclerViewUser(val list: List<User>, val itemUserClickListener: ItemUserClickListener): RecyclerView.Adapter<AdapterRecyclerViewUser.ViewHolderUser>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUser {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)

        return ViewHolderUser(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderUser: ViewHolderUser, position: Int) {
        val user = list[position]
        holderUser.name.text = user.name
        holderUser.email.text = user.email
        holderUser.companyName.text = user.company.name
        holderUser.city.text = user.address.city
    }


    inner class ViewHolderUser(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val name: TextView = itemView.findViewById(R.id.name_txv_itemUser)
        val email: TextView = itemView.findViewById(R.id.email_txv_itemUser)
        val companyName: TextView = itemView.findViewById(R.id.companyName_txv_itemUser)
        val city: TextView = itemView.findViewById(R.id.city_txv_itemUser)
        private val layoutUser: CardView = itemView.findViewById(R.id.layoutUser_itemUser)

        init {
            layoutUser.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val user = getData(adapterPosition)
            itemUserClickListener.onItemClick(user)

        }

        private fun getData(id: Int): User {
            return list[id]
        }
    }
}


interface ItemUserClickListener{
    fun onItemClick(user: User)

}
