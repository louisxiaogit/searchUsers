package com.louis.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.louis.myapplication.R
import com.louis.myapplication.data.User

class UserListAdapter(
    context: Context,
    private val dataList: MutableList<User>
) : RecyclerView.Adapter<UserListAdapter.UsrViewHolder>() {

    private val context: Context = context
    private val items: MutableList<User> = dataList

    fun setItems(newItems: List<User>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<User>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class UsrViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tvName: TextView
        lateinit var ivAvatar: ImageView
        fun bind(item: User) {
            tvName = itemView.findViewById(R.id.tv_name)
            ivAvatar = itemView.findViewById(R.id.iv_avatar)

            tvName.text = item.name
            ivAvatar.mutiTypeLoad(context, item.avatarUrl)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsrViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return UsrViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsrViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return items.size
    }
}