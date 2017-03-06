# **함수형언어의 디자인패턴**

> 함수형 프로그래밍에서는 전통적인 디자인 패턴들이 다음 세가지로 나타난다고 함.
>
> * 패턴이 언어에 흡수
> * 패턴 해법이 함수형 패러다임에도 존재하지만, 구체적인 구현 방식은 다름
> * 패턴 해법이 패러다임에 없는 기능으로 구현 \( 자바로는 불가능한 \)

### 

### 함수 수준의 재사용

디자인 패턴으로 해결할 수 있는 문제들은 아주 특정된 문제에만 적용되기때문에 사용범위는 좁을 수밖에 없음.

저자가 생각하는 디자인 패턴의 존재 목적은 자바 가지고 있는 언어의 결함을 메꾸기 위해서 필요한 것이며

뼈대뿐인 클래스에 래핑하지 않고서는 행동을 전달할 수 없는 한계가 있기 때문임.



함수형 언어는 객체지향 언어들보다 더 큰 단위로 재사용함. 매개변수로 커스터마이즈되는 공통된 작업을 추출해 냄

![](/assets/스크린샷 2017-02-28 오후 12.10.27.png)

함수형 프로그래머들은 위의 그림처럼 큰단위의 재사용 매커니즘을 추출하려고 함.



### **템플릿 메서드**

전통적인 탬플릿 메소드 패턴은 하위 클래스가 추상클래스에서 정해준 메서드를 구현해야함. 

추상 메서드의 정의는 일종의 가이드 문서역할을 함. \( 팅빈 메서드를 구현할 수 있지만 \)

일급함수를 사용하면 불필요한 클래스를 없앨 수 있기 때문에 템플릿 메서드 디자인 패턴을 구현하기 쉬워짐.

템플릿 메소드는 하나의 알고리즘의 뼈대만 정의, 세부 구현은 하위 클래스에 맡기도록 함.



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
        plan[]
    }
    
    // 특별보호 접근자(?.) : 객체가 널인지 확인한 후에 메서드를 실행 
    def process() {
        checkCredit?.call()
        checkInventory?.call()
        ship?.call()
    }
}
```

> 고계함수가 있기 때문에 커맨드패턴, 탬플릿 패턴과 같은 고전적인 패턴에서 자주 사용하는 보일러플레이트 코드가 필요없어짐.



### **전략\(Strategy\) 패턴** 

각자 캡슐화되어 서로 교환 가능한 알고리즘 군으로 정의하여 클라이언트에 상관없이 바꿔서 사용할 수 있게 해주는 패턴.

일급함수를 사용하면 사용이 간편해짐.



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

> new CalcMult().product(5, 2)
10
> new CalcAdds().product(5, 2)
10

```

**개선된 함수형프로그래밍 전략패턴**

```
def calcMult = (m, n) -> n * m
def calcAdds = (m, n) -> {
        def result = 0
        n.times { result += m }
        result
    }

calcMult(5, 2)
calcAdds(5, 2)
```

> 전통적인 방법은 같은 클래스나 인터페이스를 상속해야하는등 각 전략에 이름과 구조를 정해야되는 제약사항이 존재함

























