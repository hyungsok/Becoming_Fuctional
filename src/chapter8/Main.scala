package chapter8

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
