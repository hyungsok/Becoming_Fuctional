package chapter4
/**
 * Created by hyungsok7 on 2016. 9. 25..
 */
class Main {

    /**
     * 고객들에게 이메일을 발송
     *
     ● 이용 고객이다(Customer.enabled가 true).
     ● 계약이 유효하다(Contract.enabled가 true).
     ● 계약은 만료 전이다.
     ● 현재 연락처가 있다(Contact.enabled가 true).
     * @param msg
     */
    public static void sendEnabledCustomersEmails(String msg) {
        Constants.allCustomers.findAll {
            customer -> customer.enabled && customer.contract.enabled
        }.each {
            customer ->
                customer.contacts.findAll {
                    contact -> contact.enabled
                }.each {
                    contact -> contact.sendEmail(msg)
                }
        }
    }

    /**
     * 반복코드가 많아서 좀 더 함수형답게 작성하기 위해서 클로져 생성함
     *
     * @param cls
     */
    public static void eachEnabledContact(Closure cls) {
        Constants.allCustomers.findAll {
            customer -> customer.enabled && customer.contract.enabled
        }.each {
            customer -> customer.contacts.each(cls)
        }
    }

    /**
     *
     * @param ids
     * @param status
     * @return
     */
    public static List<Customer> getContractForCustomerList(List<Integer> ids, Boolean status) {
        Constants.allCustomers.collect {
            customer ->
                if (ids.indexOf(customer.id) >= 0) {
                    new Customer(
                            customer.id,
                            customer.name,
                            customer.state,
                            customer.domain,
                            customer.enabled,
                            new Contract(
                                    customer.contract.begin_date,
                                    status
                            ),
                            customer.contacts
                    )
                } else {
                    customer
                }
        }
    }
    /**
     * 메인 클래스
     *
     * @param args
     */
    static void main(String[] args) {
        def msg = "안녕하세요, %s %s 고객님\n" +
                "금번 폐사가 새로 출시한 상품이 있어 연락드리게 되었습니다. 한번 사용해보시고 구매를 결정하셔\n" +
                "도 되니 부담없이 02-2128-8731으로 전화주시면 안내해 드리겠습니다.\n" +
                "감사합니다.\n" +
                "고객님의 평생 파트너 ‘한빛증권’"

        sendEnabledCustomersEmails(msg)

        eachEnabledContact({ contact -> contact.sendEmail(msg)})

        getContractForCustomerList([1000, 1001], false)
                .each {customer -> println("contract >> " + customer.name + " : " + customer.contract.enabled)}
    }
}


