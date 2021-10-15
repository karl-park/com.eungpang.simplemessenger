package com.eungpang.simplemessenger.ui.login.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.eungpang.simplemessenger.domain.bot.BotType
import com.eungpang.simplemessenger.domain.common.Result
import com.eungpang.simplemessenger.domain.friend.AddUserProfileUseCase
import com.eungpang.simplemessenger.domain.friend.Profile
import com.eungpang.simplemessenger.shared.ConstantPref
import com.eungpang.simplemessenger.shared.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val app: Application,
    private val addUserProfileUseCase: AddUserProfileUseCase,
) : AndroidViewModel(app) {
    val actionState = SingleLiveEvent<ActionState>()
    sealed class ActionState {
        class GoToFriendListView(
            val myProfile: Profile
        ): ActionState()

        class ShowToastMessage(
            val message: String
        ): ActionState()
    }

    fun registerWithName(name: String) {
        viewModelScope.launch {
            if (isValidName(name).not()) {
                actionState.postValue(ActionState.ShowToastMessage("Name is too long."))
                return@launch
            }

            val myProfile = Profile(
                generateUUID(),
                name,
                "file:///android_asset/chope.png",
                null,
                null,
                Date()
            )
            when (addUserProfileUseCase.invoke(myProfile)) {
                is Result.Success -> {
                    registerUserToSharedPreference(myProfile.userId)
                    registerDefaultBots(myProfile.userId)
                    actionState.postValue(ActionState.GoToFriendListView(myProfile))
                }
                else -> {
                    actionState.postValue(ActionState.ShowToastMessage("Sorry, try again."))
                }
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    private fun registerUserToSharedPreference(userId: String) {
        val pref = app.getSharedPreferences(ConstantPref.KEY_PREF_NAME, Application.MODE_PRIVATE)
        pref.edit()
            .putString(ConstantPref.KEY_USER_ID, userId)
            .commit()
    }

    private suspend fun registerDefaultBots(userId: String) {
        val profiles = listOf(
            Profile(
                "${userId}-echobot-1",
                "Jone",
                "https://www.jenkins.io/images/logos/snow/snow.png",
                null,
                "echobot1@eungpang.com",
                Date(1634225659469L),
                BotType.EchoBot
            ),
            Profile(
                "${userId}-echobot-2",
                "Kent",
                "https://www.jenkins.io/images/logos/cute/cute.png",
                null,
                "echobot2@eungpang.com",
                Date(1632025656469L),
                BotType.EchoBot
            )
        )
        addUserProfileUseCase.invoke(profiles)
    }

    private fun isValidName(name: String) : Boolean {
        if (name.length > 10) return false

        return true
    }

    private fun generateUUID() : String {
        return UUID.randomUUID().toString()
    }
}