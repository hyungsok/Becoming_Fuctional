package chapter7.groovy;

import java.util.List;

public class Contact {

    public final Integer contact_id;
    public final String firstName;
    public final String lastName;
    public final String email;
    public final Boolean enabled;

    public Contact(Integer contact_id, String firstName, String lastName, String email, Boolean enabled) {
        this.contact_id = contact_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enabled = enabled;
    }

    public static List<Customer> setNameAndEmailForContactAndCustomer(Integer customer_id,
                                                                      Integer contact_id,
                                                                      String name,
                                                                      String email) {

        return Customer.updateContactForCustomerContact(customer_id, contact_id, {
                    contact -> new Contact(contact.contact_id, contact.firstName, name, email, contact.enabled) });
    }

    public void sendEmail() {
        System.out.println("Sending Email");
    }

}
