package design_patten.strategy

interface Calc {
    def product(n, m)
}

class CalcMult implements Calc {
    def product(n, m) { n * m }
}

class CalcAdds implements Calc {
    def product(n, m) {
        def result = 0
        n.times {
            result += m
        }
        result
    }
}

println("-----------------------------------------------")
def listOfStrategies = [new CalcMult(), new CalcAdds()]
listOfStrategies.each { s -> println(">> product : " + s.product(5, 2)) }

println("-----------------------------------------------")
def listOfExpression = [
        { n, m -> n * m },
        { n, m ->
            def result = 0
            n.times { result += m }
            result
        }]
listOfExpression.each { product -> println(">> product : " + product(5, 2)) }
