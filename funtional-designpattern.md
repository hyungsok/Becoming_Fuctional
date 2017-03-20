# **함수형언어의 디자인패턴**

> 함수형 프로그래밍에서는 전통적인 디자인 패턴들이 다음 세가지로 나타난다고 함.
>
> * 패턴이 언어에 흡수 \(일급함수로 패턴처리\)
> * 패턴 해법이 함수형 패러다임에도 존재하지만, 구체적인 구현 방식은 다름 \( 팩토링 = 커링 \)
> * 패턴 해법이 패러다임에 없는 기능으로 구현 \( 매타 프로그래밍을 사용한 해법 \)

### 

### 함수 수준의 재사용

디자인 패턴으로 해결할 수 있는 문제들은 아주 특정된 문제에만 적용되기때문에 사용범위는 좁을 수밖에 없음.

저자가 생각하는 디자인 패턴의 존재 목적은 자바 가지고 있는 언어의 결함을 메꾸기 위해서 필요한 것이며

뼈대뿐인 클래스에 래핑하지 않고서는 행동을 전달할 수 없는 한계가 있기 때문임.

**함수형 언어는 객체지향 언어들보다 더 큰 단위로 재사용함**. 

![](/assets/functional_designpattern_1.png)

> 함수형 프로그래머들은 위의 그림처럼 큰단위의 재사용 매커니즘을 추출하려고 함.

### 

### **템플릿\(Template\) 메서드 패턴**

* 탬플릿은 하나의 틀을 의미, 하나의 틀에서 만들어진 형태는 같은 것으로 간주.
* 일반적으로 상위클래스에서 추상메서드를 통해 기능의 골격을 제공, 하위 클래스는 세부처리를 구체화함.
* 추상 메서드의 정의는 일종의 가이드 문서역할을 함. \( 팅빈 메서드를 구현할 수 있지만 \)

일급함수를 사용하면 불필요한 클래스를 없앨 수 있기 때문에 템플릿 메서드 디자인 패턴을 구현하기 쉬워짐.



**그루비로 작성한 전형적인 템플릿 메서드 **

```Groovy
abstract class Customer {
    def plan

    def Customer() {
        plan = []
    }

    def abstract checkCredit()
    def abstract checkInventory()
    def abstract ship()

    def process() {
        checkCredit()
        checkInventory()
        ship()
    }
}
```

**일급함수를 사용한 템플릿 메서드 **

```Groovy
class Customer {
    def plan, checkCredit, checkInventtory, ship

    def CustomerBlocks() {
        plan = []
    }

    // 특별보호 접근자(?.) : 객체가 널인지 확인한 후에 메서드를 실행 
    def process() {
        checkCredit?.call()
        checkInventory?.call()
        ship?.call()
    }
}

// 일급함수를 이용하면 아래와 같이 심플하게 구현가능
class UsCustomerBlocks extends Customer {

    def UsCustomerBlocks() {

        checkCredit = { plan.add "checking US customer credit" }

        checkInventory = { plan.add "checking US warehouses" }

        ship = { plan.add "Shipping to US address" }
    } 
}
```

### 

### **전략\(Strategy\) 패턴**

* strategy는 전략, 전술로 알고리즘을 뜻함.
* 알고리즘을 사용하는 곳과 알고리즘을 제공하는 곳을 분리시킨 구조로 알고리즘을 동적으로 교체 수 있는 장점을 지님
* 일급함수를 사용하면 사용이 간편하게 구현이 가능

**그루비로 작성한 전통적인 전략 패턴**

```Groovy
interface Calc {
    def product(n, m)
}

class CalcMult implements Calc {
    def product(n, m) { n * m }
}

class CalcAdds implements Calc {
    def product(n, m) {
        def result = 0
        n.times { result += m }
        result
    }
}

class StrategyTest {
    def listOfStrategies = [new CalcMult(), new CalcAdds()]

    @Test
    public void product_verifier() {
        listOfStrategies.each { s -> assertEquals(10, s.product(5, 2))} 
    }
}
```

**개선된 함수형프로그래밍 전략패턴**

```
@Test
public void product_verifier() {
    def listOfExp = [
        {n, m -> n * m },
        {n, m ->
            def result = 0
            n.times { result += m }
            result }]

    listOfExp.each { e -> assertEquals(10, e(5, 2)) } 
 }
```

> 전통적인 방법은 같은 클래스나 인터페이스를 상속해야하는등 각 전략에 이름과 구조를 정해야되는 제약사항이 존재함
>
> 코드블록을 일급함수로 사용하여, 이전 예제에서의 보일러플레이트 코드 대부분을 제거할 수 있음.

#### 

### 플라이웨이트\(flyweight\) 패턴

* flyweight는 '\(권투·레슬링 등의\) 플라이급 선수\(보통 체중 48~51kg사이\)'를 말함.
* 인스턴스를 많이 생성한다면 new가 많아지고 이는 메모리 사용량이 많아짐을 의미.
* 메모리 사용량을 줄이기 위한 방법으로, 인스턴스를 필요한 대로 다 만들어 쓰지 말고, 동일한 것은 가능하면 공유해서 [객체](http://terms.naver.com/entry.nhn?docId=3532992&ref=y)생성을 줄이자는 것

**그루비로 작성한 플라이웨이트 패턴 **

```Groovy
class Computer {
  def type
  def cpu
  def memory
  def hardDrive
  def cd
}

class Desktop extends Computer {
  def driveBays
  def fanWattage
  def videoCard
}

class Laptop extends Computer {
  def usbPorts
  def dockingBay
}

// 컴퓨터와 사용자연계해주는 클래스 
class AssignedComputer {
  def computerType
  def userId

  public AssignedComputer(computerType, userId) {
    this.computerType = computerType
    this.userId = userId
  }
}

@Singleton(strict=false) 
class ComputerFactory {
  def types = [:]

  // 오브젝트를 미리만들어서 캐시  
  private ComputerFactory() {
    def laptop = new Laptop()
    def tower = new Desktop()
    types.put("MacBookPro6_2", laptop)
    types.put("SunTower",  tower)
  }

  def ofType(computer) {
    types[computer]
  }
}

@Test
public void flyweight_computers() {
  def bob = new AssignedComputer(ComputerFactory.instance.ofType("MacBookPro6_2"), "Bob")
  def steve = new AssignedComputer(ComputerFactory.instance.ofType("MacBookPro6_2"),"Steve") 

  assertTrue(bob.computerType == steve.computerType)
}
```

**그루비에서** **메모이제이션 \(memoization\) 상세 예제 **

```Groovy
@Field
boolean incrementChange = false

@Memoized
int increment(int value) {
    println("++ increment(" + incrementChange + ") value : " + value)
    incrementChange = true
    value + 1
}

def square = { value ->
    println("++ square() value : " +  value)
    value * value
}.memoize()

println("---------------------------------------------------")
println("\t increment >> " + increment(10))
println("\t incrementChange >> " + incrementChange)
println("---------------------------------------------------")
incrementChange = false
println("\t increment >> " + increment(10))
println("\t incrementChange >> " + incrementChange)
println("---------------------------------------------------")
println("\t increment >> " + increment(11))
println("\t incrementChange >> " + incrementChange)

println("---------------------------------------------------")
println("\t square >> " + square(10))
println("\t square >> " + square(10))
println("\t square >> " + square(11))

/** 
[ 결과값 ]
---------------------------------------------------
++ increment(false) value : 10
     increment >> 11
     incrementChange >> true
---------------------------------------------------
     increment >> 11
     incrementChange >> false
---------------------------------------------------
++ increment(false) value : 11
     increment >> 12
     incrementChange >> true
---------------------------------------------------
++ square() value : 10
     square >> 100
     square >> 100
++ square() value : 11
     square >> 121
*/
```

**그루비에서 플라이웨이트를 Momoize한 함수정의한 버전**

```Groovy
class Computer {
  def type
  def cpu
  def memory
  def hardDrive
  def cd
}

class Desktop extends Computer {
  def driveBays
  def fanWattage
  def videoCard
}

class Laptop extends Computer {
  def usbPorts
  def dockingBay
}

class AssignedComputer {
  def computerType
  def userId

  public AssignedComputer(computerType, userId) {
    this.computerType = computerType
    this.userId = userId
  }
}

def computerOf = { type ->
    def of = [ MacBookPro6_2: new Laptop(), SunTower: new Desktop()]
    return of[type]
}
def computerOfType = computerOf.memoize()

@Test
public void flyweight_computers() {
  def sally = new AssignedComputer(computerOfType("MacBookPro6_2"), "Sally")
  def betty = new AssignedComputer(computerOfType("MacBookPro6_2"), "Betty")

  assertTrue sally.computerType == betty.computerType
}
```

> Memozies된 함수는 런타임 값을 캐시할 수 있게 해주는 함수임.
>
> 전통적인 플라이웨이트 패턴에서는 새클래스를 펙토리로 생성하여 사용하는데 함수형버전에서는 하나의 메소드를 구현한 후 Memoize한 버전을 리턴하면 플라이웨이트 패턴의 의미를 보존하면서 아주 간단하게 구현을 할 수 있음.

### 팩토리 패턴 \( 커링 \)

> 객체를 생성하기 위해 인터페이스를 정의하지만, 어떤 클래스의 객체를 생성할 지에 대해서는 하위 클래스에서 결정  
> \[ 팩토리 패턴 활용 예 \]
>
> * 생성할 객체 타입을 예측할 수 없을 때 ==&gt; 부모 클래스 타입을 이용 
> * 생성할 객체의 명세를 하위 클래스에서 정의하고자 하는 경우
> * 객체 생성의 책임을 하위 클래스에 위임시키고 어느 하위 클래스가 위임 했는지에 대한 정보를 은닉하고자 하는 경우

**커링\(currying\)**

> 고차함수를 응용하는 방법의 하나로 파라미터 여러 개를 가진 함수를 한 개의 파라미터만 받는 함수의 조합으로 표현하는 기법
>
> * 디자인 패턴 차원에서 보면,  커링은 함수의 팩토리처럼 사용됨.
> * 함수형 언어에서 보편적인 기능은 함수를 여느 자료구조처럼 사용할 수 있게 해주는 일급함수 덕분에 주어진 조건에 따라 다른 함수들을 리턴하는 함수를 만들 수 있음.

**그루비에서의 커링**

> 그루비는 커링을 Closure 클래스의 curry\(\) 함수를 사용하여 구현

```groovy
def adder = {x, y -> x + y}
def incrementer = adder.curry(1)

println "increment : " + incrementer(5)
println "increment : " + incrementer(6)
println "increment : " + incrementer(7)
println "increment : " + incrementer(8)
println "increment : " + incrementer(9)

// 출력 
increment : 6
increment : 7
increment : 8
increment : 9
increment : 10
```

**스칼라에서의 커링**

```scala
def modN(n : Int)(x : Int) = x % n

def multiply(f : Int => Int, x : Int, y : Int) = f(x) + y

println(multiply(modN(2), 9, 4))

// 출력 
5
```

> 커링이 언어나 런타임에 내장되어 있기 때문에, 함수 팩토리 개념이 이미 녹아들어있어 다른 구조물이 필요없다.



