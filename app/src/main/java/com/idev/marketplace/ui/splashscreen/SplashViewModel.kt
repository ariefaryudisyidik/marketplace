package com.idev.entreumart.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.idev.entreumart.data.local.datastore.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    pref: UserPreference
) : ViewModel() {

    val loginStatus = pref.getLoginStatus.asLiveData()
}