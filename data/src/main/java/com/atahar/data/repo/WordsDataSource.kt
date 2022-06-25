package com.atahar.data.repo

import com.atahar.entities.WordJson

interface WordsDataSource {
    suspend fun getWords(): List<WordJson>
}