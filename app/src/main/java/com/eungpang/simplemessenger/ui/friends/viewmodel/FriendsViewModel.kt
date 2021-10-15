package com.eungpang.simplemessenger.ui.friends.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eungpang.simplemessenger.domain.chat.GetChatHistoryUseCase
import com.eungpang.simplemessenger.domain.chat.Message
import com.eungpang.simplemessenger.domain.common.Result
import com.eungpang.simplemessenger.domain.friend.AddUserProfileUseCase
import com.eungpang.simplemessenger.domain.friend.GetFriendsListUseCase
import com.eungpang.simplemessenger.domain.friend.Profile
import com.eungpang.simplemessenger.shared.ConstantPref
import com.eungpang.simplemessenger.shared.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    app: Application,
    private val getFriendsListUseCase: GetFriendsListUseCase,
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val addUserProfileUseCase: AddUserProfileUseCase,
) : AndroidViewModel(app) {
    private val userId: String

    private val _friends = MutableLiveData<List<FriendViewItem>>()
    val friends: LiveData<List<FriendViewItem>> = _friends

    val actionState = SingleLiveEvent<ActionState>()
    sealed class ActionState {
        class GoToConversationView(
            val friendProfile: Profile
        ): ActionState()

        class ShowToastMessage(
            val message: String
        ): ActionState()
    }

    init {
        val pref = app.getSharedPreferences(ConstantPref.KEY_PREF_NAME, Application.MODE_PRIVATE)
        userId = pref.getString(ConstantPref.KEY_USER_ID, "")!! // If it's null, should throw exception or redirect user to logout view

        viewModelScope.launch {
            when (val result = getFriendsListUseCase.invoke(userId)) {
                is Result.Success -> {
                    _friends.postValue(
                        result.data.map {
                            val roomId = retrieveRoomId(userId, it.userId)
                            val getLastChatHistoryResult = getChatHistoryUseCase.invoke(
                                roomId,
                                0,
                                1
                            )
                            val lastMessage = when (getLastChatHistoryResult) {
                                is Result.Success -> {
                                    getLastChatHistoryResult.data.lastOrNull()
                                }
                                else -> null
                            }

                            FriendViewItem(it, lastMessage, ::onClickFriendViewItem)
                        }
                    )
                }
                is Result.Loading -> {

                }
                is Result.Error -> {

                }
            }
        }
    }

    fun updateLastMessage(friendId: String, message: Message) {
        val friendList = _friends.value ?: return
        _friends.value = friendList.map {
            if (it.user.userId == friendId) {
                it.copy(lastMessage = message)
            } else it
        }
    }

    fun isValidName(name: String) : Boolean {
        if (name.length > 10) return false

        return true
    }

    fun addNewBot(name: String) {
        viewModelScope.launch {
            val botProfile = Profile(
                "${userId}-${name}",
                name,
                BOT_AVATAR_URL_LIST.random(),
                null,
                null,
                Date()
            )
            when (val result = addUserProfileUseCase.invoke(botProfile)) {
                is Result.Success -> {
                    val friendList = _friends.value ?: emptyList()
                    _friends.postValue(friendList.toMutableList().apply {
                        add(FriendViewItem(botProfile, null, ::onClickFriendViewItem))
                    })
                }
                else -> {
                    actionState.postValue(ActionState.ShowToastMessage("Sorry, try again."))
                }
            }
        }
    }

    private fun onClickFriendViewItem(profile: Profile) {
        actionState.postValue(ActionState.GoToConversationView(profile))
    }

    private fun retrieveRoomId(loggedInId: String, friendId: String) : String {
        // TODO: do refactoring to pass just loggedInId and friendId
        //  RoomId should be calculated from UseCases or Repositories.
        return listOf(loggedInId, friendId).sorted().joinToString("||")
    }

    companion object {
        val BOT_AVATAR_URL_LIST = listOf(
            "https://www.jenkins.io/images/logos/JCasC/JCasC.png",
            "https://www.jenkins.io/images/logos/actor/actor.png",
            "https://www.jenkins.io/images/logos/alien/alien.png",
            "https://www.jenkins.io/images/logos/amsterdam/amsterdam.png",
            "https://www.jenkins.io/images/logos/atomium/atomium.png",
            "https://www.jenkins.io/images/logos/austin/austin.png",
            "https://www.jenkins.io/images/logos/automotive/automotive.png",
            "https://www.jenkins.io/images/logos/balloon/balloon.png",
            "https://www.jenkins.io/images/logos/barcelona/barcelona.png",
            "https://www.jenkins.io/images/logos/boston/boston.png",
            "https://www.jenkins.io/images/logos/brno/brno.png",
            "https://www.jenkins.io/images/logos/cambridge/cambridge.png",
            "https://www.jenkins.io/images/logos/clown/clown.png",
            "https://www.jenkins.io/images/logos/cute/cute.png",
            "https://www.jenkins.io/images/logos/evergreen/evergreen.png",
        )
    }
}

data class FriendViewItem(
    val user: Profile,
    val lastMessage: Message?,
    val onAction: (Profile) -> (Unit)
)
