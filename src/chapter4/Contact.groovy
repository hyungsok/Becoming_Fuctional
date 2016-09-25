package chapter4

/**
 * Created by hyungsok7 on 2016. 9. 25..
 */
class Contact {
    public Integer contact_id = 0;
    public String firstName = "";
    public String lastName = "";
    public String email = "";
    public Boolean enabled = true;

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
}
