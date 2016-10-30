package chapter4.sample

def f = "Foo"

def func(obj) {
    obj = "Bar"
}

println f
func(f)
println f