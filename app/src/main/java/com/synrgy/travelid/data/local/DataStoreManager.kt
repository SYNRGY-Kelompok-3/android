package com.synrgy.travelid.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val context: Context) {
    companion object {
        private const val PREF_NAME = "User"
        val TOKEN_KEY = stringPreferencesKey("TOKEN")

        val Context.dataStoreAuth: DataStore<Preferences> by preferencesDataStore(
            name = PREF_NAME,
            produceMigrations = Companion::sharedPreferencesMigration
        )

        private fun sharedPreferencesMigration(context: Context) =
            listOf(SharedPreferencesMigration(context, PREF_NAME))
    }

    fun getToken(): Flow<String> {
        return context.dataStoreAuth.data.map { preferences ->
            preferences[TOKEN_KEY].orEmpty()
        }
    }

    suspend fun setToken(value: String) {
        context.dataStoreAuth.edit { preferences ->
            preferences[TOKEN_KEY] = value
        }
    }
}