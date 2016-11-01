package chapter5

import chapter4.Constants
import chapter4.Customer

/**
 * Created by hyungsok7 on 2016. 9. 25..
 *
 * 5장. 재귀함수
 *  - 불변 변수는 '변경할 수 없다는 분명한 단점이 존재
 *  - 리스트 원소값 하나를 변경하거나 if문을 통한 변수값을 대입하는 작업이 까다로움
 *  - 위의 까다로운 문제점을 해결하기 위해 제귀가 필요
 *
 * 제귀함수의 제한사항
 *  - 재귀 호출의 깊이 즉 함수가 자기 자신을 호출하는 횟수 제한이 있음
 *   1. 메모리 용량 ( 함수를 한번 호출할때마다 스택에 프레임이 생성 - 재귀 호출이 여러 차례 반복되면서 스택 공간을 소진해버리기 때문)
 *   2. 컴파일러나 인터프리터 자체에 제한
 *   3. 호출 횟수가 많아지면 돌아갈 곳의 주소 값을 저장하고 있는 스택이 넘치거나 프로그램의 실행 속도가 느려지는 단점
 *
 * 꼬리제귀 : 재귀의 탈을 쓰고 있는 반복문
 *
 * 1. 그루비 : 릴 트램펄린 trampolining 기법으로 처리
 * 2. 스칼라 ( 그루비는 아래와같은 건 없음 )
 *  @tailrec : 꼬리재귀함수 지정, 구현을 tail recursive하게 구현하지 않았을 경우에는 에러를 발생
 *   - 컴파일러가 꼬리재귀를 인식하고 최적화하면서 일반 재귀가 가지는 단점을 없애줌
 */
class MainGroovy {

    /**
     * 현재 연락처가 없는 이용 고객수를 세는 함수
     *  - 중복된 findAll을 한데 모아 한번만 순회하도록 처리
     *  - 리스트의 크기를 알려면 새로운 리스트를 직접 생성
     *  - 굉장히 많은 고객의 수를 고려하면 폐기할 리스트를 생성하는데 들어가는 시간이 너무나 낭비인 코드
     *
     * @param customers
     * @return
     */
    public static int countEnabledCustomersWithNoEnabledContacts(List<Customer> customers) {
        return customers.findAll({
                    customer -> customer.enabled && (customer.contacts.find({ contact -> contact.enabled }) == null)
                }).size()
    }

    /**
     * 현재 연락처가 없는 이용 고객수를 재귀적으로 세는 함수
     *  - 일반재귀 방식
     *
     * @param customers
     * @return
     */
    public static int countEnabledCustomersWithNoEnabledContactsNormal(List<Customer> customers) {
        if(customers.isEmpty()) {
            return 0
        } else {
            int addition = (customers.head().enabled &&
                    (customers.head().contacts.find({
                        contact -> contact.enabled
                    }) == null)
            ) ? 1 : 0
            return addition + countEnabledCustomersWithNoEnabledContactsNormal(customers.tail())
        }
    }

    /**
     * 현재 연락처가 없는 이용 고객수를 재귀적으로 세는 함수
     *  - 꼬리재귀 방식
     *
     * @param customers
     * @param sum
     * @return
     */
    public static int countEnabledCustomersWithNoEnabledContactsTail(List<Customer> customers, int sum) {
        if(customers.isEmpty()) {
            return sum
        } else {
            int addition = (customers.head().enabled &&
                    (customers.head().contacts.find({ contact ->
                        contact.enabled
                    }) == null)
            ) ? 1 : 0
            return countEnabledCustomersWithNoEnabledContactsTail(customers.tail(), sum + addition)
        }
    }

    /**
     * 현재 연락처가 없는 이용 고객수를 세는 함수
     *  - 꼬리 재귀, 트램펄린 기법 이용
     *
     *  trampoline 원리
     *  1. trampoline()은 함수를 TrampolineClosure이라는 wrapper로 감쌈
     *  2. TrampolineClosure를 실행하면 countEnabledCustomersWithNoEnabledContacts(Customer.allCustomers, 0)를 실행
     *  4. countEnabledCustomersWithNoEnabledContacts 실행결과를 TrampolineClosure이 반환
     *  5. 새로운 TrampolineClosure 함수를 실행
     *  6. 더는 TrampolineClosure이 반환되지 않을때까지 같은 과정(1~5번)을 반복
     *
     *
     * 꼬리 재귀로 깊이 이슈는 해결했지만, 문법적으로 그루비는 일단 변수를 정의하고 변수에 할당한 클로저에 .trampoline()을 호출해야 하는 문제가 있으서 귀찮음.
     */
    public static def countEnabledCustomersWithNoEnabledContactsTrampoline = { List<Customer> customers, int sum ->
        if(customers.isEmpty()) {
            return sum
        } else {
            int addition = (customers.head ().enabled &&
                    (customers.head ().contacts.find ({ contact ->
                        contact.enabled
                    }) == null)
            ) ? 1 : 0
            return countEnabledCustomersWithNoEnabledContactsTrampoline.trampoline(customers.tail (), sum + addition)
        }
    }.trampoline()

    /**
     * 한빛증권 사세가 확장되면서 팀장님은 현재 연락처가 없는 이용 고객의 인원수를 세어보라고 지시
     *
     * @param args
     */
    static void main(String[] args) {
        println countEnabledCustomersWithNoEnabledContacts(Constants.allCustomers)
        println countEnabledCustomersWithNoEnabledContactsNormal(Constants.allCustomers)
        println countEnabledCustomersWithNoEnabledContactsTail(Constants.allCustomers, 0)
        println countEnabledCustomersWithNoEnabledContactsTrampoline(Constants.allCustomers, 0)
    }
}


