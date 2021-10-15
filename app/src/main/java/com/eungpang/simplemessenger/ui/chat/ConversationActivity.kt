package com.eungpang.simplemessenger.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eungpang.simplemessenger.databinding.ActivityConversationBinding
import com.eungpang.simplemessenger.ui.chat.adapter.ConversationAdapter
import com.eungpang.simplemessenger.ui.chat.viewmodel.ConversationViewModel
import com.eungpang.simplemessenger.ui.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationActivity : AppCompatActivity() {
    private val viewModel by viewModels<ConversationViewModel>()

    private lateinit var binding: ActivityConversationBinding
    private lateinit var adapter: ConversationAdapter

    companion object {
        private const val INTENT_KEY_FRIEND_ID = "friendId"
        fun startActivity(context: Context, friendId: String) {
            context.startActivity(
                Intent(context, ConversationActivity::class.java).apply {
                    putExtra(INTENT_KEY_FRIEND_ID, friendId)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val friendId = intent.getStringExtra(INTENT_KEY_FRIEND_ID)
        if (friendId == null) {
            showToast("Invalid Friend Information. Please try again.")
            finish()
            return
        }

        initializeViews()

        viewModel.loadChatHistory(friendId)
        viewModel.messages.observe(this) {
            adapter.setMessages(it)
        }
    }

    private fun initializeViews() {
        adapter = ConversationAdapter()

        binding.rvChat.run {
            layoutManager = LinearLayoutManager(this@ConversationActivity, LinearLayoutManager.VERTICAL, false)
            adapter = this@ConversationActivity.adapter
        }

        binding.toolbarChat.title = "Chat"
    }
}