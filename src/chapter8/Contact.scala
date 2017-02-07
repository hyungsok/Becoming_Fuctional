package chapter8

object Contact {

  def setNameAndEmailForContactAndCustomer(customer_id: Integer,
                                           contact_id: Integer,
                                           name: String,
                                           email: String): List[Customer] = {
    Customer.updateContactForCustomerContact(
      customer_id,
      contact_id, { contact =>
        new Contact(
          contact.contact_id,
          contact.firstName,
          name,
          email,
          contact.enabled
        )
      }
    )
  }
}

class Contact(val contact_id: Integer,
              val firstName: String,
              val lastName: String,
              val email: String,
              val enabled: Boolean) {

  def sendEmail() = {
    println("(" + contact_id + ") : " + firstName + " " + lastName + " send Email to : " + email)

  }

}
