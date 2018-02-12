package info.kunalsheth.units.sample

import info.kunalsheth.units.annotations.*
import info.kunalsheth.units.generated.*

typealias U = UnitOfMeasure
typealias D = Dimension

@Schema(
    relationships = [
        Relation(Dimension(L = 1), Dimension(T = 1)),
        Relation(Dimension(L = 1, T = -1), Dimension(L = 1, T = -2))
    ],
    quantities = [
        Quantity("Speed", Dimension(L = 1, T = -1)),
        Quantity("Acceleration", Dimension(L = 1, T = -2))
    ],
    unitsOfMeasure = [
        UnitOfMeasure("Grams", 0.001, Dimension(M = 1)),
        UnitOfMeasure("Ounces", 0.0283495, Dimension(M = 1)),
        UnitOfMeasure("Pounds", 0.453592, Dimension(M = 1)),

        // to decrease verbosity, you can create typealiases or use `import ... as ...`
        U("Feet", 0.3048, D(L = 1)),
        U("Metres", 1.0, D(L = 1)),
        U("Percent", 0.01, D()),
        U("MilesPerHour", 0.44704, D(L = 1, T = -1)),
        U("Minutes", 60.0, D(T = 1)),
        U("Hours", 3600.0, D(T = 1)),
        U("Seconds", 1.0, D(T = 1)),
        U("Miles", 1609.34, D(L = 1)),
        U("MetresPerHour", 0.0002777777778, D(L = 1, T = -1))
    ]
)
fun main(args: Array<String>) {
    println("Custom")
    val mass1 = 3.kilo { Grams }
    val mass2 = 14.Ounces
    val sum = mass1 + mass2
    // mass1 + 3.Days // will not compile

    assert(sum in 7.4.Pounds..7.5.Pounds)
    assert(sum in 3.3.kilo { Grams }..7.5.Pounds) // this works (but it is hard to understand)
    // assert(sum in 7.4.Kilowatts..7.5.Pounds) // will not compile


    val ratio = 2.Feet / 1.Metres
    assert(ratio in 55.Percent..65.Percent)
    assert(ratio.Percent in 55..65)


    assert(1.kilo { Grams } == 1000.Grams)
    assert(10.milli { Metres } == 1.centi { Metres })
    assert(60000.milli { Seconds } == 1.Minutes)


    val speed = 65.MilesPerHour
    val time = 27.Minutes
    val distance = speed * time
    val aBitFaster = distance / (time - 30.Seconds)

    assert(distance == time * speed)
    assert(distance in 29.Miles..30.Miles)
    assert(distance in 30.Miles..29.Miles) // this works too
    assert(aBitFaster in speed..(speed + 4.kilo { MetresPerHour }))


    val kunalsCar = Car(200.MilesPerHour, 62.Miles / 1.Hours / 3.1.Seconds)
    assert(kunalsCar.zeroToSixty() < 3.2.Seconds)
}

data class Car(val topSpeed: Speed, val floorIt: Acceleration) {
    fun zeroToSixty() = 60.MilesPerHour / floorIt
}