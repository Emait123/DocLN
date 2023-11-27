package com.example.docln.plugins

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.SystemFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

//Ds reader setting. Các số tương ứng với index trong ds các settinga.
data class ReaderSetting (
    var backgroundColor: Int,
    var fontColor: Int,
    var fontSize: Int,
    var fontStyle: Int,
    var textAlign: Int
)

data class UserInfo (
    val userID: Int,
    val userName: String
)

class AppDataStore (private val context: Context) {
    private val TAG: String = "Thông báo"

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("appData")

        //DS các key. Tạo object thay vì gõ tay để tránh đánh sai key
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")

        val BG_COLOR = intPreferencesKey("bg_color")
        val FONT_COLOR = intPreferencesKey("font_color")
        val FONT_SIZE = intPreferencesKey("font_size")
        val FONT_STYLE = intPreferencesKey("font_style")
        val TEXT_ALIGN = intPreferencesKey("text_align")
    }

    val readerSettingFlow: Flow<ReaderSetting> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Lỗi đọc file", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences -> mapReaderSetting(preferences) }

    suspend fun updateReaderSettings(bgColor: Int, fontColor: Int, fontSize: Int, fontStyle: Int, textAlign: Int) {
//    suspend fun updateReaderSettings(setting: ReaderSetting) {
//        context.dataStore.edit { preferences -> preferences[BG_COLOR] = setting.backgroundColor }
//        context.dataStore.edit { preferences -> preferences[FONT_COLOR] = setting.fontColor }
//        context.dataStore.edit { preferences -> preferences[FONT_SIZE] = setting.fontSize }
//        context.dataStore.edit { preferences -> preferences[FONT_STYLE] = setting.fontStyle }
//        context.dataStore.edit { preferences -> preferences[TEXT_ALIGN] = setting.textAlign }

        context.dataStore.edit { preferences -> preferences[BG_COLOR] = bgColor }
        context.dataStore.edit { preferences -> preferences[FONT_COLOR] = fontColor }
        context.dataStore.edit { preferences -> preferences[FONT_SIZE] = fontSize }
        context.dataStore.edit { preferences -> preferences[FONT_STYLE] = fontStyle }
        context.dataStore.edit { preferences -> preferences[TEXT_ALIGN] = textAlign }
    }

    suspend fun getReaderSettings() = mapReaderSetting(context.dataStore.data.first().toPreferences())

    private fun mapReaderSetting(preferences: Preferences): ReaderSetting {
        val bgColor = preferences[BG_COLOR] ?: 0
        val fontColor = preferences[FONT_COLOR] ?: 0
        val fontSize = preferences[FONT_SIZE] ?: 18
        val fontStyle = preferences[FONT_STYLE] ?: 0
        val textAlign = preferences[TEXT_ALIGN] ?: 0
        return ReaderSetting(bgColor, fontColor, fontSize, fontStyle, textAlign)
    }

    val userInfoFlow: Flow<UserInfo> = context.dataStore.data
        .catch { exception -> 
            if (exception is IOException) {
                Log.e(TAG, "Lỗi đọc file", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences -> mapUserInfo(preferences) }

    suspend fun updateUserInfo(id: Int, name: String) {
        context.dataStore.edit { preferences -> preferences[USER_ID] = id }
        context.dataStore.edit { preferences -> preferences[USER_NAME] = name }
    }

    suspend fun getUserInfo() = mapUserInfo(context.dataStore.data.first().toPreferences())

    private fun mapUserInfo(preferences: Preferences): UserInfo {
        val id = preferences[USER_ID] ?: -1
        val name = preferences[USER_NAME] ?: "Khách"
        return UserInfo(id, name)
    }
}