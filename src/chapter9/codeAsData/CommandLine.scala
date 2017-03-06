package chapter9.codeAsData

case class CommandLineOption(description: String, func: () => Unit)

object CommandLine {

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

  def createCustomer(): Unit = {
    Customer.allCustomers = Customer.createCustomer(
      CommandLine.askForInput("Name"),
      CommandLine.askForInput("State"),
      CommandLine.askForInput("Domain")
    ).toList ::: Customer.allCustomers
  }

  def showCustomerList(): Unit = {
    Customer.allCustomers.foreach(customer =>
      println(customer.name)
    )
  }

  def showContactList(): Unit = {
    Customer.allCustomers.filter({ customer =>
      customer.enabled && customer.contract.enabled
    }).foreach({ customer =>
      customer.contacts.foreach(contact =>
        println(contact.firstName + " : " + contact.email))
    })
  }
}