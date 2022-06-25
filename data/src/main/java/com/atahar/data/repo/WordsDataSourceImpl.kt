package com.atahar.data.repo

import android.content.Context
import android.util.Log
import com.atahar.entities.WordJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


class WordsDataSourceImpl(private val context: Context) : WordsDataSource {

    override suspend fun getWords() : List<WordJson> {
        return withContext(Dispatchers.IO) {

            lateinit var jsonString: String
            try {
                jsonString = context.assets.open("words.json")
                    .bufferedReader()
                    .use { it.readText() }
            } catch (ioException: IOException) {
                Log.d("askl", ioException.message ?: "")
            }

            val listCountryType = object : TypeToken<List<WordJson>>() {}.type
            Gson().fromJson(jsonString, listCountryType)
        }
    }
}