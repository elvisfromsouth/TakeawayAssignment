package com.takeaway.borzikov.common.testing

import com.takeaway.borzikov.common.Text
import java.math.BigDecimal
import java.util.UUID
import kotlin.random.Random

fun randomString(): String {
    return UUID.randomUUID().toString()
}

fun randomText(): Text {
    return Text.Simple(randomString())
}

fun randomDouble(): Double {
    return Random.nextDouble()
}

fun randomInt(): Int {
    return Random.nextInt()
}

fun randomBigDecimal(): BigDecimal {
    return randomDouble().toBigDecimal()
}