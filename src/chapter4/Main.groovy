package chapter4

/**
 * Created by hyungsok7 on 2016. 9. 25..
 */
class Main {
    // init
    static {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1, "Lee", "Kevin", "hyungsok7@gmail.com", false));
        contacts.add(new Contact(2, "Kim", "Rechard", "hyungsok7@gmail.com", false));
        contacts.add(new Contact(3, "Choi", "Kim", "hyungsok7@gmail.com", false));
        contacts.add(new Contact(4, "Lee", "Charlee", "hyungsok7@gmail.com", false));
        contacts.add(new Contact(5, "Yoon", "Cadon", "hyungsok7@gmail.com", false));
        contacts.add(new Contact(6, "Zoo", "Bernard", "hyungsok7@gmail.com", false));

        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer(
                    1000 + i,
                    "name" + i,
                    "state" + i,
                    "domain" + i,
                    i % 2 == 0,
                    new Contract(Calendar.getInstance(), true),
                    contacts
            );
            Customer.allCustomers.add(customer);
        }
    }


    /**
     * 고객들에게 이메일을 발송
     ● 이용 고객이다(Customer.enabled가 true).
     ● 계약이 유효하다(Contract.enabled가 true).
     ● 계약은 만료 전이다.
     ● 현재 연락처가 있다(Contact.enabled가 true).
     * @param msg
     */
    public static void sendEnabledCustomersEmails(String msg) {
        Customer.allCustomers.findAll {
            customer -> customer.enabled && customer.contract.enabled
        }.each { customer ->
            customer.contacts.findAll {
                contact -> contact.enabled
            }.each { contact ->
                contact.sendEmail(msg)
            }
        }
    }

    /**
     * 반복코드가 많아서 좀 더 함수형답게 작성하기 위해서 클로져 생성함
     *
     * @param cls
     */
    public static void eachEnabledContact(Closure cls) {
        Customer.allCustomers.findAll { customer ->
            customer.enabled && customer.contract.enabled
        }.each { customer ->
            customer.contacts.each(cls)
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
        eachEnabledContact({ contact -> contact.sendEmail(msg)})
        println("--------------------------------------------------")

        def ids = new ArrayList<Integer>()
        for (index in 0..1) {
            ids.add(1000 + index)
        }
        Customer.setContractForCustomerList(ids, false)
                .each {customer -> println("contract >> " + customer.name + " : " + customer.contract.enabled)}
    }
}


