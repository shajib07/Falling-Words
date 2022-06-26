package com.atahar.fallingwords.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.atahar.fallingwords.R
import com.atahar.fallingwords.databinding.FragmentGameBinding
import com.atahar.fallingwords.viewmodel.WordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.ceil


@AndroidEntryPoint
class GameFragment : Fragment() {

    private val sharedViewModel: WordViewModel by activityViewModels()
    private var binding: FragmentGameBinding? = null
    private var singleLoopTimer: CountDownTimer? = null
    private lateinit var fallingWordAnimator: ObjectAnimator
    private lateinit var fallingText: TextView

    var containerH = 0
    var containerW = 0
    var wordW = 0
    var wordH = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentGameBinding.inflate(inflater)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            gameFragment = this@GameFragment
        }

        // Reset all the params related to Game State before starting the game
        sharedViewModel.resetGameStates()

        fallingText = binding?.translateText!!

        // Start the game loop as soon as the global layout happened
        fallingText.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (fallingText.isShown) {
                        fallingText.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        gameSetup()
                        gameLoop()
                    }
                }
            })


        sharedViewModel.answerGiven.observe(viewLifecycleOwner, Observer { isAnswerGiven ->
            if (isAnswerGiven) {
                fallingWordAnimator.end()
            }
        })

        sharedViewModel.isGameFinish.observe(viewLifecycleOwner, Observer { gameFinish ->
            if (gameFinish) {
                finishGame()
                openScoreFragment()
            }
        })

        singleLoopTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                sharedViewModel.updateTimerCount(
                    ceil((millisUntilFinished / 1000).toDouble()).toInt()
                )
            }
            override fun onFinish() {
                sharedViewModel.updateTimerCount(0)
            }
        }
    }

    private fun setupFallingText() {
        val container = fallingText.parent as ViewGroup

        if (container.width < container.height) {
            containerW = container.width
            containerH = container.height
        } else {
            containerW = container.height
            containerH = container.width
        }

        wordW = fallingText.width
        wordH = fallingText.height

        fallingText.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun updateFallingTextXPos() {
        // Position the view at a random place between the left and right edges of the game screen
        var width = Math.random().toFloat() * containerW

        if (width + wordW > containerW) {
            width -= ((width + wordW) - containerW)
        }
        fallingText.translationX = width
    }

    // Update the game state variables
    private fun updateParams() {
        sharedViewModel.updateParams()
        updateFallingTextXPos()
    }

    private fun setupAnimation() {
        fallingWordAnimator = ObjectAnimator.ofFloat(
            fallingText,
            View.TRANSLATION_Y,
            -wordH.toFloat(),
            containerH + wordH.toFloat()
        )

        fallingWordAnimator.interpolator = AccelerateInterpolator(1f)
        fallingWordAnimator.duration = 5000

        fallingWordAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationCancel(animation: Animator?) {
                super.onAnimationCancel(animation)
                animation?.removeAllListeners()
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                sharedViewModel.updateMissingCount()
                if (sharedViewModel.isGameFinish.value == false)
                    gameLoop()
            }
        })
    }

    private fun gameSetup() {
        setupFallingText()
    }

    // This method manages the game loop for every new words by updating states and timer
    private fun gameLoop() {
        updateParams()
        setupAnimation()
        singleLoopTimer?.start()
        fallingWordAnimator.start()
    }

    // Cancel the game when user stopped playing or game ends
    private fun cancelAnimation() {
        if (this@GameFragment::fallingWordAnimator.isInitialized)
            fallingWordAnimator.cancel()
    }

    private fun cancelTimer() {
        singleLoopTimer?.cancel()
        singleLoopTimer = null
    }

    private fun finishGame() {
        cancelAnimation()
        cancelTimer()
    }

    fun openScoreFragment() = findNavController().navigate(
        R.id.action_gameFragment_to_scoreFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finishGame()
                    openScoreFragment()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        finishGame()
        binding = null
    }

}