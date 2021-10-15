package com.eungpang.simplemessenger.ui.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eungpang.simplemessenger.databinding.ActivityConversationBinding
import com.eungpang.simplemessenger.ui.chat.adapter.ConversationAdapter
import com.eungpang.simplemessenger.ui.chat.viewmodel.ConversationViewModel
import com.eungpang.simplemessenger.ui.extension.hideKeyboard
import com.eungpang.simplemessenger.ui.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationActivity : AppCompatActivity() {
    private val viewModel by viewModels<ConversationViewModel>()

    private lateinit var binding: ActivityConversationBinding
    private lateinit var adapter: ConversationAdapter

    companion object {
        const val INTENT_KEY_LAST_MESSAGE = "lastMessage"
        const val INTENT_KEY_FRIEND_ID = "friendId"

        fun getIntent(context: Context, friendId: String) =
            Intent(context, ConversationActivity::class.java).apply {
                putExtra(INTENT_KEY_FRIEND_ID, friendId)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarChat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        binding.ivSendButtonChat.setOnClickListener {
            val message = binding.etMessageChat.text?.toString()
            if (message.isNullOrBlank()) {
                // If it's empty, do nothing
                return@setOnClickListener
            }

            binding.etMessageChat.setText("")
            hideKeyboard()
            viewModel.onClickSendMessage(message)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            goBack()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        goBack()
    }

    private fun goBack() {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(INTENT_KEY_LAST_MESSAGE, viewModel.lastMessage)
            putExtra(INTENT_KEY_FRIEND_ID, viewModel.friendId)
        })
        finish()
    }
}