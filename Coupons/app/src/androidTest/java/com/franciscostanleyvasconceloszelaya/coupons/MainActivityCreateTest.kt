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
import org.hamcrest.Matchers.not
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
        etCoupon.perform(replaceText("WELCOME_02"))

        val btnConsult = onView(withId(R.id.btnConsult))
        btnConsult.perform(click())

        val btnCreate = onView(withId(R.id.btnCreate))
        btnCreate.check(matches(isDisplayed()))

        val etDescription = onView(withId(R.id.etDescription))
        etDescription.perform(replaceText("Cupon1"))


        etDescription.perform(replaceText("WELCOME_01"))
        btnCreate.perform(click())

        val snackbar = onView(withId(com.google.android.material.R.id.snackbar_text))
        snackbar.check(matches(withText("Created coupon")))
    }

    /*
    * Corrobora que el boton "crear" no existe y no es visible.
    * Test: nuesto etCoupon inicia vacio, luego haz click sobre el, añade el texto "WELCOME_01"
    * y ahora desde btnConsult, haz click sobre el.
    * Verifica que el btnCreate no es visible
    * */
    @Test
    fun consultCouponExistTest() {
        val etCoupon = onView(withId(R.id.etCoupon))
        etCoupon.check(matches(withText("")))
        etCoupon.perform(click())
        etCoupon.perform(replaceText("WELCOME_01"))

        val btnConsult = onView(withId(R.id.btnConsult))
        btnConsult.perform(click())

        val btnCreate = onView(withId(R.id.btnCreate))
        btnCreate.check(matches(not(isDisplayed())))// ! = not()
    }

    /*
    * Comprueba que no se puede crear un cupon con codigo repetido.
    * Test: etCoupon inicia vacio, y se reemplaza texto con "WELCOME_01A (cupon existente)"
    * Click en btnConsult
    * Corrobora que btnCreate existe.
    * Añade descripcion y edita etCoupon(por un codigo existente, ej: WELCOME_01)
    * Click en btnCreate
    * Comprueba el mensaje de sanckbar(The coupon already exists, use another code).*/
    @Test
    fun createCouponWithOldCodeTest(){
        val etCoupon = onView(withId(R.id.etCoupon))
        etCoupon.perform(replaceText("WELCOME_01A"))

        val btnConsult = onView(withId(R.id.btnConsult))
        btnConsult.perform(click())

        val btnCreate = onView(withId(R.id.btnCreate))
        btnCreate.check(matches(isDisplayed()))

        val etDescription = onView(withId(R.id.etDescription))
        etDescription.perform(replaceText("3x2 enn chocolates"))
        etCoupon.perform(replaceText("WELCOME_01"))

        btnCreate.perform(click())

        val snackbar = onView(withId(com.google.android.material.R.id.snackbar_text))
        snackbar.check(matches(withText("The coupon already exists, use another code")))
    }
}