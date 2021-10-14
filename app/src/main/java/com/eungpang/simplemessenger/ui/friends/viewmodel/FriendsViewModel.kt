package com.eungpang.simplemessenger.ui.friends.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.eungpang.simplemessenger.domain.friend.GetFriendsListUseCase
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    app: Application,
    getFriendsListUseCase: GetFriendsListUseCase,
) : AndroidViewModel(app) {

}