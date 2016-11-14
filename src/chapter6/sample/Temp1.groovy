package chapter6.sample

class TestClass1 {

    def all = [1, 2, 3, 4, 5, 6]
    /**
     * 느긋하지 않은 멤버
     */
    def odd = all.findAll { num -> num % 2 == 1 }
}

//new TestClass()로 인스턴스를 생성하는 즉시 odd 멤버가 초기화
println(new TestClass1().odd)
