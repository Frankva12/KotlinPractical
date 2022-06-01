package com.franciscostanleyvasconceloszelaya.coupons.common.utils

import org.junit.Assert.*
import org.junit.Test
import com.franciscostanleyvasconceloszelaya.coupons.R
import com.franciscostanleyvasconceloszelaya.coupons.common.entities.CouponEntity

class CouponUtilsKtTest {
    @Test
    fun validateTextCodeSuccessTest() {
        val code = "WELCOME"
        assertTrue(validateTextCode(code))
    }

    @Test
    fun validateTextCodeEmptyFailTest() {
        val code = ""
        assertFalse(validateTextCode(code))
    }

    @Test
    fun validateTextCodeMinLenghtTest() {
        val code = "HOLA"
        //val code2 = "HOLA2"
        assertFalse(validateTextCode(code))
        //assertTrue(validateTextCode(code2))
    }

    @Test
    fun validateTextCodeMaxLenghtTest() {
        val code = "HOLA1234567"
        //val code2 = "HOLA2123"
        assertFalse(validateTextCode(code))
        //assertTrue(validateTextCode(code2))
    }

    @Test
    fun getMsgErrorByCodeNullTest() {
        val errorCode = null
        val expectedValue = R.string.error_unknown
        val result = getMsgErrorByCode(errorCode)
        assertEquals("Error al evaluar un null", expectedValue, result)
    }

    @Test
    fun getMsgErrorByCodeExistTest() {
        val errorCode = Constants.ERROR_EXIST
        val expectedValue = R.string.error_unique_code
        val result = getMsgErrorByCode(errorCode)
        assertEquals("Error al evaluar cupon existente", expectedValue, result)
    }

    @Test
    fun getMsgErrorByCodeLengthTest() {
        val errorCode = Constants.ERROR_LENGTH
        val expectedValue = R.string.error_invalid_length
        val result = getMsgErrorByCode(errorCode)
        assertEquals("Error al evaluar la longitud valida", expectedValue, result)
        assertNotEquals("El recurso no existe", -1, result)
    }

    @Test
    fun checkNotNullCouponTest() {
        val coupon = CouponEntity(code = "Android", description = "Cursos a$9,99 USD")
        assertNotNull("El cupon no deberia ser null", coupon)
    }

    @Test
    fun checkGroupsSuccessTest() {
        val aNames = arrayOf("Alain", "Daniel", "Marie")//valor actual
        val bNames = arrayOf("Alain", "Daniel", "Marie")//valor esperado
        assertArrayEquals("Los arreglos deberian coincidir, revise sus elementos", bNames, aNames)
    }

    @Test
    fun checkNullCouponTest() {
        val coupon = null
        assertNull("El cupon deberia ser null", coupon)
    }

    @Test
    fun checkGroupsFailTest() {
        val aNames = arrayOf("Alain", "Daniel", "Marie")//valor actual
        val bNames = arrayOf("Alain", "Danielin", "Marie")//valor esperado
        assertNotEquals("Los arreglos no deberian coincidir", bNames, aNames)
    }

}