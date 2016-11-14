package chapter6.sample

class TestClass3 {

    def all = [1, 2, 3, 4, 5, 6]
    @Lazy
    def odd = all.findAll { num -> println("Foo"); num % 2 == 1; }

}

def tc = new TestClass3()

println("Bar")

println(tc.odd)
