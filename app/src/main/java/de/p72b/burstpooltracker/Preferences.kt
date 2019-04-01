package de.p72b.burstpooltracker

import android.content.Context
import android.content.SharedPreferences

class Preferences {
    companion object {
        const val OPT_OUT_CRASHLYTICS = "OPT_OUT_CRASHLYTICS"
        const val OPT_OUT_ANALYTICS = "OPT_OUT_ANALYTICS"

        private const val SHARED_PREFS_FILE = "burstpooltracker"
        private val preferences: SharedPreferences = App.sInstance.getSharedPreferences(
            SHARED_PREFS_FILE, Context.MODE_PRIVATE)

        fun writeToPreferences(key: String, value: String) {
            preferences.edit().putString(key, value).apply()
        }

        fun readStringFomPreferences(key: String): String? {
            return preferences.getString(key, null)
        }

        fun writeToPreferences(key: String, value: Boolean) {
            preferences.edit().putBoolean(key, value).apply()
        }

        fun readBooleanFromPreferences(key: String): Boolean {
            return preferences.getBoolean(key, false)
        }
    }
}
