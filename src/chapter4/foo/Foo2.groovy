package chapter4.foo

class Foo {
    String str
}

def f = new Foo(str: "Foo")

def func(Foo obj) {
    obj.str = "Bar"
}

println f.str
func(f)
println f.str