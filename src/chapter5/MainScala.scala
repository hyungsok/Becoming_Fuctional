package chapter5

import java.util.Calendar

import scala.annotation.tailrec

/**
  * 스칼라 같은 언어는 좀 더 표현적이고 재귀 코드를 읽고 이해하기가 더 쉬움
  *
  * 간단한 스칼라 문법 정리
  *  - 함수는 def 키워드로 정의
  *  - 타입은 항상 정의부 뒤에 나오고 콜론(:) 연산자로 구분
  *  - 클래스 선언시 var으로 선언해야 외부에서 접근가능한 변수가 됨 (선언 안하면 private 변수가 됨)
  */
object MainScala {

  class Customer(var id: Int, var name: String, var state: String, var domain: String, var enabled: Boolean, var contract: Contract, var contacts: List[Contact]) {
    // 느긋한 enabledContacts 멤버
    lazy val enabledContacts = contacts.filter { contact => contact.enabled }
  }

  class Contact(var id: Int, var firstName: String, var lastName: String, var email: String, var enabled: Boolean)

  class Contract(var begin_date: Calendar = Calendar.getInstance(), var end_date: Calendar = Calendar.getInstance(), var enabled: Boolean = true)

  val contacts = List[Contact](
    new Contact(101, "Lee", "Kevin", "Kevin@gmail.com", false),
    new Contact(102, "Kim", "Rechard", "Rechard@gmail.com", false),
    new Contact(103, "Choi", "Kim", "Kim@gmail.com", false),
    new Contact(104, "Lee", "Charlee", "Charlee@gmail.com", false),
    new Contact(105, "Yoon", "Cadon", "Cadon@gmail.com", true),
    new Contact(106, "Zoo", "Bernard", "Bernard@gmail.com", false)
  )

  val allCustomers = List[Customer](
    new Customer(1, "John", "USA", "facebook.com", true, new Contract, contacts),
    new Customer(2, "Choi", "USA", "google.com", true, new Contract, contacts),
    new Customer(3, "You", "Korea", "naver.com", true, new Contract, List()),
    new Customer(4, "Jang", "USA", "yahoo.com", true, new Contract, contacts),
    new Customer(5, "Yuki", "Japan", "facebook.com", false, new Contract, contacts),
    new Customer(6, "Su", "China", "facebook.com", true, new Contract, List()),
    new Customer(7, "Kim", "Korea", "daum.com", true, new Contract, List())
  )

  @tailrec
  def countEnabledCustomersWithNoEnabledContacts(customers: List[Customer], sum: Int): Int = {
    if (customers.isEmpty) {
      return sum
    } else {
      val addition: Int =
        if (customers.head.enabled && customers.head.contacts.count(contact => contact.enabled) > 0) 1
        else 0
      return countEnabledCustomersWithNoEnabledContacts(customers.tail, sum + addition)
    }
  }

  /**
    * 한빛증권 사세가 확장되면서 팀장님은 현재 연락처가 없는 이용 고객의 인원수를 세어보라고 지시
    */
  def main(args: Array[String]): Unit = {
    println(countEnabledCustomersWithNoEnabledContacts(allCustomers, 0))
  }
}
