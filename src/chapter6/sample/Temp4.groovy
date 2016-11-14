package chapter6.sample

class TestClass4 {

    def all = [1, 2, 3, 4, 5, 6]
    @Lazy
    def odd = all.findAll { num -> num % 2 == 1 }

}

def tc = new TestClass4()

// odd를 참조하기 전 all를 변경하면, odd를 호출하는 시점에는 all의 ‘새’ 값을 기반으 로 ‘새로’ 계산된 값이 반영
tc.all = [1, 2, 3]

println(tc.odd)
