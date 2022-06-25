package com.atahar.fallingwords.di

import android.content.Context
import com.atahar.data.repo.WordsDataSource
import com.atahar.data.repo.WordsDataSourceImpl
import com.atahar.data.repo.WordsRepoImpl
import com.atahar.domain.repo.WordsRepo
import com.atahar.domain.usecase.GetWords
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWordsDataSource(@ApplicationContext appContext: Context): WordsDataSource {
        return WordsDataSourceImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideWordsRepo(wordsDataSource: WordsDataSource): WordsRepo {
        return WordsRepoImpl(wordsDataSource)
    }

    @Provides
    @Singleton
    fun provideGetWords(wordsRepo: WordsRepo): GetWords {
        return GetWords(wordsRepo)
    }
}