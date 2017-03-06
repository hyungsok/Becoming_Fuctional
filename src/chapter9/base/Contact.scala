package chapter9.base

object Main {
  def main(args: Array[String]): Unit = {
    new Contact(0, "Lee", "Hyungsok", "hyungsok7@naver.com", true).sendEmail();
  }
}

class Contact(val contact_id : Integer, 
              val firstName : String, 
              val lastName : String, 
              val email : String, 
              val enabled : Boolean) {

  def sendEmail() = {
    new Email(email, "My Subject", "My Body").send()
  }
}

case class Email(val address : String, val subject : String, val body : String) {
  def send() : Boolean = {
    println("To: " + address + "\nSubject: " + subject + "\nBody: " + body)
    true
  }
}
