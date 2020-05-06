package machine
import java.util.Scanner

// This task cries for some classes, but they have not been introduced yet
// I'll do my best with google searches on kotlin classes
class MachineState (val water: Int, val milk: Int, val beans: Int, val cups: Int, val cash: Int) {}

class CoffeeRequest (val water: Int, val milk: Int, val beans: Int, val cash: Int) {}

class FillRequest (val water: Int, val milk: Int, val beans: Int, val cups: Int) {}

class CoffeeMachine (var state: MachineState) {
    fun printStatus() {
        println("The coffee machine has:")
        println("${this.state.water} of water")
        println("${this.state.milk} of milk")
        println("${this.state.beans} of coffee beans")
        println("${this.state.cups} of disposable cups")
        println("${this.state.cash} of money")
    }

    fun processCoffeeRequest(coffeeRequest: CoffeeRequest) {
        when {
            this.state.water < coffeeRequest.water -> println("Sorry, not enough water!")
            this.state.milk < coffeeRequest.milk -> println("Sorry, not enough milk!")
            this.state.beans < coffeeRequest.beans -> println("Sorry, not enough coffee beans!")
            this.state.cups < 1 -> println("Sorry, not enough disposable cups!")
            else -> {
                this.state = MachineState(this.state.water - coffeeRequest.water,
                    this.state.milk - coffeeRequest.milk,
                    this.state.beans - coffeeRequest.beans,
                    this.state.cups - 1,
                    this.state.cash + coffeeRequest.cash
                )
                println("I have enough resources, making you a coffee!")
            }
        }
    }

    fun processFillRequest(fillRequest: FillRequest) {
        this.state = MachineState(this.state.water + fillRequest.water,
                this.state.milk + fillRequest.milk,
                this.state.beans + fillRequest.beans,
                this.state.cups + fillRequest.cups,
                this.state.cash
        )
    }

    fun processTakeRequest() {
        println("I gave you ${this.state.cash}")
        this.state = MachineState(this.state.water,
                this.state.milk,
                this.state.beans,
                this.state.cups,
                0
        )
    }

    val espressoRequest = CoffeeRequest(250, 0, 16, 4)
    val latteRequest = CoffeeRequest(350, 75, 20, 7)
    val cappuccinoRequest = CoffeeRequest(200, 100, 12, 6)
}

fun processBuy(coffeeMachine: CoffeeMachine, scanner: Scanner) {
    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
    val coffeeRequest = when (scanner.next()) {
        "1" -> coffeeMachine.espressoRequest
        "2" -> coffeeMachine.latteRequest
        "3" -> coffeeMachine.cappuccinoRequest
        "back" -> return
        else -> null
    }
    if (coffeeRequest == null) {
        println("Unknown buy request")
    } else {
        coffeeMachine.processCoffeeRequest(coffeeRequest)
    }
}

fun processFill(coffeeMachine: CoffeeMachine, scanner: Scanner) {
    print("Write how many ml of water do you want to add: ")
    val water = scanner.nextInt()
    print("Write how many ml of milk do you want to add: ")
    val milk = scanner.nextInt()
    print("Write how many grams of coffee beans do you want to add: ")
    val beans = scanner.nextInt()
    print("Write how many disposable cups of coffee do you want to add: ")
    val cups = scanner.nextInt()
    coffeeMachine.processFillRequest(FillRequest(water, milk, beans, cups))
}

fun main() {
    val scanner = Scanner(System.`in`)
    val coffeeMachine = CoffeeMachine(MachineState(400, 540, 120, 9, 550))
    while (true) {
        print("Write action (buy, fill, take, remaining, exit): ")
        when (scanner.next()) {
            "buy" -> processBuy(coffeeMachine, scanner)
            "fill" -> processFill(coffeeMachine, scanner)
            "take" -> coffeeMachine.processTakeRequest()
            "remaining" -> coffeeMachine.printStatus()
            "exit" -> return
            else -> println("Command not recognized. Spilling coffee all around")
        }
    }
}
