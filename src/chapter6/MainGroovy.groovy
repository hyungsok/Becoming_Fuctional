package chapter6

import chapter4.Constants
import chapter4.Customer

/**
 * Created by hyungsok7 on 2016. 11. 13..
 * 6. 조급한 계산과 느긋한 계산
 *
 *
 * 타입 T인 필드 x 앞에 다음과 같이 @Lazy 어노테이션을 붙이면,
 * 내부적으로 그루비 컴파일러는 다음의 게터 메소드를 자동 생성합니다.


 @Lazy T x

private T $x

T getX() {
    if ($x != null)
        return $x
    else {
        $x = new T()
        return $x
    }
}

  volatile? 디테일한 정리.
--------------------------------
 @Lazy volatile T x

private volatile T $x

T getX() {
    T $x_local = $x
    if ($x_local != null)
        return $x_local else {
        synchronized (this) {
            if ($x == null) {
                $x = new T()
            }
            return $x
        }
    }
}

 *
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
        println customer.getEnabledContacts()
        println "----------------------------------"
        println customer.enabledContactsLazy
    }
}



