package chapter4.foo

def f = "Foo"

def func(obj) {
    obj = "Bar"
}

println f
func(f)
println f