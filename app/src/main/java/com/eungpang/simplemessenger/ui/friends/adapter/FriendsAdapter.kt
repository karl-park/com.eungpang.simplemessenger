package com.eungpang.simplemessenger.ui.friends.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eungpang.simplemessenger.databinding.ItemFriendsBinding
import com.eungpang.simplemessenger.ui.friends.viewmodel.FriendViewItem

class FriendsAdapter: RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {
    private val _friends = mutableListOf<FriendViewItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setFriends(friends: List<FriendViewItem>) {
        _friends.run {
            clear()
            addAll(friends)
        }

        // Note: We can use DiffUtils to optimize the performance
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendsBinding.inflate(layoutInflater, parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val item = _friends[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = _friends.size

    class FriendViewHolder(
        private val binding: ItemFriendsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(viewItem: FriendViewItem) {
            binding.item = viewItem
            binding.executePendingBindings()
        }
    }
}
