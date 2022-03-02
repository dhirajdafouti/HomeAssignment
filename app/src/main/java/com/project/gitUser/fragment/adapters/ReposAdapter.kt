package com.project.gitUser.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.gitUser.databinding.RepoViewItemBinding

import com.project.gitUser.model.UserData

/**
 * Adapter for the list of repositories.
 */

class ReposAdapter(val onclickListener: RepoAdapterOnClickListener) :
    ListAdapter<UserData, ReposAdapter.ViewHolder>(AsteroidDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData = getItem(position)
        holder.bind(userData!!, onclickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class AsteroidDiffCallback : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder private constructor(val binding: RepoViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            userData: UserData,
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

    class RepoAdapterOnClickListener(val clickListener: (userData: UserData) -> Unit) {
        fun onClick(userData: UserData) = clickListener(userData)
    }
}
