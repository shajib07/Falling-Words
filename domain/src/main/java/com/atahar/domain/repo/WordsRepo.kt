package com.atahar.domain.repo

import com.atahar.entities.WordJson

interface WordsRepo {
    suspend fun getWords(): List<WordJson>
}