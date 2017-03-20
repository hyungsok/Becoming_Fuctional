package design_patten.factory

/**
 * Created by hyungsoklee on 2017. 3. 20..
 */
def adder = {x, y -> x + y}
def incrementer = adder.curry(1)

println "increment : " + incrementer(5)
println "increment : " + incrementer(6)
println "increment : " + incrementer(7)
println "increment : " + incrementer(8)
println "increment : " + incrementer(9)
println "increment : " + incrementer(10)