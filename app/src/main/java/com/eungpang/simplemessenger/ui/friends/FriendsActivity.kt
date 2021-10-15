package com.eungpang.simplemessenger.ui.friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eungpang.simplemessenger.R
import com.eungpang.simplemessenger.databinding.ActivityFriendsBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                ConversationActivity.startActivity(
                    this, actionState.friendProfile.userId
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

        binding.toolbarFriends.title = "My friends"
    }
}