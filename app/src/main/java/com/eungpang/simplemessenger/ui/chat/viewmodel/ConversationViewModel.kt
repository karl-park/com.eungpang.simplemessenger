package com.eungpang.simplemessenger.ui.chat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eungpang.simplemessenger.domain.bot.BotType
import com.eungpang.simplemessenger.domain.chat.GetChatHistoryUseCase
import com.eungpang.simplemessenger.domain.chat.Message
import com.eungpang.simplemessenger.domain.chat.SendMessageUseCase
import com.eungpang.simplemessenger.domain.common.Result
import com.eungpang.simplemessenger.domain.friend.GetUserProfileUseCase
import com.eungpang.simplemessenger.domain.friend.Profile
import com.eungpang.simplemessenger.shared.ConstantPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    app: Application,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getChatHistoryUseCase: GetChatHistoryUseCase
) : AndroidViewModel(app) {
    companion object {
        const val DEFAULT_LIMIT = 20
    }

    var currentPage = 0
    private val userId: String
    private lateinit var myProfile: Profile
    private lateinit var friendId: String
    private lateinit var friendProfile: Profile

    private val _messages = MutableLiveData<List<ChatViewItem>>()
    val messages: LiveData<List<ChatViewItem>> = _messages

    init {
        val pref = app.getSharedPreferences(ConstantPref.KEY_PREF_NAME, Application.MODE_PRIVATE)
        userId = pref.getString(ConstantPref.KEY_USER_ID, null)!! // If it's null, should throw exception or redirect user to logout view

        viewModelScope.launch {
            when (val result = getUserProfileUseCase.invoke(userId)) {
                is Result.Success -> {
                    myProfile = result.data
                }
                else -> {
                    // TODO: error, user not found
                }
            }
        }
    }

    fun loadChatHistory(friendId: String) {
        viewModelScope.launch {
            this@ConversationViewModel.friendId = friendId
            val roomId = retrieveRoomId(userId, friendId)
            val result = getChatHistoryUseCase.invoke(
                roomId,
                currentPage,
                DEFAULT_LIMIT
            )

            // Load Friend Profile Once
            if (::friendProfile.isInitialized.not()) {
                val friendProfileResult = getUserProfileUseCase.invoke(friendId)
                if (friendProfileResult !is Result.Success) {
                    // TODO: show alert/toast or retry button to load chat history
                    return@launch
                }
                friendProfile = friendProfileResult.data
            }

            when (result) {
                is Result.Success -> {
                    _messages.postValue(result.data.map {
                        if (it.authorId == userId) {
                            ChatViewItem.MyMessage(myProfile, it)
                        } else {
                            ChatViewItem.FriendMessage(friendProfile, it)
                        }
                    })
                }
                else -> {
                    // TODO: show alert/toast or retry button to load chat history
                }
            }
        }
    }

    fun onClickSendMessage(message: String) {
        sendMessageToFriend(
            Message(
                userId,
                retrieveRoomId(userId, friendProfile.userId),
                message
            )
        )
    }

    private fun sendMessageToFriend(message: Message) {
        viewModelScope.launch {
            when (sendMessageUseCase.invoke(message)) {
                is Result.Success -> {
                    val msg = _messages.value ?: emptyList()
                    val newMessage = mutableListOf<ChatViewItem>().apply {
                        addAll(msg)
                        add(ChatViewItem.MyMessage(myProfile, message))
                    }
                    _messages.postValue(newMessage)
                    handleBotBehavior(message)
                }
                else -> {

                }
            }
        }
    }

    private fun handleBotBehavior(message: Message) {
        if (friendProfile.botType == null) return

        viewModelScope.launch {
            when (friendProfile.botType) {
                BotType.EchoBot -> {
                    when (sendMessageUseCase.invoke(message)) {
                        is Result.Success -> {
                            val msg = _messages.value ?: emptyList()
                            val newMessage = mutableListOf<ChatViewItem>().apply {
                                addAll(msg)
                                add(ChatViewItem.FriendMessage(friendProfile, message))
                            }
                            _messages.postValue(newMessage)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun retrieveRoomId(loggedInId: String, friendId: String) : String {
        // TODO: do refactoring to pass just loggedInId and friendId
        //  RoomId should be calculated from UseCases or Repositories.
        return listOf(loggedInId, friendId).sorted().joinToString("||")
    }
}

sealed class ChatViewItem(
    val profile: Profile,
    val message: Message
) {
    class MyMessage(profile: Profile, message: Message): ChatViewItem(profile, message)
    class FriendMessage(profile: Profile, message: Message): ChatViewItem(profile, message)
}
