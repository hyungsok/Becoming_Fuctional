package design_patten.template

class CustomerBlocksWithProtection {
    def plan, checkCredit, checkInventory, ship

    def CustomerBlocksWithProtection() {
        plan = []
    }
    def process() {
        checkCredit?.call()
        checkInventory?.call()
        ship?.call()
    }
}
