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
    public final List<Contract> contacts = new ArrayList<Contract>();

    public Customer(Integer id,
                    String name,
                    String state,
                    String domain,
                    Boolean enabled,
                    Contract contract,
                    List<Contract> contacts) {
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
}
