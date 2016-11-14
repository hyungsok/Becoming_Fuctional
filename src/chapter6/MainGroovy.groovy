package chapter6

import chapter4.Constants
import chapter4.Customer

/**
 * Created by hyungsok7 on 2016. 11. 13..
 * 6. 조급한 계산과 느긋한 계산
 *  -
 */
class MainGroovy {

    static void main(String[] args) {
        // 데이터 중복을 제거한, 전체 현재 연락처 조회 메소드
        Customer customer = Constants.allCustomers.get(0);
        println "----------------------------------"
        // No Lazy
        println customer.getEnabledContacts()
        println "----------------------------------"
        // Lazy
        println customer.enabledContactsLazy
        println "----------------------------------"
    }
}

