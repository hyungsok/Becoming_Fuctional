# **함수형 OOP**

많은 OOP의 개념과 아이디어 \(디자인패턴\)를 계속 살리면서 작성해야할 코드를 줄일 수 있는 방법에 대한 이야기.

> 함수형 프로그래밍에서는 전통적인 디자인 패턴들이 다음 세가지로 나타난다고 함.
>
> * 패턴이 언어에 흡수
> * 패턴 해법이 함수형 패러다임에도 존재하지만, 구체적인 구현 방식은 다름
> * 패턴 해법이 패러다임에 없는 기능으로 구현 \( 자바로는 불가능한 \)

### 

### 함수 수준의 재사용

함수형 언어는 객체지향 언어들보다 더 큰 단위로 재사용함. 매개변수로 커스터마이즈되는 공통된 작업을 추출해 냄

![](/assets/스크린샷 2017-02-28 오후 12.10.27.png)

디자인 패턴으로 해결할 수 있는 문제들은 아주 특정된 문제에만 적용되기때문에 사용범위는 좁을 수밖에 없음.

디자인 패턴의 존재 목적은 자바 가지고 있는 언어의 결함을 메꾸기 위함.\( 뼈대뿐인 클래스에 래핑하지 않고서는 행동을 전달할 수 없는 한계 \)

### 

### **정적 캡슐화**

* 객체는 단지 데이터 집합을 캡슐화 시킨 그릇에 불과하다. 
  * 객체로 인자를 받는 정적 함수로 동작하게 하면서 객체를 핸들링.
  * 객체는 하나의 그릇으로 다루는 방식으로 함수와 데이터 바라보는 관점이 달라짐.
* 업무지시
  * 어떤 양식으로 이메일을 보낼수있도록 이메일 발송 로직을 따로 추출해서 리펙토링하여 개선하라
  * 전략패턴을 함수형 언어로 리펙토링 처리
    * [https://www.tutorialspoint.com/design\_pattern/strategy\_pattern.htm](https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm)

**원본**

```
class Contact(val contact_id: Integer,
    val firstName: String,
    val lastName: String,
    val email: String,
    val enabled: Boolean) {

    def sendEmail() = {
        println("수신:" + email + "\n제목:안녕하세요!\n내용:반나서반갑습니다."
    }    
}
```

**개선**

```
// 새로 작성한 Email 클래스 (그릇의 역할)
case class Email(val address: String, 
    val subject: String, 
    val body: String) {

    def send() : Boolean = Email.send(this)
}

// send 함수를 Email 싱글톤 클래스로 로직분리 
object Email {
    def send(msg: Email): Boolean = {
        println("수신:" + msg.address + "\n제목:" + msg.subject + "\n내용:" + msg.body) 
        true
    }
}
```

> 스칼라는 정적멤버\(필드 + 메소드\)를 싱글톤형태의 동반객체 \(Companion object\)에 담아놓으면 동일 소스파일에서 동반 객체와 동반 클래스는 서로 비공개 멤버까지 마음대로 참조할수가 있다.

```
// 리펙토링한 Contact클래스 
class Contact(val contact_id: Integer,
    val firstName: String,
    val lastName: String,
    val email: String,
    val enabled: Boolean) {

    def sendEmail() = {
        new Email(email, "안녕하세요!", "반나서반갑습니다.").send()
    }    
}
```

### 

### 객체는 그릇이다.

* 함수형 프로그래밍과 OOP가 서로 잘어울릴수 있게 만든 방안
* 업무지시
  * 어떤 이메일은 "친애하는~씨께"로 사직해야한다는 추가요청, Subject에 이름을 표시해야한다는 추가 요청
  * Email 클래스를 확실하게 그릇으로 여기질수 있도록 수정
    * 이메일생성에 필요한 필드만 포함
    * 이메일발송 기능은 없음

```
object Email {
  // Email 객체에서 공통 기능을 send 함수로 추출
  def send(to : String, subject : String, body : String) : Boolean = {
    println("To: " + to + "\nSubject: " + subject + "\nBody: " + body)
    true
  }

  // 패턴매칭을 통해서 기능로직 추가수정 
  def send(msg : Email) : Boolean = {
    msg match {
      case Email(address, subject, body, true, name) => 
        send(address, subject, "Dear " + name + ",\n" + body)
      case Email(address, subject, body, _, _) => 
        send(address, subject, body)
    }
    true
  }
}

// isDearReader, name 두개 파라미터 추가
case class Email(val address : String,
      val subject : String,
      val body : String,
      val isDearReader : Boolean,
      val name : String) {

  def send() : Boolean = Email.send(this)

}
```

```
// sendEmail함수 수정  
class Contact(val contact_id: Integer,
    val firstName: String,
    val lastName: String,
    val email: String,
    val enabled: Boolean) {

    def sendEmail() = {
        new Email(this.email, "안녕하세요~!", "블라블라~", true, this.firstName).send()
    }    
}
```

### 

### 코드는 데이터다

* 전략 디자인 패턴의 확장 \( 일급함수를 매개변수로 전달 \)
  * 디자인패턴 : [https://www.tutorialspoint.com/design\_pattern/strategy\_pattern.htm](https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm)
* 업무지시
  * 화면에 질문을 보여주고 사용자로부터 입력을 받는다. \( CommandLineOption 클래스 작성 \)
    * 명령어와 일급함수와 매치하여 CommandLineOption 클래스를 구성한다.
  * 사용자에게 모든 가능한 옵션을 보여준다. \( Map&lt;string, CommandLineOption&gt; 으로 옵션 데이터 추가 \)
  * 사용자의 입력을 해석한다. \( Pattern Matching 통해서 처리 \)

```Scala
case class CommandLineOption(description: String, func: () => Unit)

object CommandLine {
  // 고객 신규 추가
  def createCustomer(): Unit = {
    Customer.allCustomers = Customer.createCustomer(
      CommandLine.askForInput("Name"),
      CommandLine.askForInput("State"),
      CommandLine.askForInput("Domain")
    ).toList ::: Customer.allCustomers
  }

  // 고객 목록 조회
  def showCustomerList(): Unit = {
    Customer.allCustomers.foreach(customer =>
      println(customer.name)
    )
  }

  // 전체 이용 고객의 현재 연락처 목록 조회
  def showContactList(): Unit = {
    Customer.allCustomers.filter({ customer =>
      customer.enabled && customer.contract.enabled
    }).foreach({ customer =>
      customer.contacts.foreach(contact =>
        println(contact.firstName + " : " + contact.email))
    })
  }

  // 커맨드 옵션 
  val options: Map[String, CommandLineOption] = Map(
    "1" -> CommandLineOption("고객 신규 추가", createCustomer),
    "2" -> CommandLineOption("고객 목록 조회", showCustomerList),
    "3" -> CommandLineOption("전체 이용 고객의 현재 연락처 목록 조회", showContactList),
    "q" -> CommandLineOption("종료", sys.exit)
  )

  def askForInput(question: String): String = {
    print(question + ": ")
    readLine()
  }

  def prompt() = {
    options.foreach(option => println(option._1 + ") " + option._2.description))
    options.get(askForInput("Option").trim.toLowerCase) match {
      case Some(CommandLineOption(_, exec)) => exec()
      case _ => println("Invalid input")
    }
  }

  def main(args: Array[String]) = {
    while (true) {
      prompt()
    }
  }
}
```

고객 신규 추가’와 ‘고객 목록 조회’라는 두 옵션만 보더라도 이미 존재 하는 함수를 참조하여 캡슐화를 무너뜨리지 않고도 기존 작성된 함수를 재사용할 수 있다. 그러면 관리할 클래스의 수는 줄고 코드의 가독성이 높아진다.

## **정리**

우리가 흔히 알고 있는 디자인 패턴을 함수형 관점으로 구현이 가능

기존의 우리가 알고 있는 OOP관점으로 프로그래밍하다보면 관리해야할 클래스의 수가 늘어나게 되지만

함수형으로 개발하면 클래스의 수를 최대한 많이 줄일수있고 코드의 가독성이 높아지는 장점이 있음.

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



