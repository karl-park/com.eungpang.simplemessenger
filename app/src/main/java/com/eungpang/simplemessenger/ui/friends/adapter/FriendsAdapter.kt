package com.eungpang.simplemessenger.ui.friends.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eungpang.simplemessenger.databinding.ItemFriendsBinding
import com.eungpang.simplemessenger.domain.friend.Profile

// TODO: need to pass event handler(or it can be inside of ViewData class)
class FriendsAdapter(

): RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {
    private val _friends = mutableListOf<Profile>()
    fun setFriends(friends: List<Profile>) {
        _friends.run {
            clear()
            addAll(friends)
        }

        // TODO: We can use DiffUtils to optimize the performance
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
        fun bind(profile: Profile) {
            // TODO: bind it (data + events)
            binding.item = profile
            binding.executePendingBindings()
        }
    }
}