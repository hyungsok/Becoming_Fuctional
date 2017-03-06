package design_patten.flyweight

class Computer {
    def type
    def cpu
    def memory
    def hardDrive
    def cd
}

class Desktop extends Computer {
    def driveBays
    def fanWattage
    def videoCard
}

class Laptop extends Computer {
    def usbPorts
    def dockingBay
}

class AssignedComputer {
    def computerType
    def userId

    public AssignedComputer(computerType, userId) {
        this.computerType = computerType
        this.userId = userId
    }
}

@Singleton(strict = false)
class ComputerFactory {
    def types = [:]

    private ComputerFactory() {
        def laptop = new Laptop()
        def tower = new Desktop()
        types.put("MacBookPro6_2", laptop)
        types.put("SunTower", tower)
    }

    def ofType(computer) {
        types[computer]
    }
}


def computerOf = { type ->
    def computerList = [MacBookPro6_2: new Laptop(), SunTower: new Desktop()]
    return computerList[type]
}
def computerOfType = computerOf.memoize()

def bob = new AssignedComputer(ComputerFactory.instance.ofType("MacBookPro6_2"), "Bob")
def steve = new AssignedComputer(ComputerFactory.instance.ofType("MacBookPro6_2"), "Steve")
println(">> booob : " + bob.userId + " / " + bob.computerType)
println(">> steve : " + steve.userId + " / " + steve.computerType)
println("--------------------------------------------------------")

def sally = new AssignedComputer(computerOfType("MacBookPro6_2"), "Sally")
def betty = new AssignedComputer(computerOfType("MacBookPro6_2"), "Betty")
println(">> sally : " + sally.userId + " / " + sally.computerType)
println(">> betty : " + betty.userId + " / " + betty.computerType)
