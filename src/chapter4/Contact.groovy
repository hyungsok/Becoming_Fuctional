package chapter4

/**
 * 연락처
 */
class Contact {

    // 연락처 ID
    public final Integer id;
    // 이름
    public final String firstName;
    // 성씨
    public final String lastName;
    // 이메일 주소
    public final String email;
    // 사용여부
    public final Boolean enabled;

    public Contact(Integer id,
                   String firstName,
                   String lastName,
                   String email,
                   Boolean enabled) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enabled = enabled;
    }

    /**
     * 현재 연락처 이메일 보내기
     *
     * @param msg
     */
    public void sendEmail(String msg) {
        println("--------------------------------------------------")
        msg = String.format(msg, firstName, lastName)
        println(msg)
    }
}
