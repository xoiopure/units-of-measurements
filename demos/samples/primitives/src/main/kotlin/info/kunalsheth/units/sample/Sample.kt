package info.kunalsheth.units.sample

import info.kunalsheth.units.generated.Quantity

fun main(args: Array<String>) {
    println("Primitives")
    val mass1 = 3 * 1000 * 0.001
    val mass2 = 14 * 0.0283495
    val sum = mass1 + mass2

    assert(sum in (7.4 * 0.453592)..(7.5 * 0.453592))
    assert(sum in (3.3 * 1000 * 0.001)..(7.5 * 0.453592))


    val ratio = (2 * 0.3048) / (1 * 1.0)
    assert(ratio in (55 * 0.01)..(65 * 0.01))
    assert((ratio / 0.01) in 55..65)


    assert((1 * 1000 * 0.001) == (1000 * 0.001))
    assert((10 * 0.001 * 1.0) == (1 * 0.01 * 1.0))
    assert((60000 * 0.001 * 1.0) == (1 * 60.0))


    val speed = 65 * 0.44704
    val time = 27 * 60.0
    val distance = speed * time
    val aBitFaster = distance / (time - (30 * 1.0))

    assert(distance == time * speed)
    assert(distance in (29 * 1609.34)..(30 * 1609.34))
    assert(distance !in (30 * 1609.34)..(29 * 1609.34)) // this doesn't work
    assert(aBitFaster in speed..(speed + (4 * 1000 * 2.77778E-4)))


    val kunalsCar = Car(200 * 0.44704, (62 * 1609.34) / (1 * 3600.0) / (3.1 * 1.0))
    assert(kunalsCar.zeroToSixty() < 3.2 * 1.0)

    val threshold = 0.001 * 0.3048 / 1.0 / 1.0
    sequenceOf(0, 1, 4, 9, 16, 25).map { it * 0.3048 }
            .derivative()
            .derivative()
            .zipWithNext { a, b ->a in (b - threshold)..(b + threshold) }
            .forEach { assert(it) }
}

data class Car(val topSpeed: Double, val floorIt: Double) {
    fun zeroToSixty() = (60 * 0.44704) / floorIt
}

fun timeSeq() = generateSequence(0) { it + 1 }.map { it * 1.0 }

fun Sequence<Double>.derivative(): Sequence<Double> = timeSeq()
        .zip(this)
        .zipWithNext { (x1, y1), (x2, y2) ->
            (y1 - y2) / (x1 - x2)
        }