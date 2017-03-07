
## 불변성
* 지역변수는 변하지 않는다.
* 전역변수는 레퍼런스만 바뀐다.

## 가변성
* 변수 : 변하는(variable) 저장소

## 함수에 전달한 변숫값 변경
```
def f = "Foo"
def func(obj) {
   // obj와 f는 아무런 연관관계가 없으므로 f값는 그대로 유지 
   obj = "Bar"
}
println f
func(f)
println f

// 출력결과
Foo
Foo
```

```
// 위의 의도를 구현하려면 값을 변경하려는 레퍼런스가 포함된 가변객체를 사용하면 해결
class Foo {
  String str
}
def f = new Foo(str: "Foo")
def func(Foo obj) {
  // 이 함수가의 목적이 불분명하다면 시간이 지나면서 자꾸뒤바뀌는 의문을 풀지 못함 
  obj.str = "Bar"
}
println f.str
func(f)
println f.str

// 출력결과
Foo
Bar
```
## 한빛증권 이메일 보내기 프로젝트 
![](/asserts/immutable-variable_1.png)

### 그루비로 작성한 계약 정보를 수정하는 예제 
```
// 조회한 고객 한 명이 들어 있거나, 또는 텅 비어 있든지 (널 체크가 필요없는 안전한 방법) : findAll 고차함수 
def getCustomerById(Integer customerId) {
  Customer.allCustomers.findAll({ customer ->
    customer.id == customerId
  })
}
```

### 수정된 contract 출력 ( collect, each )
```
Customer.allCustomers.collect({ customer ->
            customer.contract.enabled = false 
            customer.contract
        }).each { contract ->
            println("= contract : " + contract.begin_date.getTime() + " : " + contract.enabled)
        }

[출력결과]
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
= contract : Sun Oct 02 19:40:00 KST 2016 : false
```

## 한빛증권 이메일 프로젝트 
```
안녕하세요, Yoon Cadon 고객님
금번 폐사가 새로 출시한 상품이 있어 연락드리게 되었습니다. 한번 사용해보시고 구매를 결정하셔
도 되니 부담없이 02-2128-8731으로 전화주시면 안내해 드리겠습니다.
감사합니다.
고객님의 평생 파트너 ‘한빛증권’

// 예시 
class Contact {
    public final Integer contact_id;
    public final String firstName;
    public final String lastName;
    public final String email;
    public final Boolean enabled;

    public Contact(Integer contact_id,
                   String firstName,
                   String lastName,
                   String email,
                   Boolean enabled) {

        this.contact_id = contact_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enabled = enabled;
    }

    public static void eachEnabledContact(Closure cls) {
        Customer.allCustomers.findAll { customer ->
            customer.enabled && customer.contract.enabled
        }.each { customer ->
            customer.contacts.each(cls)
        }
    }

    public void sendEmail(String msg) {
        println("--------------------------------------------------")
        msg = String.format(msg, firstName, lastName)
        println(msg)
    }
}

// Main
def msg = "안녕하세요, %s %s 고객님\n" +
                "금번 폐사가 새로 출시한 상품이 있어 연락드리게 되었습니다. 한번 사용해보시고 구매를 결정하셔\n" +
                "도 되니 부담없이 02-2128-8731으로 전화주시면 안내해 드리겠습니다.\n" +
                "감사합니다.\n" +
                "고객님의 평생 파트너 ‘한빛증권’"
eachEnabledContact({ contact -> contact.sendEmail(msg)})

```


## 한빛증권 이메일 요구사항 변경
```
발신: 한빛증권 신상품 소개 <trials@hanbitmall.com>
수신: 김상민 <smkim@hanbit.co.kr>
제목: 신상품 안내
안녕하세요, 김창수 고객님
금번 폐사가 새로 출시한 상품이 있어 연락드리게 되었습니다. 한 번 사용해보시고 구매를 결정하
셔도 되니 부담 없이 02-2128-8731로 전화주시면 안내해 드리겠습니다.
감사합니다.
고객님의 평생 파트너 '한빛증권’
운영팀에 따르면 김창수씨는 김상민 → 김창수로 개명을 했는데, 이메일 본문엔 김창수로, 주소는
김상민 <smkim@hanbit.co.kr>으로 발송이 되었다는 말입니다.
```
![](https://github.com/funfunStudy/study/blob/master/images/4-1.png)
* 동시성 (concurrency) 공유된 변수가 실제로 어느 시점에 어떤 상태라는 보장이 없음을 의미
* 해결책
 * Customer.allCustomers 객체의 모든 접근을 동기화한다. 
    - 모든 접근을 synchronized 블록
    - 병목현상이 발생할 있음 
 * Customer.allCustomers 리스트와 멤버를 변경할 수 없게 한다. 
    - 수정된 멤버를 가진 새로운 리스트를 생성해야되는 불편


# 불변성 
* 데이터베이스의 트랜잭션과 동일한 개념 ( 변경전, 후만 남게 되기 때문 )

```
// Customers 리스트에서 특정 id를 가진 고객들만 매핑하여 수정합니다 
public static List<Customer> setContractForCustomerList(List<Integer> ids, Boolean status) {
// allCustomers 리스트를 순회하면서 현재 id가 ids 리스트에 포함 되어 있는지 
// 체크 포함되어 있으면 Customer 객체를 생성하고Contract 를 제외한 모든 필드를 그대로 복사
        Customer.allCustomers.collect { customer ->
            if(ids.indexOf(customer.id) >= 0) {
                new Customer(
                        customer.id,
                        customer.name,
                        customer.state,
                        customer.domain,
                        customer.enabled,
                        // Contract 부분이 나중에 어떤 식으로든 바뀌게 될지 모르니 변경필요 
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
```

* 반복 로직을 추상화하여 리팩토링
```
public static List<Customer> updateCustomerByIdList(List<Integer> ids, Closure cls) {
    Customer.allCustomers.collect { customer ->
        if(ids.indexOf(customer.id) >= 0) {
            cls(customer)
        } else {
            customer
        }
    }
}

public static List<Customer> updateContact(Integer customer_id, Integer contact_id, Closure cls) {
    updateCustomerByIdList([customer_id], { customer ->
        new Customer(
                customer.id,
                customer.name,
                customer.state,
                customer.domain,
                customer.enabled,
                customer.contract,
                customer.contacts.collect { contact ->
                    if(contact.contact_id == contact_id) {
                        cls(contact)
                    } else {
                        contact
                    }
                }
        )
    })
}

public static List<Customer> updateContractForCustomerList(List<Integer> ids, Closure cls) {
    updateCustomerByIdList(ids, { customer ->
        new Customer(
                customer.customer_id,
                customer.name,
                customer.state,
                customer.domain,
                customer.enabled,
                cls(customer.contract),
                customer.contacts
        )
    })
}
```


# 정리하기
* 불변변수의 장점
 - 버그 추적이 쉬워짐 ( 특정 변수들이 불변이란 사실을 이미 알고 있으므로 ) 
 - 함수 파라미터와 반환값에 대해 더욱 분명히 이해할 수 있음
* 불변변수의 단점
 - 대규모의 코드 리팩토링을 수반하므로 단행하기가 만만치 않음 






