package com.application.beautify.common

import android.content.Context
import android.content.SharedPreferences
import com.application.beautify.common.Constant
import com.application.beautify.model.Beautify
import com.application.beautify.model.FullBeautify
import com.application.beautify.model.User
import com.google.gson.Gson

class StoredValue(private val context: Context) {

    private val prefsName = "BEAUTIFY"

    fun save(PREFS_KEY: String, text: String) {
        val settings: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()
        editor.putString(PREFS_KEY, text)
        editor.apply()
    }

    fun saveBool(PREFS_KEY: String, boolean: Boolean) {
        val settings: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()
        editor.putBoolean(PREFS_KEY, boolean)
        editor.apply()
    }

    fun getValue(PREFS_KEY: String): String {
        val settings: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val text: String
        text = settings.getString(PREFS_KEY, "")!!
        return text
    }

    fun getBool(PREFS_KEY: String): Boolean {
        val settings: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        return settings.getBoolean(PREFS_KEY, false)
    }

    fun removeValue(PREFS_KEY: String) {
        val settings: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()
        editor.remove(PREFS_KEY)
        editor.apply()
    }

    fun saveObject(PREFS_KEY: String, `object`: Any) {
        val settings: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()
        val gson = Gson()
        editor.putString(PREFS_KEY, gson.toJson(`object`))
        editor.apply()
    }

    fun getObject(PREFS_KEY: String): Any? {
        val settings: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val text: String
        val gson = Gson()
        text = settings.getString(PREFS_KEY, null)!!
        return when (PREFS_KEY) {
            Constant().PREFS_USER_KEY -> gson.fromJson<User>(text, User::class.java)
            Constant().PREFS_ITEM_KEY -> gson.fromJson<FullBeautify>(text, FullBeautify::class.java)
            Constant().PREFS_BEAUTIFY_KEY -> gson.fromJson<Beautify>(text, Beautify::class.java)
            Constant().PREFS_USERS_KEY -> gson.fromJson<User>(text, User::class.java)
            else -> null
        }
    }
}