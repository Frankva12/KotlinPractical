package com.franciscostanleyvasconceloszelaya.fundamentoskotlin

fun main() {
    assignNumber(2)
    val appDev = app(id = 1, "Terrific app", "12/08/2021")
    appDev.appDev()
}

fun assignNumber(number: Int) {
    if (number <= 0) {
        println("No tengo ninguna cosa")
    } else {
        println("Tengo $number manzanas")
    }
}

open class app(val id: Int, val name: String, val dateDev: String){
    open fun appDev() {
        println("The app id is: $id, the name of the app is $name, and the date of development is: $dateDev")
    }
}

