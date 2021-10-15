package com.eungpang.simplemessenger.ui.friends

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eungpang.simplemessenger.R
import com.eungpang.simplemessenger.databinding.ActivityFriendsBinding
import com.eungpang.simplemessenger.domain.chat.Message
import com.eungpang.simplemessenger.ui.chat.ConversationActivity
import com.eungpang.simplemessenger.ui.extension.showToast
import com.eungpang.simplemessenger.ui.friends.adapter.FriendsAdapter
import com.eungpang.simplemessenger.ui.friends.viewmodel.FriendViewItem
import com.eungpang.simplemessenger.ui.friends.viewmodel.FriendsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FriendsActivity : AppCompatActivity() {
    private val viewModel by viewModels<FriendsViewModel>()

    private lateinit var binding: ActivityFriendsBinding
    private lateinit var adapter: FriendsAdapter

    private val getLastUpdatedMessage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val lastMessage = it.data?.getParcelableExtra<Message>(ConversationActivity.INTENT_KEY_LAST_MESSAGE)
            val friendId = it.data?.getStringExtra(ConversationActivity.INTENT_KEY_FRIEND_ID)
            if (lastMessage != null && friendId != null) {
                viewModel.updateLastMessage(friendId, lastMessage)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarFriends)

        initializeViews()
        viewModel.friends.observe(this, ::handleFriendsList)
        viewModel.actionState.observe(this, ::handleActionState)
    }

    private fun handleFriendsList(friends: List<FriendViewItem>) {
        // TODO: if it's empty, please show empty view
        adapter.setFriends(friends)
    }

    private fun handleActionState(actionState: FriendsViewModel.ActionState) {
        when (actionState) {
            is FriendsViewModel.ActionState.GoToConversationView -> {
                getLastUpdatedMessage.launch(
                    ConversationActivity.getIntent(this, actionState.friendProfile.userId, actionState.friendProfile.name)
                )
            }
            is FriendsViewModel.ActionState.ShowToastMessage -> {
                showToast(actionState.message)
            }
        }
    }

    private fun initializeViews() {
        adapter = FriendsAdapter()

        binding.rvFriends.run {
            layoutManager = LinearLayoutManager(this@FriendsActivity, LinearLayoutManager.VERTICAL, false)
            adapter = this@FriendsActivity.adapter
            setHasFixedSize(true)
        }

        binding.fabFriends.setOnClickListener {
            val et = EditText(this)

            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin =resources.getDimensionPixelSize(R.dimen.spacing_major)
                rightMargin =resources.getDimensionPixelSize(R.dimen.spacing_major)
            }
            et.layoutParams = params

            val container = FrameLayout(this)
            container.addView(et)

            AlertDialog.Builder(this)
                .setTitle("Add New EchoBot's Name")
                .setMessage("He says what you say")
                .setIcon(R.drawable.ic_android_purple_24)
                .setCancelable(false)
                .setView(container)
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val botName = et.text.toString()
                    if (viewModel.isValidName(botName).not()) {
                        showToast("It's too long")
                        return@setPositiveButton
                    }
                    viewModel.addNewBot(botName)
                }
                .show()
        }

        supportActionBar?.title = "My friends"
    }
}