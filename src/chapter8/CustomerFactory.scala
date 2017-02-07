package chapter8

import java.util.Calendar

/**
  * Created by hyungsoklee on 2017. 2. 5..
  */
object CustomerFactory {
  /**
    * Customer 객체 제약사항
    *
    * ● 성명 (name)은 필수입니다.
    * ● 국가 (state)는 필수입니다.
    * ● 도메인 (domain)은 필수입니다.
    * ● 이용 여부 (enabled)의 기본값은 true다.
    * ● 계약 (contract)은 오늘 날짜를 기준으로 생성된다.
    * ● 연락처 리스트 (contacts)는 처음에 빈 상태로 만들어진다.
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
