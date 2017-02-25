# **함수형 OOP**

많은 OOP의 개념과 아이디어 \(디자인패턴\)를 계속 살리면서 작성해야할 코드를 줄일 수 있는 방법에 대한 이야기.

> 내가 이해한 함수형 프로그래밍은 최대한 순수함수를 작성하는 것.
>
> 가능한 입력과 출력의 관계를 기술하게 끔 추론하는 프로그래밍 인것 같다.
>
> 자연스럽게 객체에 데이터를 입력에 의해 변경하고 그에 맞는 액션을 취하도록 그렇게 개발해왔기때문에
>
> 객체를 바꾸지도 않을거면서 왜 가지고 있어야 하는가? 이런 의문을 항상 갖는다.
>
> 불변객체, 순수함수, immutable에 대한 개념은 코드작성을 통해서 조금씩 익히는 수 밖에는 답이 없는것 같다.
>
> 특히 이번 장에 이야기 할려고 하는 것은  **"객체는 단지 데이터 집합을 캡슐화 시킨 그릇에 불과하다." **라는 것이다.** **
>
> 객체는 행위를 하는 뭔가가 아니라, 데이터를 담는 그릇\(Contain\)이라는 사실를 코드를 통해 이해하도록 돕고있다.
>
> 함수형 프로그래밍과 OOP가 서로 잘 어울릴수 있는 예시라고 하는데 한번 살펴볼까나.

### **정적 캡슐화**

* 어떤 양식으로 이메일을 보낼수있도록 이메일 발송 로직을 따로 추출해서 리펙토링하여 개선하라

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

### 객체는 그릇이다.

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

### 코드는 데이터다

추가기능 요청

* 화면에 질문을 보여주고 사용자로부터 입력을 받는다.
* 사용자에게 모든 가능한 옵션을 보여준다.
* 사용자의 입력을 해석한다.

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

고객 신규 추가’와 ‘고객 목록 조회’라는 두 옵션만 보더라도 이미 존재 하는 함수를 참조하여 캡슐화를 무너뜨리지 않고도 기존 작성된 함수를 재사용할 수 있다. 



