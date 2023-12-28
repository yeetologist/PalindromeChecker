package com.github.yeetologist.palindromechecker.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, "")
    }

    fun setUsername(username: String) {
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun getSelected(): String? {
        return sharedPreferences.getString(KEY_SELECTED_USER, "")
    }

    fun setSelected(email: String) {
        editor.putString(KEY_SELECTED_USER, email)
        editor.apply()
    }

    // Add more getter and setter methods for other preferences

    fun clearPreferences() {
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val PREF_NAME = "preference"
        private const val KEY_USERNAME = "username"
        private const val KEY_SELECTED_USER = "selected_user"
        // Add more keys as needed
    }
}