package com.example.notedatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data")

object DataStore {

    /**
     * get a string from DataStore
     * */
    fun readString(context: Context, key: String): Flow<String> {
        return context.dataStore.data
            .map { preferences  -> //preferences是Preferences类型
                preferences[stringPreferencesKey(key)]?:""//这里不是类型安全的
            }
    }

    /**
     * write a string to DataStore
     * */
    suspend fun writeString(context: Context, key: String, value: String) {
        context.dataStore.edit { settings -> // settings是MutablePreferences类型
            settings[stringPreferencesKey(key)] = value
        }
    }
}




