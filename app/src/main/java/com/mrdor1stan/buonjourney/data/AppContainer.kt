package com.mrdor1stan.buonjourney.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.mrdor1stan.buonjourney.data.db.BuonjourneyDatabase

const val USER_PREFERENCES_NAME = "user_preferences_name"

class AppContainer(
    context: Context,
) {
    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME,
    )
    val databaseRepository: DatabaseRepository by lazy {
        DatabaseRepository(BuonjourneyDatabase.getDatabase(context))
    }
    val userRepository: UserRepository by lazy {
        UserRepository(context.dataStore)
    }
}
