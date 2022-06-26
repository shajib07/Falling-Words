package com.atahar.fallingwords.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.atahar.fallingwords.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_action_search_visibility() {
        Thread.sleep(1000)
        Espresso.onView(allOf(withId(R.id.nav_host_fragment),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

}