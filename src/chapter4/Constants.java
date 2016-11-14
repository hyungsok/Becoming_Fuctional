package chapter4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hyungsok7 on 2016. 10. 30..
 */
public class Constants {

    public static List<Customer> allCustomers = new ArrayList();

    // init
    static {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1, "Lee", "Kevin", "Kevin@gmail.com", false));
        contacts.add(new Contact(2, "Kim", "Rechard", "Rechard@gmail.com", false));
        contacts.add(new Contact(3, "Choi", "Kim", "Kim@gmail.com", true));
        contacts.add(new Contact(4, "Lee", "Charlee", "Charlee@gmail.com", false));
        contacts.add(new Contact(5, "Yoon", "Cadon", "Cadon@gmail.com", true));
        contacts.add(new Contact(6, "Zoo", "Bernard", "Bernard@gmail.com", false));

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
            allCustomers.add(customer);
        }
    }
}
