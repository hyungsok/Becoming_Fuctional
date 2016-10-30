package chapter4

import scala.Int

/**
 * 고객
 */
public class Customer {

    // 식별자: 단순 일련번호입니다.
    public final Integer id;
    // 이름
    public final String name;
    // 국가
    public final String state;
    // 도메인
    public final String domain;
    // 이용여부 ( true : 이용고객 , false : 휴면고객
    public final Boolean enabled;

    // 계약정보
    public final Contract contract;
    // 연락처
    public final List<Contact> contacts;

    public Customer(Integer id,
                    String name,
                    String state,
                    String domain,
                    Boolean enabled,
                    Contract contract,
                    List<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.domain = domain;
        this.enabled = enabled;

        this.contract = contract;
        this.contacts = contacts;
    }

}
