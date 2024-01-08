package com.febi.projek.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager (
    private val context: Context,
){
    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private const val PREF_NAME = "User"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = PREF_NAME
        )
    }

    suspend fun saveToken(token: String){
        context.dataStore.edit{settings -> settings[KEY_TOKEN]}
    }

    suspend fun saveEmail(username: String) {
        context.dataStore.edit { settings -> settings[KEY_EMAIL] = username }
    }

    fun getToken(): Flow<String> {
        return context.dataStore.data.map { settings ->
            settings[KEY_TOKEN].orEmpty()
        }
    }

    suspend fun setToken(value: String) {
        context.dataStore.edit { settings ->
            settings [KEY_TOKEN] = value
        }
    }
}