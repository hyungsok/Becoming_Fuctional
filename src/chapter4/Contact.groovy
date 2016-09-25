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

    public void sendEmail(String msg) {
        println("--------------------------------------------------")
        msg = String.format(msg, firstName, lastName)
        println(msg)
    }
}
