package chapter6.sample

class TestClass2 {

    def all = [1, 2, 3, 4, 5, 6]
    def odd = all.findAll { num -> println("Foo"); num % 2 == 1; }

}

def tc = new TestClass2()

println("Bar")

// “Foo”가 한 무더기 찍힌 뒤 “Bar”가 나오고 마지막에 배열 자신 이 출력됩니다. 하지만 odd 앞에 @Lazy를 붙이면 결과가 달라집니다
println(tc.odd)
