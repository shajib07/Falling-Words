package com.atahar.fallingwords.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atahar.domain.usecase.GetWords
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

@HiltViewModel
class WordViewModel @Inject constructor(
    private val getWords: GetWords
) : ViewModel() {

    private var _correctAns = MutableLiveData(0)
    val correctAns: LiveData<Int>
        get() = _correctAns

    private var _wrongAns = MutableLiveData(0)
    val wrongAns: LiveData<Int>
        get() = _wrongAns

    private var _missingAns = MutableLiveData(0)
    val missingAns: LiveData<Int>
        get() = _missingAns

    private var _timerCount = MutableLiveData(5)
    val timerCount: LiveData<Int>
        get() = _timerCount

    private var _answerGiven = MutableLiveData(false)
    val answerGiven: LiveData<Boolean>
        get() = _answerGiven

    private var _isGameFinish = MutableLiveData(false)
    val isGameFinish: LiveData<Boolean>
        get() = _isGameFinish

    var currentIndex: Int = 0
    private var isShownCorrectTrans: Boolean = false

    private val _inputText = MutableLiveData<String>()
    val inputText: LiveData<String>
        get() = _inputText

    private val _translateText = MutableLiveData<String>()
    val translateText: LiveData<String>
        get() = _translateText

    var wordMap = HashMap<String, String>()

    init {
        getWords()
    }

    fun getWords() {
        viewModelScope.launch {
            val data = getWords.invoke()
            if (data.isEmpty()) _isGameFinish.value = true
            wordMap = data.associate { it.text_eng to it.text_spa } as HashMap<String, String>
        }
    }

    fun onWrongAnsClicked() {
        updateAnswer(false)
    }

    fun onCorrectAnsClicked() {
        updateAnswer(true)
    }

    private fun updateAnswer(isCorrect: Boolean) {
        if (_answerGiven.value == true)
            return

        if (isShownCorrectTrans != isCorrect) {
            _wrongAns.value = _wrongAns.value?.plus(1)
        } else {
            _correctAns.value = _correctAns.value?.plus(1)
        }

        _answerGiven.value = true

    }


    private fun getInputTextByIndex(idx: Int) = wordMap.keys.elementAt(idx)

    private fun getPossibleTranslateText(): String {
        isShownCorrectTrans = Random.nextBoolean()

        if (wordMap.size == 1) {
            isShownCorrectTrans = true
        }

        return if (isShownCorrectTrans) {
            wordMap.getValue(getInputTextByIndex(currentIndex))
        } else {
            var arbitraryIdx = abs((wordMap.size - 1) - currentIndex)
            if (arbitraryIdx == currentIndex) {

                if (currentIndex == wordMap.size - 1)
                    arbitraryIdx--
                else
                    arbitraryIdx++
            }
            wordMap.getValue(getInputTextByIndex(arbitraryIdx))
        }
    }

    fun updateParams() {
        if (currentIndex >= wordMap.size) {
            _isGameFinish.value = true
            return
        }

        _inputText.value = getInputTextByIndex(currentIndex)
        _translateText.value = getPossibleTranslateText()

        currentIndex++
        _timerCount.value = 5
        _answerGiven.value = false
    }

    fun updateMissingCount() {
        if (answerGiven.value != true) {
            _missingAns.value = _missingAns.value?.plus(1)
        }
    }

    fun updateTimerCount(count: Int) {
        _timerCount.value = count
    }


    fun resetGameStates() {
        currentIndex = 0
        _timerCount.value = 0
        _answerGiven.value = false
        _missingAns.value = 0
        _correctAns.value = 0
        _wrongAns.value = 0
        _isGameFinish.value = false
    }

}