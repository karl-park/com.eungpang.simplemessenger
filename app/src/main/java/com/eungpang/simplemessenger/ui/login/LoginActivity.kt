package com.eungpang.simplemessenger.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.eungpang.simplemessenger.R
import com.eungpang.simplemessenger.databinding.ActivityFriendsBinding
import com.eungpang.simplemessenger.databinding.ActivityLoginBinding
import com.eungpang.simplemessenger.ui.chat.ConversationActivity
import com.eungpang.simplemessenger.ui.extension.showToast
import com.eungpang.simplemessenger.ui.friends.FriendsActivity
import com.eungpang.simplemessenger.ui.friends.viewmodel.FriendsViewModel
import com.eungpang.simplemessenger.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()

        viewModel.actionState.observe(this, ::handleActionState)
    }

    private fun handleActionState(actionState: LoginViewModel.ActionState) {
        when (actionState) {
            is LoginViewModel.ActionState.GoToFriendListView -> {
                showToast("Welcome ${actionState.myProfile.name}\uD83C\uDF89!")
                startActivity(Intent(this, FriendsActivity::class.java))
                finish()
            }
            is LoginViewModel.ActionState.ShowToastMessage -> {
                showToast(actionState.message)
            }
        }
    }

    private fun initializeViews() {
        binding.btnLogin.setOnClickListener {
            val name = binding.etNameLogin.text?.toString()
            if (name.isNullOrEmpty()) return@setOnClickListener

            viewModel.registerWithName(name)
        }
    }
}
