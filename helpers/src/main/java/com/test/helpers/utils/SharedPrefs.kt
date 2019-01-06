package com.test.helpers.utils

import android.content.Context
import javax.inject.Inject
import android.R.id.edit
import android.content.SharedPreferences
import android.preference.PreferenceManager



class SharedPrefs(val mContext: Context) {

    var mAppPrefs = mContext.getSharedPreferences(mContext.packageName, Context.MODE_PRIVATE)
    var mEditor: SharedPreferences.Editor = mAppPrefs.edit()


    fun save(key: String, value: Any?) {
        when(value) {
            is Int -> {
                mEditor.putInt(key, value)
            }

            is String -> {
                mEditor.putString(key, value)
            }

            is Boolean -> {
                mEditor.putBoolean(key, value)
            }

            is Long -> {
                mEditor.putLong(key, value)
            }
        }

        mEditor.apply()
    }

    fun getInt(key: String): Int {
        return mAppPrefs.getInt(key, 0)
    }

    fun getString(key: String): String? {
        return mAppPrefs.getString(key, "")
    }

    fun getBoolean(key: String): Boolean {
        return mAppPrefs.getBoolean(key, false)
    }

    fun getLong(key: String): Long {
        return mAppPrefs.getLong(key, 0)
    }
}