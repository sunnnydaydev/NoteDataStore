package com.example.notedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Region")

object DataStore {

    /**
     * get a string from DataStore
     * */
    fun readString(context: Context, key: String): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[stringPreferencesKey(key)]?:""
            }
    }

    /**
     * write a string to DataStore
     * */
    suspend fun writeString(context: Context, key: String, value: String) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value
        }
    }
}




