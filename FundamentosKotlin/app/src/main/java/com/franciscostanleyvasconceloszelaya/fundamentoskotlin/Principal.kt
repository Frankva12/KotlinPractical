package com.franciscostanleyvasconceloszelaya.fundamentoskotlin

const val separator = "=================="

fun main() {
//    print("Hola Kotlin!")
    newTopic("Hola Kotlin")

    newTopic("Variables y constantes")
//    val a = 2
//    val a = "Hola"
    val a = true
    println("a = $a")

//    var b = 2
    var b: Int
    b = 5
    println("b = $b")

    var objNull: Any?
    objNull = null
    objNull = "Hi"
//    objNull = "Hi"

    println(objNull)

}



fun newTopic(topic: String) {
//    println()
//    print("=======================")
//    print(topic)
//    print("==========================")
//    println()

    print("\n$separator $topic $separator\n")
}