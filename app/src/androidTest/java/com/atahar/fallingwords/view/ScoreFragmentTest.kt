package com.atahar.fallingwords.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.atahar.fallingwords.R
import com.atahar.fallingwords.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ScoreFragmentTest {

    @Test
    fun test_perform_restart() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ScoreFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.restart_btn)).perform(click())

        verify(navController).navigate(
            R.id.action_scoreFragment_to_gameFragment
        )
    }

    @Test
    fun test_correct_ans_text_visibility() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ScoreFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.correct_ans_text)).check(ViewAssertions.matches(isDisplayed()))
    }


    @Test
    fun test_wrong_ans_text_visibility() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ScoreFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.wrong_ans_text)).check(ViewAssertions.matches(isDisplayed()))
    }

}