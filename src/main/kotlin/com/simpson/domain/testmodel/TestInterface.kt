package com.simpson.domain.testmodel

interface TestInterface {
    fun init(): Boolean
    fun execute(num:Int): Boolean
    fun fint(): () -> Unit
    fun reset(): () -> Unit
}