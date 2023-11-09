package com.team2127.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class CookieDataSource @Inject constructor(
    private val context: Context
) {

    val Context.cookieDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "com.team2127.meetu.cookieInfo"
    )

    private companion object{
        val KEY_COOKIE = stringPreferencesKey(name = "cookie")
    }

    suspend fun setCookie(cookie: String){
        context.cookieDataStore.edit {
            it[KEY_COOKIE] = cookie
        }
    }

    suspend fun getCookie(): String? {
        val flow = context.cookieDataStore.data
            .catch { exception ->
                if (exception is IOException){
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {
                it[KEY_COOKIE]
            }
        return flow.firstOrNull()
    }
}
