# **함수형 OOP**

> 내가 이해한 함수형 프로그래밍은 최대한 순수함수를 작성하는 것. 가능한 입력과 출력의 관계를 기술하게 끔 추론하는 프로그래밍 인것 같다.
>
> 자연스럽게 객체에 데이터를 입력에 의해 변경하고 그에 맞는 액션을 취하도록 그렇게 개발해왔던 경험으로
>
> 객체를 바꾸지도 않을거면서 왜 가지고 있어야 하는가? 이런 의문을 항상 갖는다.
>
> 이런 의문은 코드를 통해서 조금씩 익히는 수 밖에는 답이 없는것 같다.



> 이번 장은
>
> **"객체는 단지 데이터 집합을 갭슐화 시킨 그릇에 불과하다."**
>
> 객체는 행위를 하는 뭔가가 아니라, 데이터를 담는 그릇\(Contain\)이라는 사실을 이해하도록 설명을 돕고있다.



### **정적 캡슐화**

어떤 양식으로 이메일을 보낼수있도록 이메일 발송 로직을 따로 추출해서 리펙토링을 하여라.

**원본**

```
class Contact(val contact_id: Integer,
    val firstName: String,
    val lastName: String,
    val email: String,
    val enabled: Boolean) {

    def sendEmail() = {
        println("수신:" + email + "\n제목:안녕하세요!\n내용:반나서반갑습니다."
    }    
}
```

**개선**

```
// 새로 작성한 Email 클래스 
case class Email(val address: String, val subject: String, val body: String {
    def send() : Boolean = Email.send(this)
}

// send 함수를 Email 싱글톤 클래스 
object Email {
    def send(msg: Email): Boolean = {
        println("수신:" + msg.address + "\n제목:" + msg.subject + "\n내용:" + msg.body) 
        true
    }
}
```

> 스칼라는 정적멤버\(필드 + 메소드\)를 싱글톤형태의 동반객체 \(Companion object\)에 담아놓으면 동일 소스파일에서 동반 객체와 동반 클래스는 서로 비공개 멤버까지 마음대로 참조할수가 있다. _**\(확인요망 \)**_

```
// 리펙토링한 Contact클래스 
class Contact(val contact_id: Integer,
    val firstName: String,
    val lastName: String,
    val email: String,
    val enabled: Boolean) {

    def sendEmail() = {
        new Email(email, "안녕하세요!", "반나서반갑습니다.").send()
    }    
}
```



### 객체는 그릇이다.

어떤 이메일은 "친애하는~씨께"로 사직해야한다는 추가요청 제시















