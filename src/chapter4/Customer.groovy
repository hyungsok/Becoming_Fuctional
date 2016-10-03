package chapter4

/**
 * Created by hyungsok7 on 2016. 9. 25..
 */
public class Customer {

    static public List<Customer> allCustomers = new ArrayList<Customer>();

    public final Integer id = 0;
    public final String name = "";
    public final String state = "";
    public final String domain = "";
    public final Boolean enabled = true;
    public final Contract contract = null;
    public final List<Contact> contacts = new ArrayList<Contact>();

    public Customer(Integer id,
                    String name,
                    String state,
                    String domain,
                    Boolean enabled,
                    Contract contract,
                    List<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.domain = domain;
        this.enabled = enabled;
        this.contract = contract;
        this.contacts = contacts;
    }

    static def EnabledCustomer = { customer -> customer.enabled == true }
    static def DisabledCustomer = { customer -> customer.enabled == false }

    public static List<String> getDisabledCustomerNames() {
        Customer.allCustomers.findAll(DisabledCustomer).collect({cutomer -> cutomer.name })
    }

    public static List<String> getEnabledCustomerStates() {
        Customer.allCustomers.findAll(EnabledCustomer).collect({cutomer -> cutomer.state})
    }

    public static List<String> getEnabledCustomerDomains() {
        Customer.allCustomers.findAll(EnabledCustomer).collect({cutomer -> cutomer.domain})
    }

    public static List<String> getEnabledCustomerSomeoneEmail(String someone) {
        Customer.allCustomers.findAll(EnabledCustomer).collect({cutomer -> someone + "@" + cutomer.domain})
    }

    public static ArrayList<Customer> getCustomerById(ArrayList<Customer> inList, final Integer id) {
        inList.findAll({customer -> customer.id == id })
    }

    public static def getCustomerById(Integer customerId) {
        Customer.allCustomers.findAll({ customer -> customer.id == customerId })
    }

    public static List<Customer> setContractForCustomerList(List<Integer> ids, Boolean status) {
        Customer.allCustomers.collect { customer ->
            if(ids.indexOf(customer.id) >= 0) {
                new Customer(
                        customer.id,
                        customer.name,
                        customer.state,
                        customer.domain,
                        customer.enabled,
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





}
