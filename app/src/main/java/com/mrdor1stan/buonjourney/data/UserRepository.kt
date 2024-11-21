package com.mrdor1stan.buonjourney.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserRepository(
    private val dataStore: DataStore<Preferences>,
) {
    private companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        const val TAG = "UserRepository"
    }

    suspend fun logIn(
        name: String
    ) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = name
            preferences[IS_LOGGED_IN] = true
        }
    }

    suspend fun logOut() {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = ""
            preferences[IS_LOGGED_IN] = false
        }
    }

    private val dataStoreFlow =
        dataStore.data.catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }

    val name: Flow<String> =
        dataStoreFlow
            .map { preferences ->
                preferences[USER_NAME] ?: ""
            }

    val isLoggedIn: Flow<Boolean> =
        dataStoreFlow
            .map { preferences ->
                preferences[IS_LOGGED_IN] ?: false
            }
}