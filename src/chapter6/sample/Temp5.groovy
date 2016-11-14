package chapter6.sample

class TestClass5 {

    def all = [1, 2, 3, 4, 5, 6]
    @Lazy
    def odd = all.findAll { num -> num % 2 == 1 }

}

def tc = new TestClass5()

println(tc.odd)

tc.all = [1, 2, 3]

// 느긋한멤버 odd는 최초 참조시 단 한번계산이이루어 지고, 그 이후에는 두번 다시계산하는법이 없습니다.
println(tc.odd)
