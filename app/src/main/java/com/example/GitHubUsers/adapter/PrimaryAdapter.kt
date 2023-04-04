package com.example.GitHubUsers.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.GitHubUsers.ItemsItem
import com.example.GitHubUsers.R
import com.example.GitHubUsers.detail.DetailUser
import de.hdodenhof.circleimageview.CircleImageView

class PrimaryAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<PrimaryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        holder.tvUser.text = user.login
        Glide.with(holder.itemView)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.tvImage)

        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailUser::class.java)
            intentDetail.putExtra(DetailUser.EXTRA_USER, user.login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUser: TextView = view.findViewById(R.id.tv_user)
        val tvImage: CircleImageView = view.findViewById(R.id.Image)
    }

}