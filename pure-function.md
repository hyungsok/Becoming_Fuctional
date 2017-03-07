# 순수함수 (Pure Function)
> 함수는 주어진 입력으로 계산하는 것 이외에 프로그램의 실행에 영향을 미치지 않아야 하며, 이를 부수 효과(side effect)가 없어야 한다고 한다. 이러한 함수를 순수 함수라고 한다. 이론은 쉬우나 구현은 조금은 다른것 같다. 
* 순수 함수는 참조 투명성을 보장. 입력값이 같다면 결과값도 항상 같다.
* 스레드가 다루는 데이터 불변이고 사용하는 함수가 순수하다면, 스레드가 아무리 많더라도 문제가 되지 않음
* 이런 이유로 함수형 프로그래밍은 멀티스레딩에 강하다. 

# 입력에 따라 출력이 달라진다. 
* 인자가 같다면 순수 함수의 결과는 일정해야 한다. (참조투명성)
* 함수의 결과는 오직 입력된 파라미터에만 의존해야한다. 함수 외부의 어떤 값에도 의존하지 않는다. 
 * 전역변수, 파일이나 네트워크로 부터 읽지 않아야 한다. 
 
``` java
package chapter3;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    // 모든 고객 정보리스트
    static public ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public Integer id = 0;
    public String name = "";
    public String address = "";
    public String state = "";
    public String primaryContact = "";
    public String domain = "";
    public Boolean enabled = true;
    public Contract contract;

    public Customer() {
    }

    public interface Function1<A1, B> {
        public B call(A1 in1);
    }

    /**
     * 초기화
     */
    static {
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customer.name = "name : " + i;
            customer.address = "address : " + i;
            customer.state = "state : " + i;
            customer.primaryContact = "primaryContact : " + i;
            customer.domain = "domain : " + i;
            customer.enabled = i % 2 == 0;
            allCustomers.add(customer);
        }
    }

    public Customer setCustomerId(Integer customer_id) {
        this.id = customer_id;
        return this;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public Customer setState(String state) {
        this.state = state;
        return this;
    }

    public Customer setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public Customer setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Customer setContract(Contract contract) {
        this.contract = contract;
        return this;
    }

    /**
     * 고객정보를 가지고오는 정보 클래스
     */
    public static List<String> getEnabledCustomerAddresses() {
        return getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.address;
            }
        });
    }

    public static List<String> getEnabledCustomerNames() {
        return getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.name;
            }
        });
    }

    public static List<String> getEnabledCustomerStates() {
        return getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.state;
            }
        });
    }

    public static List<String> getEnabledCustomerPrimaryContacts() {
        return getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.primaryContact;
            }
        });
    }

    public static List<String> getEnabledCustomerDomains() {
        return getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.domain;
            }
        });
    }

    public static <B> List<B> getEnabledCustomerField(Function1<Customer, B> func) {
        ArrayList<B> outList = new ArrayList<B>();
        for (Customer customer : Customer.allCustomers) {
            if (customer.enabled) {
                outList.add(func.call(customer));
            }
        }
        return outList;
    }

    /**
     * id에 해당하는 고객이 존재하면 반환하고, 그렇지 않으면 null을 반환하는 간단한 메소드
     *
     * @param customer_id
     * @return
     */
    public static Customer getCustomerById(Integer customer_id) {
        for (Customer customer : Customer.allCustomers) {
            if (customer.id == customer_id) {
                return customer;
            }
        }
        // 이걸 대체 무슨 의미로 받아들여야 할까요?
        //  - 에러인가? 못찾았다는 것인가?
        return null;
    }

    /**
     * 프로그램 오류는 아닌데도 호출자에게 ‘고객을 찾지 못했으니 예외를 던지는 거 야’라고 말하는 건 좀 이상하므로 위의 getCustomerById 보완
     *
     * @param customer_id
     * @return
     */
    public static ArrayList<Customer> getCustomerListById(Integer customer_id) {
        ArrayList<Customer> outList = new ArrayList<Customer>();
        for (Customer customer : Customer.allCustomers) {
            if (customer.id == customer_id) {
                outList.add(customer);
            }
        }
        return outList;
    }

    /**
     * 일급함수이지만 순수함수는 아니다. 참조된 allCustomers값에 의해서 변경될 소지가 있기 때문이다
     *
     * @param func
     * @return
     */
    public static ArrayList<Customer> filter(Function1<Customer, Boolean> func) {
        ArrayList<Customer> outList = new ArrayList<Customer>();
        for (Customer customer : Customer.allCustomers) {
            if (func.call(customer)) {
                outList.add(customer);
            }
        }
        return outList;
    }

    /**
     * filter 메소드를 이용해서 getCustomerListById 개선한 메소드
     * 함수를 더 작은 순수 함수들로 나누어 작성하면 코드의 전반적인 기능을 잘 이해하고 파악하는 데 도움이 될 수 있음
     *
     * @param customer_id
     * @return
     */
    public static ArrayList<Customer> getCustomerListFilterById(final Integer customer_id) {
        return Customer.filter(new Function1<Customer, Boolean>() {
            public Boolean call(Customer customer) {
                return customer.id == customer_id;
            }
        });
    }

    /**
     *
     * @param function1
     * @param function2
     * @param <B>
     * @return
     */
    public static <B> List<B> getField(Function1<Customer, Boolean> function1, Function1<Customer, B> function2) {
        ArrayList<B> outList = new ArrayList<B>();
        for (Customer customer : Customer.filter(function1)) {
            outList.add(function2.call(customer));
        }
        return outList;
    }

    // 테스트
    public static void main(String[] args) {
        List<String> addresses = getEnabledCustomerAddresses();
        for (String address : addresses) {
            System.out.print(address + " ");
        }
        System.out.println();
        List<String> names = getField(
                new Function1<Customer, Boolean>() {
                    @Override
                    public Boolean call(Customer in1) {
                        return in1.enabled;
                    }
                },
                new Function1<Customer, String>() {
                    @Override
                    public String call(Customer in1) {
                        return in1.name;
                    }
                });
        for (String name : names) {
            System.out.print(name + " ");
        }
    }


}
```

# 함수를 순수하게 
``` java
/**
 * 순수함수 컨셉으로 작업한 클래스
 */
public class FunctionalConcepts {

    private FunctionalConcepts() {
    }

    /**
     * @param inList
     * @param customer_id
     * @return
     */
    public static ArrayList<Customer> getCustomerById(ArrayList<Customer> inList, final Integer customer_id) {
        return filter(inList, new Function1<Customer,
                Boolean>() {
            public Boolean call(Customer customer) {
                return customer.id == customer_id;
            }
        });
    }

    /**
     * @param customer_id
     */
    public static void setContractDisabledForCustomer(ArrayList<Customer> inList, Integer customer_id) {
        for (Customer customer : inList) {
            if (customer.id == customer_id) {
                customer.contract.enabled = false;
            }
        }
    }

    /**
     * @param customer_id
     */
    public static void setContractEnabledForCustomer(ArrayList<Customer> inList, Integer customer_id) {
        for (Customer customer : getCustomerById(inList, customer_id)) {
            customer.contract.enabled = true;
        }
    }

    /**
     *
     * @param inList
     * @param customer_id
     * @param status
     */
    public static void setContractForCustomer(ArrayList<Customer> inList, Integer customer_id, final Boolean status) {
        foreach(getCustomerById(inList, customer_id),
                new Foreach1<Customer>() {
                    public void call(Customer customer) {
                        customer.contract.enabled = status;
                    }
                });
    }

    /**
     *
     * @param customer_id
     * @param status
     * @return
     */
    public static List<Contract> setContractForCustomer(Integer customer_id, final Boolean status) {
        return map(getCustomerById(Customer.allCustomers, customer_id), new Function1<Customer, Contract>() {
                    public Contract call(Customer customer) {
                        return customer.contract.setEnabled(status);
                    }
                }
        );
    }

    /**
     * @param inList
     * @param func
     * @param <A1>
     * @param <B>
     * @return
     */
    public static <A1, B> List<B> map(List<A1> inList, Function1<A1, B> func) {
        ArrayList<B> outList = new ArrayList<B>();
        for (A1 obj : inList) {
            outList.add(func.call(obj));
        }
        return outList;
    }

    /**
     * @param inList
     * @param func
     * @param <A>
     */
    public static <A> void foreach(ArrayList<A> inList, Foreach1<A> func) {
        for (A obj : inList) {
            func.call(obj);
        }
    }

    /**
     * @param inList
     * @param func
     * @param <A>
     * @return
     */
    public static <A> ArrayList<A> filter(ArrayList<A> inList, Function1<A, Boolean> func) {
        ArrayList<A> outList = new ArrayList<A>();
        for (A obj : inList) {
            if (func.call(obj)) {
                outList.add(obj);
            }
        }
        return outList;
    }

    /**
     * @param inList
     * @param function1
     * @param function2
     * @param <B>
     * @return
     */
    public static <B> List<B> getField(ArrayList<Customer> inList,
                                       Function1<Customer, Boolean> function1,
                                       Function1<Customer, B> function2) {
        ArrayList<B> outList = new ArrayList<B>();
        for (Customer customer : filter(inList, function1)) {
            outList.add(function2.call(customer));
        }
        return outList;
    }

}
```

## 부수효과 (인자로 전달된 객체 필드를 건드는 일)
 * 함수의 외부의 변수를 변경하거나 파일이나 네트워크로 데이터를 내보내지 않는다. 
 * 예외도 부수효과이다.
 * 부수효과를 제거해야 순수함수요건이 완성됨
 * 순수 함수에 가까울수록 문제를찾기가 수월하게 됨. 쉬운 테스트 가능


# 정리하기
 * 고차 함수로 코드 중복을 줄이고 순수 함수를 만들면 테스트하기 좋아지고 함수의 이해도가 높아진다.
 * 이책에서는 문법이 자바와 유사하고 자바로 코딩할 수 있다는 점을 들어 그루비를 대체 언어로 선정.
   ( filter, map에대응되는 findAll, collect 고차함수를 내장하였음 )


## 그루비란 
[](https://hyungsok.gitbooks.io/becoming-fuctional/content/groovy.html)


# 그루비 문법에 맞게 리팩토링

```groovy 
// 널 체크가 필요없는 안전한 방법
def getCustomerById(Integer customerId) { 
   Customer.allCustomers
   .findAll({ customer -> customer.id == customerId })
}

// id와 동일한 고객 리스트를 가지고와서 그 고객상태를 false 변경하고 계약내용을 확인하는 함수 
def setContractForCustomer(Integer customerId) { 
   Customer.allCustomers.findAll({ 
       customer -> customer.id == customerId }).collect({ 
       customer -> customer.contract.setEnabled(false) }).each({ 
       contract -> println contract })
}
```

```java 
import java.util.ArrayList;
import java.util.List;

public class Customer {

    static public ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public Integer id = 0;
    public String name = "";
    public String address = "";
    public String state = "";
    public String primaryContact = "";
    public String domain = "";
    public Boolean enabled = true;
    public Contract contract;

    public Customer() {}


    public Customer setCustomerId(Integer customer_id) {
        this.customer_id = customer_id;
        return this;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public Customer setState(String state) {
        this.state = state;
        return this;
    }

    public Customer setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public Customer setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Customer setContract(Contract contract) {
        this.contract = contract;
        return this;
    }

    static def EnabledCustomer = { customer -> customer.enabled == true }
    static def DisabledCustomer = { customer -> customer.enabled == false }

    public static List<String> getDisabledCustomerNames() {
        Customer.allCustomers.findAll(DisabledCustomer).collect({ cutomer ->
            cutomer.name
        })
    }

    public static List<String> getEnabledCustomerStates() {
        Customer.allCustomers.findAll(EnabledCustomer).collect({ cutomer ->
            cutomer.state
        })
    }

    public static List<String> getEnabledCustomerDomains() {
        Customer.allCustomers.findAll(EnabledCustomer).collect({ cutomer ->
            cutomer.domain
        })
    }

    public static List<String> getEnabledCustomerSomeoneEmail(String someone) {
        Customer.allCustomers.findAll(EnabledCustomer).collect({ cutomer ->
            someone + "@" + cutomer.domain
        })
    }

    public static ArrayList<Customer> getCustomerById(ArrayList<Customer> inList, final Integer customer_id) {
        inList.findAll({ customer -> customer.customer_id == customer_id })
    }
}
```
