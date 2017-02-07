package chapter8

import java.util.Calendar

object Customer {

  val contacts1 = List[Contact](
    new Contact(101, "Lee", "Kevin", "Kevin@gmail.com", true),
    new Contact(102, "Kim", "Rechard", "Rechard@gmail.com", false),
    new Contact(103, "Choi", "Kim", "Kim@gmail.com", true)
  )

  val contacts2 = List[Contact](
    new Contact(201, "Lee", "Alpha", "Alpha@gmail.com", false),
    new Contact(202, "Kim", "Beta", "Beta@gmail.com", true)
  )

  val contact3 = List[Contact](
    new Contact(301, "Lee", "Charlee", "Charlee@gmail.com", false),
    new Contact(302, "Yoon", "Cadon", "Cadon@gmail.com", true),
    new Contact(303, "Zoo", "Bernard", "Bernard@gmail.com", false)
  )

  val allCustomers = List[Customer](
    new Customer(1, "John", "USA", "facebook.com", true, new Contract(Calendar.getInstance(), true), contacts1),
    new Customer(2, "Choi", "USA", "google.com", false, new Contract(Calendar.getInstance(), true), List()),
    new Customer(3, "You", "Korea", "naver.com", true, new Contract(Calendar.getInstance(), true), List()),
    new Customer(4, "Jang", "USA", "yahoo.com", true, new Contract(Calendar.getInstance(), true), List()),
    new Customer(5, "Yuki", "Japan", "facebook.com", true, new Contract(Calendar.getInstance(), true), contacts2),
    new Customer(6, "Suc", "China", "facebook.com", true, new Contract(Calendar.getInstance(), true), List()),
    new Customer(7, "Kim", "Korea", "daum.com", true, new Contract(Calendar.getInstance(), true), contact3)
  )

  def EnabledCustomer(customer: Customer): Boolean = customer.enabled == true

  def DisabledCustomer(customer: Customer): Boolean = customer.enabled == false

  def getDisabledCustomerNames(): List[String] = {
    Customer.allCustomers.filter(DisabledCustomer).map({ customer =>
      customer.name
    })
  }

  def getEnabledCustomerStates(): List[String] = {
    Customer.allCustomers.filter(EnabledCustomer).map({ customer =>
      customer.state
    })
  }

  def getEnabledCustomerDomains(): List[String] = {
    Customer.allCustomers.filter(EnabledCustomer).map({ customer =>
      customer.domain
    })
  }

  def getEnabledCustomerSomeoneEmail(someone: String): List[String] = {
    Customer.allCustomers.filter(EnabledCustomer).map({ customer =>
      someone + "@" + customer.domain
    })
  }

  def getCustomerById(inList: List[Customer],
                      customer_id: Integer): List[Customer] = {
    inList.filter(customer => customer.customer_id == customer_id)
  }

  def eachEnabledContact(cls: Contact => Unit) {
    Customer.allCustomers.filter({ customer =>
      customer.enabled && customer.contract.enabled
    }).foreach({ customer =>
      customer.contacts.foreach(cls)
    })
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  def updateCustomerByIdList(initialIds: List[Customer],
                             ids: List[Integer],
                             cls: Customer => Customer): List[Customer] = {
    if (ids.size <= 0 || initialIds.size <= 0) {
      List()
    } else {
      val precust = initialIds.find(cust => cust.customer_id == ids(0))
      val cust = if (precust.isEmpty) { List() } else { List(cls(precust.get)) }
      cust ::: updateCustomerByIdList(
        initialIds.filter(cust => cust.customer_id != ids(0)),
        ids.drop(1),
        cls
      )
    }
  }

  /**
    * 패턴매치를 활용하여 간결하게 수정한 메소드 ( 그래도 좀 간결하지 않음 )
    */
  def updateCustomerByIdList1(initialIds: List[Customer],
                              ids: List[Integer],
                              cls: Customer => Customer): List[Customer] = {
    (initialIds, ids) match {
      case (List(), _) => List()
      case (_, List()) => List()
      case _ => {
        val precust = initialIds.find(cust => cust.customer_id == ids(0))
        val cust = if (precust.isEmpty) { List() } else { List(cls(precust.get)) }
        cust ::: updateCustomerByIdList1(
          initialIds.filter(cust => cust.customer_id != ids(0)),
          ids.drop(1),
          cls
        )
      }
    }
  }

  def updateCustomerByIdListUpgrade(initialIds: List[Customer],
                                    ids: List[Integer],
                                    cls: Customer => Customer): List[Customer] = {
    (initialIds, ids) match {
      case (List(), _) => List()
      case (_, List()) => List()
      case (_, id :: tailIds) => {
        val precust = initialIds.find(cust => cust.customer_id == id)
        precust match {
          case None => updateCustomerByIdListUpgrade(initialIds, tailIds, cls)
          case Some(x) => cls(x)::updateCustomerByIdListUpgrade(
            initialIds.filter(y => y.customer_id != id),
            tailIds,
            cls
          )
        }
      }
    }
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  def updateContactForCustomerContact(customer_id: Integer,
                                      contact_id: Integer,
                                      cls: Contact => Contact): List[Customer] = {
    updateCustomerByIdList(Customer.allCustomers, List(customer_id), { customer =>
      new Customer(
        customer.customer_id,
        customer.name,
        customer.state,
        customer.domain,
        customer.enabled,
        customer.contract,
        customer.contacts.map { contact =>
          if (contact.contact_id == contact_id) {
            cls(contact)
          } else {
            contact
          }
        }
      )
    })
  }

  def updateContractForCustomerList(ids: List[Integer], cls: Contract => Contract): List[Customer] = {
    updateCustomerByIdList(Customer.allCustomers, ids, { customer =>
      new Customer(
        customer.customer_id,
        customer.name,
        customer.state,
        customer.domain,
        customer.enabled,
        cls(customer.contract),
        customer.contacts
      )
    })
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  def countEnabledCustomersWithNoEnabledContactsOld(customers: List[Customer], sum: Integer): Integer = {
    if (customers.isEmpty) {
      sum
    } else {
      val addition = if (customers.head.enabled && customers.head.contacts.exists({ contact => contact.enabled })) { 1 } else { 0 }
      countEnabledCustomersWithNoEnabledContacts(customers.tail, addition + sum)
    }
  }

  def countEnabledCustomersWithNoEnabledContacts(customers: List[Customer], sum: Integer): Integer = {
    customers match {
      case List() => sum
      case Customer(_, _, _, _, true, _, List()) :: custs => countEnabledCustomersWithNoEnabledContacts(custs, sum)
      case Customer(_, _, _, _, true, _, contacts) :: custs
        if contacts.exists({ contact => contact.enabled }) => countEnabledCustomersWithNoEnabledContacts(custs, sum + 1)
      case cust :: custs => countEnabledCustomersWithNoEnabledContacts(custs, sum)
    }
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

// 패턴매치를 사용하려면 case 예약어를 통해서 클래스를 재정의 해야함
case class Customer(val customer_id: Integer,
                    val name: String,
                    val state: String,
                    val domain: String,
                    val enabled: Boolean,
                    val contract: Contract,
                    val contacts: List[Contact]) {
}
