package com.atahar.domain.usecase

import com.atahar.domain.repo.WordsRepo

class GetWords(private val wordsRepo: WordsRepo) {
    suspend operator fun invoke() = wordsRepo.getWords()
}

