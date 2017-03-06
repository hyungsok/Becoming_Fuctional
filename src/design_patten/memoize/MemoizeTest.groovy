package design_patten.memoize

import groovy.transform.Field
import groovy.transform.Memoized

@Field
boolean incrementChange = false

@Memoized
int increment(int value) {
    println("++ increment(" + incrementChange + ") value : " + value)
    incrementChange = true
    value + 1
}

def square = { value ->
    println("++ square() value : " +  value)
    value * value
}.memoize()

println("---------------------------------------------------")
println("\t increment >> " + increment(10))
println("\t incrementChange >> " + incrementChange)
println("---------------------------------------------------")
incrementChange = false
println("\t increment >> " + increment(10))
println("\t incrementChange >> " + incrementChange)
println("---------------------------------------------------")
println("\t increment >> " + increment(11))
println("\t incrementChange >> " + incrementChange)

println("---------------------------------------------------")
println("\t square >> " + square(10))
println("\t square >> " + square(10))
println("\t square >> " + square(11))
