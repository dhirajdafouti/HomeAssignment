package com.project.gitUser.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.gitUser.databinding.RepoViewItemBinding

import com.project.gitUser.model.UserFollower

/**
 * Adapter for the list of repositories.
 */

class ReposAdapter(val onclickListener: RepoAdapterOnClickListener) :
    ListAdapter<UserFollower, ReposAdapter.ViewHolder>(UserFollowersDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData = getItem(position)
        holder.bind(userData!!, onclickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class UserFollowersDiffCallback : DiffUtil.ItemCallback<UserFollower>() {
        override fun areItemsTheSame(oldItem: UserFollower, newItem: UserFollower): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserFollower, newItem: UserFollower): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder private constructor(val binding: RepoViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            userData: UserFollower,
            clickOnClickListener: RepoAdapterOnClickListener
        ) {
            binding.userData = userData
            binding.clickListener = clickOnClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RepoViewItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class RepoAdapterOnClickListener(val clickListener: (userData: UserFollower) -> Unit) {
        fun onClick(userData: UserFollower) = clickListener(userData)
    }
}
