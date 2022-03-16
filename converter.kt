import java.math.BigInteger
import java.math.BigDecimal
import java.math.RoundingMode

fun toDecimal(source: String, base: BigDecimal): String {
    val upToBase36 : MutableList<Char> = mutableListOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 															'u', 'v', 'w', 'x', 'y', 'z')
    var num = BigDecimal.ZERO
    var count = 0
    var first = source.substringBefore('.')
    val indexOfDot = source.indexOf('.')
    //Gets the first part of the string before the fractional part of the number
    for (i in first.length - 1 downTo 0) {
        for (j in upToBase36.indices) {
            if (upToBase36[j] == first[i]) {
                num += base.pow(count) * j.toBigDecimal()
                break
            }
        }
        count++
    } 
    if (indexOfDot == -1) {
        return num.toString()
    }
    val one = BigDecimal.ONE
    val fractionalPart = source.substring(indexOfDot + 1)
    count = 1
    //Gets the fractional part of the number
    for (i in 0 until fractionalPart.length) {
        for (j in upToBase36.indices) {
            if (upToBase36[j] == fractionalPart[i]) {
                var temp = one.divide(base.pow(count), 5, RoundingMode.HALF_UP)
                num += temp * j.toBigDecimal()
                break
            }
        }
        count++
    }
    return num.toString()
}

fun decimalToOthers(source: String, base: BigDecimal): String {
    val upToBase36 : MutableList<Char> = mutableListOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 															'u', 'v', 'w', 'x', 'y', 'z')
    var str = ""
    var first = source.substringBefore('.')
    val indexOfDot = source.indexOf('.')
    var beforeFraction = first.toBigInteger()
    val baseInt = base.toBigInteger()
    while(beforeFraction != BigInteger.ZERO) {
        var remainder = beforeFraction % baseInt
        str += upToBase36[remainder.toInt()]
        beforeFraction /= baseInt
    }
    str = str.reversed()
    if(indexOfDot == -1) {
        return str
    }
    str += "."
    var second = "0" + source.substring(indexOfDot)
    var fractionalPart = second.toBigDecimal()
    var counter = 0
    while (counter != 5) {
        fractionalPart *= base
        var remain = fractionalPart.setScale(0, RoundingMode.DOWN) % base
        str += upToBase36[remain.toInt()]
        counter++
    }
    return str
}

fun convert() {
    while (true) {
        println("Enter two numbers in format: {source base} {target base} (To quit type: exit)")
        val command = readLine()!!
        if (command == "exit") {
            break
        }
        val parts = command.split(" ")
        while (true) {
            println("Enter number in base ${parts[0]} to convert to base ${parts[1]} (To go back type /back)")
            val thing = readLine()!!
            if (thing == "/back") {
                break
            }
            val decimal = toDecimal(thing, parts[0].toBigDecimal())
            if (parts[1] == "10") {
                println("Conversion result: $decimal")
            } else {
                val result = decimalToOthers(decimal, parts[1].toBigDecimal())
                println("Conversion result: $result")
            }
        }
    }
}

fun main() {
    convert()
}
