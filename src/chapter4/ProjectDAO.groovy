package chapter4

/**
 * Created by hyungsok7 on 2016. 10. 30..
 */
class ProjectDAO {

    public static def EnabledCustomer = { customer -> customer.enabled == true }
    public static def DisabledCustomer = { customer -> customer.enabled == false }

    public static List<String> getDisabledCustomerNames(List<Customer> list) {
        list.findAll(DisabledCustomer).collect({ cutomer -> cutomer.name })
    }

    public static List<String> getEnabledCustomerStates(List<Customer> list) {
        list.findAll(EnabledCustomer).collect({ cutomer -> cutomer.state })
    }

    public static List<String> getEnabledCustomerDomains(List<Customer> list) {
        list.findAll(EnabledCustomer).collect({ cutomer -> cutomer.domain })
    }

    public static List<String> getEnabledCustomerSomeoneEmail(List<Customer> list, String someone) {
        list.findAll(EnabledCustomer).collect({ cutomer -> someone + "@" + cutomer.domain })
    }

    public static def getCustomerById(List<Customer> list, Integer customerId) {
        list.findAll({ customer -> customer.id == customerId })
    }

    public static List<Customer> updateCustomerByIdList(List<Integer> ids, Closure cls) {
        Constants.allCustomers.collect { customer ->
            if (ids.indexOf(customer.id) >= 0) {
                cls(customer)
            } else {
                customer
            }
        }
    }

    /**
     *
     * @param customer_id
     * @param contact_id
     * @param cls
     * @return
     */
    public static List<Customer> updateContact(Integer customer_id, Integer contact_id, Closure cls) {
        updateCustomerByIdList([customer_id], {
            customer ->
                new Customer(
                        customer.id,
                        customer.name,
                        customer.state,
                        customer.domain,
                        customer.enabled,
                        customer.contract,
                        customer.contacts.collect { contact ->
                            if (contact.contact_id == contact_id) {
                                cls(contact)
                            } else {
                                contact
                            }
                        }
                )
        })
    }

    /**
     *
     * @param ids
     * @param cls
     * @return
     */
    public static List<Customer> updateContractForCustomerList(List<Integer> customer_ids, Closure cls) {
        updateCustomerByIdList(customer_ids, {
            customer ->
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
