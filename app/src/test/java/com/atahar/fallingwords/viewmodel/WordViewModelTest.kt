package com.atahar.fallingwords.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.atahar.domain.usecase.GetWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class WordViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    private val getWords = mock<GetWords>()
    private lateinit var wordViewModel: WordViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        wordViewModel = WordViewModel(getWords)
    }

    @Test
    fun `test game states after reset`() = runBlocking {
        wordViewModel.resetGameStates()
        Assert.assertEquals(wordViewModel.currentIndex, 0)
        Assert.assertEquals(wordViewModel.missingAns.value, 0)
        Assert.assertEquals(wordViewModel.correctAns.value, 0)
        Assert.assertEquals(wordViewModel.wrongAns.value, 0)
    }

    @Test
    fun `test get words size zero`() = runBlocking {
        val size = wordViewModel.wordMap.size
        Assert.assertEquals(size, 0)
    }

}