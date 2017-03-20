package design_patten.factory

object CurryScalaTest extends App {

  def filter(xs: List[Int], p: Int => Boolean): List[Int] = {
    println("filter : " + xs)
    if (xs.isEmpty) xs
    else if (p(xs.head)) xs.head :: filter(xs.tail, p)
    else filter(xs.tail, p)
  }

  // 커링할 함수를 정의
  def dividesBy(n: Int)(x: Int) = (x % n) == 0

  val nums = List(1, 2, 3, 4, 5, 6, 7, 8)
  println(filter(nums, dividesBy(2)))
  println(filter(nums, dividesBy(3)))

  def modN(n : Int)(x : Int) = x % n

  def multiply(f : Int => Int, x : Int, y : Int) = f(x) + y

  println(multiply(modN(2), 9, 4))
}
