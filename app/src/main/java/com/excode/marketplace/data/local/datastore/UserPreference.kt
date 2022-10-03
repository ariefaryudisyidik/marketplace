package com.excode.marketplace.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val getToken: Flow<String> = dataStore.data.map { pref ->
        pref[TOKEN_KEY] ?: ""
    }

    val getLoginStatus: Flow<Boolean> = dataStore.data.map { pref ->
        pref[LOGIN_STATUS_KEY] ?: false
    }

    suspend fun saveLoginStatus(token: String, status: Boolean) {
        dataStore.edit { pref ->
            pref[TOKEN_KEY] = "Bearer $token"
            pref[LOGIN_STATUS_KEY] = status
        }
    }

    suspend fun clearLoginStatus() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token_key")
        private val LOGIN_STATUS_KEY = booleanPreferencesKey("login_status_key")
    }
}

