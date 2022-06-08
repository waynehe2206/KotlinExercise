package com.example.recyclerexercise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.recyclerexercise.data.model.GithubUser
import com.example.recyclerexercise.databinding.ItemGithubUsersBinding
import com.example.recyclerexercise.ui.fragment.MainFragmentDirections

class GithubUserListAdapter : ListAdapter<GithubUser, GithubUserListAdapter.UserViewHolder>(object :
    DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(
        oldItem: GithubUser,
        newItem: GithubUser
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: GithubUser,
        newItem: GithubUser
    ): Boolean {
        return oldItem == newItem
    }
    }
) {
    class UserViewHolder(val binding: ItemGithubUsersBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindGithubUserData(binding: ItemGithubUsersBinding, data: GithubUser){
            binding.tvUserName.text = data.login
            binding.ivUserThumbnail.apply {
                Glide.with(itemView)
                    .load(data.avatar_url)
                    .transform(CenterCrop())
                    .into(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemGithubUsersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bindGithubUserData(holder.binding, data)
            holder.binding.root.run{
                setOnClickListener {
                    val action = MainFragmentDirections.actionMainFragmentToUserDetailFragment(userData = data)
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}