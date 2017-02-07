package chapter8

import java.util.Calendar

object Contract {
  def setContractForCustomerList(ids: List[Integer], status: Boolean): List[Customer] = {
    Customer.updateContractForCustomerList(ids, { contract =>
      new Contract(contract.begin_date, contract.end_date, status)
    })
  }
}

class Contract(val begin_date: Calendar, val end_date: Calendar, val enabled: Boolean) {
  // 블록문{} : end_date 코드 블록 통한 생성자 오버로드 방식
  def this(begin_date: Calendar, enabled: Boolean) =
    this(begin_date, {
      val c = Calendar.getInstance()
      c.setTimeInMillis(begin_date.getTimeInMillis)
      c.add(Calendar.YEAR, 2)
      c
    }, enabled)

  // 자바로짠 생성자 오버로드 방식 비교
//  public Contract(Calendar begin_date, Boolean enabled) {
//    this.begin_date = begin_date;
//    this.end_date = this.begin_date.getInstance();
//    this.end_date.setTimeInMillis(this.begin_date.getTimeInMillis());
//    this.end_date.add(Calendar.YEAR, 2);
//    this.enabled = enabled;
//  }

}
