package com.franciscostanleyvasconceloszelaya.coupons

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.franciscostanleyvasconceloszelaya.coupons.mainModule.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityCreateTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun createCouponTest() {
        val etCoupon = onView(withId(R.id.etCoupon))
        etCoupon.check(matches(withText("")))//verifica que(view) coincida(con el texto("")))
        etCoupon.perform(click())
        etCoupon.perform(replaceText("Welcome_01"))

        val btnConsult = onView(withId(R.id.btnConsult))
        btnConsult.perform(click())

        val btnCreate = onView(withId(R.id.btnCreate))
        btnCreate.check(matches(isDisplayed()))

        val etDescription = onView(withId(R.id.etDescription))
        etDescription.perform(replaceText("Cupon1"))

        btnCreate.perform(click())
    }
}