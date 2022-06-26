package com.atahar.fallingwords.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.atahar.fallingwords.R
import com.atahar.fallingwords.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GameFragmentTest {

    @Test
    fun test_perform_correct_answer() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<GameFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.correct_btn)).perform(click())
    }

    @Test
    fun test_perform_wrong_answer() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<GameFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Thread.sleep(2000)
        onView(withId(R.id.wrong_btn)).perform(click())

    }

    @Test
    fun test_perform_missing_answer() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<GameFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        Thread.sleep(6000)
        onView(withId(R.id.wrong_btn)).perform(click())

    }


}