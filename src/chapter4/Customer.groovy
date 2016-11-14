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

    // 데이터 중복을 제거한, 전체 현재 연락처 조회 메소드
    private List<Contact> enabledContacts = null;

    public synchronized List<Contact> getEnabledContacts() {
        if (this.enabledContacts == null) {
            this.enabledContacts = this.contacts.findAll { contact ->
                println "getEnabledContacts() " + contact.id
                contact.enabled
            }
        }
        return this.enabledContacts;
    }

    // 느긋한 멤버로 변경
    // @Lazy
    public volatile List<Contact> enabledContactsLazy = contacts.findAll { contact ->
        println "enabledContactsLazy : " + contact.id
        contact.enabled
    }
}

