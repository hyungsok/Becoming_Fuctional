# 패턴매치 \(match\)

---

**기본용어 정리 **

* 튜플 : 여러 원소의 묶음, 각기 다른 타입의 원소를 넣을 수 있는 컨테이너 객체
* 클로져 \( 함수 리터럴의 본문에 있는 모든 변수에 대한 바인딩을 포획 \(capturing\)해서 변수가 없게 닫는 \(closeing\)행위에서 따온말 \)
  * 주어진 함수 리터럴부터 실행 시점에 만들어낸 객체 함수, 외부 변수도 참조 가능  
* : 연산자 : 함수정의시 사용함, ex&gt; def 함수명\(파라미터명 : 데이터형\)
* ::: 연산자 : 리스트를 붙이는 역할, ex&gt; List\(1,2\) ::: List\(3,4\) = List\(1,2,3,4\)
* :: 연산자 : 리스트에서 Head와 Tail을 가르는 연산자

---

**단순매치 **

* 객체로부터 멤버를 추출하여 비교하고 특정타입의 객체인지 확인 작업시 용이
* 변수대입 코드를 줄이고 좀 더 이해할 수 있는 형태로 작성 가능
* 객체멤버를 매치함으로써 특정 코드 조각을 실행할 때 상대적으로 간결한 로직을 구사가능함.

**리스트 추출 & 재귀호출 **

* 리스트는 헤드\(head\)와 테일\(tail\)로 구성 
* 헤더를 먼저바라보고 테일은 나중에 바라보는 식으로 한번에 한번 원소씩 전체리스트를 순회하는 구조

---

**그루비를 통한 패턴매치의 예**

```groovy
object CustomerFactory {
  /**
    * Customer 객체 제약사항
    *
    * 성명 (name)은 필수입니다.
    * 국가 (state)는 필수입니다.
    * 도메인 (domain)은 필수입니다.
    * 이용 여부 (enabled)의 기본값은 true다.
    * 계약 (contract)은 오늘 날짜를 기준으로 생성된다.
    * 연락처 리스트 (contacts)는 처음에 빈 상태로 만들어진다.
    *
    */
  // 패턴 매치 깊이가 2레벨
  def createCustomer0(name: String, state: String, domain: String): Customer = {
    name match {
      case "" => {
        println("Name cannot be blank")
        null
      }
      case _ => state match {
        case "" => {
          println("State cannot be blank")
          null
        }
        case _ => domain match {
          case "" => {
            println("Domain cannot be blank")
            null
          }
          case _ => new Customer(0, name, state, domain, true, new Contract(Calendar.getInstance, true), List())
        }
      }
    }
  }

  // 패턴 매치 깊이가 1레벨
  def createCustomer1(name: String, state: String, domain: String): Customer = {
    // 매치시킬 항목을 튜플 (name, state, domain)로 정의
    (name, state, domain) match {
      case ("", _, _) => {
        println("Name cannot be blank")
        null
      }
      case (_, "", _) => {
        println("State cannot be blank")
        null
      }
      case (_, _, "") => {
        println("Domain cannot be blank")
        null
      }
      case _ => new Customer(0, name, state, domain, true, new Contract(Calendar.getInstance, true), List())
    }
  }

  // createCustomer 완전체 메소드
  def createCustomer(name: String, state: String, domain: String): Option[Customer] = {
    def error(message: String): Option[Customer] = {
      println(message)
      None
    }
    (name, state, domain) match {
      case ("", _, _) => error("Name cannot be blank")
      case (_, "", _) => error("State cannot be blank")
      case (_, _, "") => error("Domain cannot be blank")
      case _ => new Some(new Customer(0, name, state, domain, true, new Contract(Calendar.getInstance, true), List()))
    }
  }
}


/**
  * [ 패턴 매치 ]
  * 객체로부터 멤버를 추출하여 비교하고 특정 타입의 객체인지를 확인하는 등의 작업을 하나의 문장으로 처리
  * 변수 대입 코드는 줄이고 코드를 좀 더 쉽게 이해할 수 있는 형태로 작성
  * 또 객체멤버를 매치함으로써 특정코드 조각을 실행해야할때 상대적으로 간결한 로직을 구사
  */
object Main {
  def main(args: Array[String]): Unit = {
    println(">> 모든 고객 정보 출력")
    printCustomers(Customer.allCustomers)
    println("------------------------------------------------------")

    println(">> 클로져를 통해서 활성화가 된 고객에게 (주소록을 가진) 이메일 보내기.")
    Customer.eachEnabledContact((contact: Contact) => contact.sendEmail())
    println("------------------------------------------------------")

    println(">> 단순치를 이용하여 고객정보 생성.")
    val customers = CustomerFactory.createCustomer("Hyungsok", "mangoplate", "daum.co.kr")
    customers.foreach(customer => println(customer.name))
    println("------------------------------------------------------")

    val count = Customer.countEnabledCustomersWithNoEnabledContactsOld(Customer.allCustomers, 0)
    println(">> 활성화되어 있고 주소록이 있는 고객 : " + count)
    println("------------------------------------------------------")

    println(">> 고객중에 활성화 되지 않은 고객리스트 가져오기")
    val disEnabledCustomers = Customer.allCustomers.filter(customer => !customer.enabled)
    printCustomers(disEnabledCustomers)
    println("------------------------------------------------------")

    println(">> 패턴매치로 일부 고객아이디 정보변경하고 이메일 보내기 ")
    val updateCustomers = Customer.updateCustomerByIdListUpgrade(Customer.allCustomers, List(2, 5),
      (customer: Customer) => customer.copy(enabled = true))
    printCustomers(updateCustomers)
    // 이메일 보내기
    updateCustomers.filter(customer => customer.enabled && customer.contract.enabled)
      .foreach({ customer => customer.contacts.drop(customer.contacts.size - 1).foreach(contact => contact.sendEmail()) })

  }

  def printCustomers(customers: List[Customer]): Unit = {
    customers.foreach(customer => {
      println(customer.customer_id + ":\t " + customer.name + ",\tenable - " + customer.enabled + ",\tcontract - " + customer.contract.enabled + ",\tcontact - " + customer.contacts.size)
    })
  }
}
```



