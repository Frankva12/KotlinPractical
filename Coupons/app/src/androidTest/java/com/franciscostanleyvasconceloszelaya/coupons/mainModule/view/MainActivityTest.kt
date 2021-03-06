package com.franciscostanleyvasconceloszelaya.coupons.mainModule.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.franciscostanleyvasconceloszelaya.coupons.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.etCoupon),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilCoupon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("udemy_02"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.etCoupon), withText("udemy_02"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilCoupon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(click())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.etCoupon), withText("udemy_01"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilCoupon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(click())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.etCoupon), withText("udemy_01"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilCoupon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.etCoupon), withText("udemy_01"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilCoupon),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(click())

        val materialButton = onView(
            allOf(
                withId(R.id.btnConsult), withText("Consult"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.etDescription),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilDescription),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText(""), closeSoftKeyboard())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.etDescription),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tilDescription),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.btnCreate), withText("Create coupon"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val snackbar = onView(withId(com.google.android.material.R.id.snackbar_text))
        snackbar.check(ViewAssertions.matches(withText("The coupon already exists, use another code")))

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnConsult), withText("Consult"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
