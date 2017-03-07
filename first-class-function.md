# 일급함수 (First-class Function)
* 일급함수란? (일급객체) : 일급 함수는 스스로 객체로 취급되는 함수, 다른 함수에 파라미터로 전달하고 반환받을 수 있고, 그냥 변수로 저장할 수도 있는 함수
 * 변수나 데이터 구조안에 담을 수 있다.
 * 파라미터로 전달 할 수 있다.
 * 반환값(return value)으로 사용할 수 있다.
 * 할당에 사용된 이름과 관계없이 고유한 구별이 가능하다.


* 참고소스 (순수 자바 소스)
https://github.com/jbackfield/BecomingFunctional/tree/master/Chapter2
* 반복 코드  
 * ArrayList 생성 
 * for루프
 * if문
 * return 문
(위의 소스를 리펙토링해보면서 일급함수의 이해를 하도록 구성됨)

* Customer 클래스를 리펙토링 해보자 
https://github.com/jbackfield/BecomingFunctional/blob/master/Chapter2/Introduction/Customer.java

* if else을 활용한 Customer 리펙토링 코드
 * 좋지 않은 리펙토링 철자가 조금이라도 틀리면 오류가 나오는 불안한 구조 

``` java
    public static List<String> getEnabledCustomerField(String field) {
        ArrayList<String> outList = new ArrayList<String>();
        for (Customer customer : Customer.allCustomers) {
            if (customer.enabled) {
                if (field == "name") {
                    outList.add(customer.name);
                } else if (field == "state") {
                    outList.add(customer.state);
                } else if (field == "primaryContact") {
                    outList.add(customer.primaryContact);
                } else if (field == "domain") {
                    outList.add(customer.domain);
                } else if (field == "address") {
                    outList.add(customer.address);
                } else {
                    throw new IllegalArgumentException("잘못된 필드입니다");
                }
            }
        }
        return outList;
    }
```

* 인터페이스를 이용한 Custom 리펙토링 
```java

    private interface ConversionFunction { 
        public String call(Customer customer);
    }

    public static List<String> getEnabledCustomerField(ConversionFunction func) {
        ArrayList<String> outList = new ArrayList<String>();
        for (Customer customer : Customer.allCustomers) {
            if (customer.enabled) {
                outList.add(func.call(customer));
            }
        }
        return outList;
    }
```

* 함수의 구조 (함수명, 파라미터, 몸체, 반환)  

* 제네릭 타이핑(generic typing) 기법으로 좀 더 추상화된 인터페이스로 Customer 리펙토링 
 * Scala Function : http://www.scala-lang.org/api/rc2/scala/Function1.html
```java
public interface Function1<A1, B> {
    B call(A1 in1);
}
// F(A1) = B

public interface Function2<A1, A2, B> {
    B call(A1 in1, A2 in2);
}
// F2(A1, A2) = B

public interface Function4<A1, A2, A3, A4, B> {
    B call(A1 in1, A2 in2, A3 in3, A4 in4);
}
// F4(A1, A2, A3, A4) = B
```
https://github.com/jbackfield/BecomingFunctional/blob/master/Chapter2/FunctionsAsObjects/Customer.java


* 익명함수 : 함수명이 필요하지 않고 스코프 scope가 한정된, 잠깐만 존재하는 함수
 * 람다, 클로져라는 익명함수 존재 

* 람다함수 : 파라미터 리스트, 몸체, 반환부만으로 구성된, 이름이 없는 함수
```java 
public class Customer {
    static public ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public Integer id = 0;
    public String name = "";
    public String address = "";
    public String state = "";
    public String primaryContact = "";
    public String domain = "";
    public Boolean enabled = true;

    public Customer() {
    }

    private interface Function1<A1, B> {
        public B call(A1 in1);
    }

    public static List<String> getEnabledCustomerAddresses() {
        return Customer.getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.addresses;
            }
        });
    }

    public static List<String> getEnabledCustomerNames() {
        return Customer.getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.name;
            }
        });
    }

    public static List<String> getEnabledCustomerStates() {
        return Customer.getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.state;
            }
        });
    }

    public static List<String> getEnabledCustomerPrimaryContacts() {
        return Customer.getEnabledCustomerField(new Function1<Customer, String>() {
            public String call(Customer customer) {
                return customer.primaryContact;
            }
        });
    }

    public static List<String> getEnabledCustomerDomains() {
        return Customer.getEnabledCustomerField(new Function1<Customer, String>() {
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
}
```

```java 
public class Customer {
    static public ArrayList<Customer> allCustomers = new ArrayList<>();
    public Integer id = 0;
    public String name = "";
    public String address = "";
    public String state = "";
    public String primaryContact = "";
    public String domain = "";
    public Boolean enabled = true;

    public Customer() {
    }

    public static List<String> getEnabledCustomerAddresses() {
        return Customer.getEnabledCustomerField(customer -> customer.address);
    }

    public static List<String> getEnabledCustomerNames() {
        return Customer.getEnabledCustomerField(customer -> customer.name);
    }

    public static List<String> getEnabledCustomerStates() {
        return Customer.getEnabledCustomerField(customer -> customer.state);
    }

    public static List<String> getEnabledCustomerPrimaryContacts() {
        return Customer.getEnabledCustomerField(customer -> customer.
                primaryContact);
    }

    public static List<String> getEnabledCustomerDomains() {
        // 좌측인자 -> 우측은 반환값 
        return Customer.getEnabledCustomerField(customer -> customer.domain);
    }
    
    // 직접 구현한 Function1를 java.util.function.Function로 바꾸었음
    // 이처럼 자바 8은 함수형 인터페이스 functional interfaces를 기본 제공
    // https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html
    public static <B> List<B> getEnabledCustomerField(Function<Customer, B> func) {
        ArrayList<B> outList = new ArrayList<B>();
        for (Customer customer : Customer.allCustomers) {
            if (customer.enabled) {
                outList.add(func.apply(customer));
            }
        }
        return outList;
    }
}
```

* 클로져 ( Closer - Closed-Over : 둘러싼 변수 )
 * 클로저는 함수 스코프 밖의 변수를 참조한다는 점만 다르고 람다 함수와 거의 같음
 * 몸체나 파라미터 리스트 어느 쪽에도 존재하지 않는 변수를 참조
 * 인수 이외의 변수를 실행시 환경이 아니라 자신이 정의된 환경(정적 스코프)에서 해결

```javacript
function getAdder(x) {
  return function(y) {
    // x 와 y는 범위 위의 벗어난 영역에 정의된 변수로 없으면 상위레벌로 계속 올라가서 해당 변수를 찾아 해결하도록 함
    return x + y;
  };
}
```

```java
// 자바 : someone 이라는 지역변수를 익명내부클래스에 전달하기 위해서 final이라는 예약어를 이용해 사용
public static List<String> getEnabledCustomerSomeoneEmail(final String someone) {
    return Customer.getEnabledCustomerField(new Function1<Customer, String>() {
        public String call(Customer customer) {
            return someone + "@" + customer.domain; }
    }); }
```

```scala
// 스칼라 
var more = 1 // 함수 스코프영역을 벗어난 리터럴 함수 
val addMore = x => x + more
```


* 그루비를 이용한 get 함수 리팩토링
```groovy
//전체 휴면 고객 성명 조회 
allCustomers.findAll(
{ customer -> customer.enabled == false } ).collect(
{ customer -> customer.name } ) 
```

# 결과 
 * 일급 객체로써의 함수를 통해 코드를 데이터처럼 다룰 수 있으면 표현력이 증대하게 됨 
 * 함수 인자로 딱 한 번 쓰기 위해 매 번 함수를 새롭게 정의하고 이름을 지정으로부터 자유 
 * 익명 함수(혹은 람다)의 도입으로 훨씬 응집성 있고 깨끗한 코드가 작성 가능 

