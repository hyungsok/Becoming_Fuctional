# 그루비 \( 동적 타이핑 언어 \)

* 기존 언어와는 달리 변수 타입이 동적\(dynamical\)이고 유연하며, 소스 파일을 컴파일하지 않고 바로 실행시키는 스크립팅 언어
  * 그루비는 동적 타이핑 언어이다. 런타임 시점에 새로운 타입을 생성할 수 있고, 컴파일 시점에서는 맞지 않는 타입을 전달해도 경고조차 하지 않는다.
  * 그루비의 이런 특징은, 함수에 부적합한 클래스를 넘기지 않도록 코딩 시 100% 단위 테스트를 작성함으로써 극복할 수 있다.
* 설치방법 : [http://groovy-lang.org/download.html\#otherways](http://groovy-lang.org/download.html#otherways)
* 2016년 전세계 16위 언어비중을 차지 \( [http://www.tiobe.com/tiobe-index](http://www.tiobe.com/tiobe-index\)\)
* 안드로이드의 빌드 스크립트로 널리 쓰임 \( build.gradle \)
  * 멀티프로젝트 대응 \(알파, 베타, 단말별리소스, OS버전\)
  * 컴포넌트로 만들기 쉬움 \(분할, 재사용, 확장\)
  * 호환성이 좋음
  * 별도의 설치파일이 없음
* 리스트와 맵을 위한 리터럴이 언어 자체에 녹아 있어 코딩량이 현격하게 줄고, 개발 시간도 단축

# 자바와의 차이점 \( 비슷하다 \)

* 미묘한 차이점들을 제외하면 자바 문법에서 상당히 많은 부분이 그루비 문법에 포함
* 모든 것을 객체로 다루기 \( 함수, 숫자 모든것을 객체로 \)
* 새로운 자료형에 관련된 연산자와 표현식

## "==" 의미

* ==는 모든 형식에서 동등함을 의미 **equals\(\)**의 기능을 의미
* 자바에서의 == 를 사용하고 싶다면 **is**라는 내장 함수를 사용
  * ex&gt; foo.is\(bar\)

## Groovy에서는 모든게 객체이다 \(숫자, 함수등\)

```groovy
println 3.toString();
3.plus(3)  // 6
```

## 문자열

* 작은 따옴표 \('\) : 자바의 문자열과 거의 같은 용도
  * String name = '문자열'
* 큰 따옴표 \("\) : 문자열 내부의 $ 기호로 동적인 내용 추가가능, 내부적으로 그루비의 GString 클래스가 사용
  * String title = "Project: ${project.name}"
  * String name = "Project: $name"
* 작은따옴표, 큰따옴표를 세개를 연달아 쓰면 여러줄 표기 가능

```groovy
String greet =  '''
                ************
                *  Hellow  *
                ************
                '''
println greet
[출력]
************
*  Hellow  *
************
```

## 메세드 호출 시 괄호 생략

* 인수가 한개 : println 'Hello World!'
* 인수가 여러개 : printf '%5.3f', 345.0
* 인수가 없을때\(생락불가능\) : println\(\) // \(\)가 없으면 에러
* 함수에서 메소드 호출할때\(생략불가능\) : println someMethod\('foo'\)

## def를 이용한 형 지정 생략

* def를 지정하는 것은 자바에서 Object형을 지정한 것과 같음.
  * java : **String name = 'Lee'**  
  * groovy : **def name = 'Lee'**
* def를 이용한 형 지정 생략은 변수, 메소드, 인수, 반환값에도 적용 가능

## 메소드의 리턴문

* 리턴 생략 가능
* 생략시 맨 마지막 문장의 결과 값을 리턴값으로 간주

## For in 반복문 \( in 예약어 \)

* for \(변수멍 in 컬렉션\)
* 자바에서 for\(;;\)지원 가능

## 클로져 \( Closure \)

* 그루비 클로져는 코드 블록 혹은 메서드 포인터와 같다. 클로져는 정의 후 나중에 실행할 코드 조각 
* 클로저는 어딘가에 정의된 임의의 코드가 다른 코드에 전달되고 실행되는 것을 말함.
* 객체지향 언어에서는 이런 행동을 흉내 낼때 매서드-객체 패턴을 사용
* 그루비에서 클로저는 다른 코드 블록처럼 중괄호로 둘러싸인 문장들이다. 인자가 있는 경우에는 먼저 인자들이 나오고 화살표\( -&gt; \) 뒤에 문장들이 나오기도 함.

```groovy
[1, 2, 3].each { entry -> println entry }
```

1. \[1, 2, 3\] 리스트
2. each 반복 메서드
3. { entry -&gt; println entry } 중괄호 내의 클로저 entry 인자
4. println entry 문장

```groovy
[1,2,3].each{ it + 1 }
// 출력 : 2 3 4 (인수를 생략하면 암묵적인수 it을 사용하면 된다)
```

```groovy
class Closer01 {
    def cl = { param1, param2 -> param1 + param2 }
    def cl_mul = { param, param2 -> param2 * param2 }

    static def func_cl(pc) {
        def val = 1
        pc(val, 2)
    }

    public static void main(String[] args) {
        def closer01 = new Closer01()
        println "${func_cl(closer01.cl)}"
        println "${func_cl(closer01.cl_mul)}"
    }
}

// 출력은 3, 4 
```

## 패키지 표현방식

```
자바 : java.net.URLEncoder.encode("a b");
// 그루비 코드는 짧기만 한게 아니라, 코드의 목적을 가장 간단한 형태로 보여준다. 
그루비: URLEncoder.encode 'a b'
```

## 맵

* key:Value 형태로 이루어지며 JVM에서 java.util.HashMap으로 인식

```groovy
def http = [
    100 : 'continue',
    200 : 'ok',
    400 : 'bad request'
    ]
assert http.size() == 3 
assert http[200] == 'ok'
http[500] == 'internal server error'

//클로져 이용한 출력
http.each{
        key, value -
>

        println "key: ${key}, value: ${value}"
}
/**
// 출력 
key: 100, value: continue
key: 200, value: ok
key: 400, value: bad request
*/

http.put(500, 'test')
println(http) // [100:continue, 200:ok, 400:bad request, 500:test]
```

## 리스트

```groovy
def roman = [ '', '1', '2', '3', '4', '5', '6', '7'  ]
assert roman[4] == '4'

roman[8] = '8'
assert roman.size() == 9 

def list = [1, 2, 3].collect { it = it * it }
println(list) // [ 1, 4, 9 ]
list.add(10)
println(list) // [ 1, 4, 9, 10 ]
def countDef =[1,3,4,5,6,2,1,11].count(1)
println(countDef) // 2 출력 1의 갯수 

// Create a list
def colorList = ["Red", "Green", "Blue", "Red", "Green", "Blue"]
println "colorList : " + colorList
println colorList.class.name

colorList : [Red, Green, Blue, Red, Green, Blue]
java.util.ArrayList

// Create a set
def colorSet = colorList as Set
println "colorSet : " + colorSet
println colorSet.class.name

colorSet : [Red, Green, Blue]
java.util.LinkedHashSet
```

## range

```groovy
def x = 1..10
assert x.contains(5)
assert x.contains(15) == false
assert x.size() == 10
assert x.from == 1
assert x.to == 10
assert x.reverse() == 10..1
```



