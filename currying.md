**커링**

인자가 여러개 있는 함수를 하나의 인자를 가진 함수의 체인형태로 만들어주는 것.

[http://arisu1000.tistory.com/27753](http://arisu1000.tistory.com/27753)

[https://bytebucket.org/nephilim/scalastudy/wiki/summary/misc\_curry.txt](https://bytebucket.org/nephilim/scalastudy/wiki/summary/misc_curry.txt)

**고계함수** \(High Order Fuction\)

[https://jaeyongcho.gitbooks.io/learnyouahaskell/content/highorderfunction.html](https://www.gitbook.com/book/hyungsok/becoming-fuctional/edit#)

#### 디자인 패턴 잘 정리된 사이트

* 자바 : [http://www.tutorialspoint.com/design\_pattern](http://www.tutorialspoint.com/design_pattern/)
* 클로져 : [http://clojure.or.kr/docs/clojure-and-gof-design-patterns.html](http://clojure.or.kr/docs/clojure-and-gof-design-patterns.html)

#### 디자인 패턴

* 커맨드 패턴 - 함수로 구현하면 분리된 객체를 여러개 만들필요없이 실행함수로서 커맨드를 반환하기만 하면됨 
  * ```Scala
    def toUpperCase(str : String) : () => String = { () => str.toUpperCase } 
    def transform : () => String = toUpperCase("foo")
    println(transform())
    ```
* 전략 패턴 - 함수를 인수로 받는 함수. 일급 함수 자체를 변수에 할당해서 구현
* 상태 패턴 - 상태에 의존하는 전략 패턴. 패턴매칭을 통해서 간단하게 구현 
* 방문자 패턴 - 다중 디스패치.
* 템플릿 메소드 패턴 - 기본 값을 포함한 전략 패턴.
* 이터레이터 패턴 - 시퀀스
* 메멘토 패턴 - 저장과 복구
* 관찰자 패턴 - 다른 함수뒤에 호출되는 함수.
* 인터프리터 패턴 - 트리를 처리하는 함수들.
* 플라이웨이트 패턴 - 캐쉬.
* 빌더 패턴 - 선택 인수.
* 책임 연쇄 패턴 - 함수 합성.
* 합성 패턴 - 트리
* 팩토리 메소드 패턴 - 객체 생성 전략.
* 추상 팩토리 패턴 - 관련 객체 생성 전략.
* 어댑터 패턴 - 랩퍼, 같은 기능들, 다양한 타입.
* 데코레이터 패턴 - 랩퍼, 같은 타입, 새로운 기능.
* 프록시 패턴 - 랩퍼, 함수 합성.
* 브릿지 패턴 - 추상과 구체의 분리.



