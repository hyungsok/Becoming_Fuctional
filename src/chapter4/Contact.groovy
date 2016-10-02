package chapter4

/**
 * Created by hyungsok7 on 2016. 9. 25..
 */
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

    public void sendEmail(String msg) {
        println("--------------------------------------------------")
        msg = String.format(msg, firstName, lastName)
        println(msg)
    }
}
