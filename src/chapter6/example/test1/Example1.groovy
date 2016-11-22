package chapter6.example.test1

class Customer {
    final Integer id
    final Boolean enabled

    public Customer(id, enabled) { this.id = id; this.enabled = enabled; }
}

class CustomerContainer {
    public List<Customer> customers = []

    /**
     * 끊임없이 컨테이너를 변경하면서 다수의 쓰레드가 동작 하는 환경에서 런타임은 매우 느려짐
     * 컨테이너가 새로 고침 할때마다 onlyEnabled 필드에 처음 접근하는 다른 쓰레드는 전부 차단되기 때문
     */
    @Lazy
    public volatile List<Customer> onlyEnabled = { customers.findAll { customer -> customer.enabled } }()

    public CustomerContainer() { this([]) }

    public CustomerContainer(customers) { this.customers = customers }

    def addCustomer(c) {
        new CustomerContainer(customers.plus(customers.size(), [c]))
    }

    def removeCustomer(c) {
        new CustomerContainer(customers.findAll { customer -> customer.id != c.id })
    }
}

class Example1 {
    static void main(String[] args) {
        def cc = new CustomerContainer()
        cc = cc.addCustomer(new Customer(1, true))
        cc = cc.addCustomer(new Customer(2, false))
        println(cc.customers)
    }
}
