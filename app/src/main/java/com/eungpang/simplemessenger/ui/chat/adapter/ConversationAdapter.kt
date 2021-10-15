package com.eungpang.simplemessenger.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.eungpang.simplemessenger.BR
import com.eungpang.simplemessenger.R
import com.eungpang.simplemessenger.databinding.ItemMessageFriendBinding
import com.eungpang.simplemessenger.databinding.ItemMessageMineBinding
import com.eungpang.simplemessenger.ui.chat.viewmodel.ChatViewItem

class ConversationAdapter: RecyclerView.Adapter<ConversationAdapter.MessageViewHolder<ViewDataBinding>>() {
    private val _messages = mutableListOf<ChatViewItem>()
    fun setMessages(messages: List<ChatViewItem>) {
        _messages.run {
            clear()
            addAll(messages)
        }

        // TODO: We can use DiffUtils to optimize the performance
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder<ViewDataBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_message_mine -> {
                val binding = ItemMessageMineBinding.inflate(layoutInflater, parent, false)
                MessageViewHolder.MyMessageViewHolder(binding)
            }
            R.layout.item_message_friend -> {
                val binding = ItemMessageFriendBinding.inflate(layoutInflater, parent, false)
                MessageViewHolder.FriendMessageViewHolder(binding)
            }
            else -> throw RuntimeException("View Type is invalid($viewType)")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder<ViewDataBinding>, position: Int) {
        val item = _messages[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = _messages.size

    override fun getItemViewType(position: Int): Int = when (_messages[position]) {
        is ChatViewItem.MyMessage -> {
            R.layout.item_message_mine
        }
        is ChatViewItem.FriendMessage -> {
            R.layout.item_message_friend
        }
    }

    sealed class MessageViewHolder<out T: ViewDataBinding>(
        private val binding: T
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewItem: ChatViewItem) {
            binding.run {
                setVariable(BR.item, viewItem)
                executePendingBindings()
            }
        }

        class MyMessageViewHolder(
            binding: ItemMessageMineBinding
        ): MessageViewHolder<ItemMessageMineBinding>(binding)

        class FriendMessageViewHolder(
            binding: ItemMessageFriendBinding
        ): MessageViewHolder<ItemMessageFriendBinding>(binding)
    }
}
