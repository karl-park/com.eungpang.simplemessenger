package com.eungpang.simplemessenger.ui.friends.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eungpang.simplemessenger.domain.common.Result
import com.eungpang.simplemessenger.domain.friend.GetFriendsListUseCase
import com.eungpang.simplemessenger.domain.friend.Profile
import com.eungpang.simplemessenger.shared.ConstantPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    app: Application,
    private val getFriendsListUseCase: GetFriendsListUseCase,
) : AndroidViewModel(app) {
    private val userId: String

    private val _friends = MutableLiveData<List<Profile>>()
    val friends: LiveData<List<Profile>> = _friends

    init {
        val pref = app.getSharedPreferences(ConstantPref.KEY_PREF_NAME, Application.MODE_PRIVATE)
        userId = pref.getString(ConstantPref.KEY_USER_ID, null)!! // If it's null, should throw exception or redirect user to logout view

        viewModelScope.launch {
            val result = getFriendsListUseCase.invoke(userId)
            when (result) {
                is Result.Success -> {
                    _friends.postValue(result.data ?: listOf())
                }
                is Result.Loading -> {

                }
                is Result.Error -> {

                }
            }
        }
    }
}
