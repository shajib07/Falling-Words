package com.atahar.data.repo

import com.atahar.domain.repo.WordsRepo

class WordsRepoImpl(
    private val wordsDataSource: WordsDataSource
) : WordsRepo {

    override suspend fun getWords() = wordsDataSource.getWords()
}