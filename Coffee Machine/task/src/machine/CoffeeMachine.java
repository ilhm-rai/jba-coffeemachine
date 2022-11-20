package machine;

import java.util.Scanner;

import static machine.CoffeeMachineState.*;

public class CoffeeMachine {

    private int money;
    private int waterCapacity;
    private int milkCapacity;
    private int coffeeBeansCapacity;
    private int disposableCups;
    private CoffeeMachineState state;

    public CoffeeMachine() {
        this.waterCapacity = 0;
        this.milkCapacity = 0;
        this.coffeeBeansCapacity = 0;
        this.disposableCups = 0;
        this.money = 0;
        this.state = CoffeeMachineState.CHOOSE_ACTION;
    }

    public CoffeeMachine(int waterCapacity, int milkCapacity, int coffeeBeansCapacity, int disposableCups, int money) {
        this.waterCapacity = waterCapacity;
        this.milkCapacity = milkCapacity;
        this.coffeeBeansCapacity = coffeeBeansCapacity;
        this.disposableCups = disposableCups;
        this.money = money;
        this.state = CoffeeMachineState.CHOOSE_ACTION;
    }

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        machine.start();
    }

    public int getWaterCapacity() {
        return waterCapacity;
    }

    public void setWaterCapacity(int waterCapacity) {
        this.waterCapacity = waterCapacity;
    }

    public int getMilkCapacity() {
        return milkCapacity;
    }

    public void setMilkCapacity(int milkCapacity) {
        this.milkCapacity = milkCapacity;
    }

    public int getCoffeeBeansCapacity() {
        return coffeeBeansCapacity;
    }

    public void setCoffeeBeansCapacity(int coffeeBeansCapacity) {
        this.coffeeBeansCapacity = coffeeBeansCapacity;
    }

    public int getDisposableCups() {
        return disposableCups;
    }

    public void setDisposableCups(int disposableCups) {
        this.disposableCups = disposableCups;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public CoffeeMachineState getState() {
        return state;
    }

    public void setState(CoffeeMachineState state) {
        this.state = state;
    }

    public void nextState() {
        state = state.nextState();
    }

    private void printResource() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water%n", waterCapacity);
        System.out.printf("%d ml of milk%n", milkCapacity);
        System.out.printf("%d g of coffee beans%n", coffeeBeansCapacity);
        System.out.printf("%d disposable cups%n", disposableCups);
        System.out.printf("$%d of money%n", money);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (this.getState() != EXIT) {
            showContext();
            String action = scanner.next();
            doAction(action);
        }
    }

    private void showContext() {
        String context = switch (this.getState()) {
            case CHOOSE_ACTION -> "Write action (buy, fill, take, remaining, exit):";
            case BUY -> "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
            case FILL_WATER -> "Write how many ml of water you want to add:";
            case FILL_MILK -> "Write how many ml of milk you want to add:";
            case FILL_COFFEE_BEANS -> "Write how many grams of coffee beans you want to add:";
            case ADD_CUPS -> "Write how many disposable cups you want to add: ";
            case EXIT -> null;
        };
        System.out.println(context);
    }

    private void chooseAction(String action) {
        switch (action) {
            case "buy" -> this.setState(BUY);
            case "fill" -> this.setState(FILL_WATER);
            case "take" -> this.takeMoney();
            case "remaining" -> this.printResource();
            case "exit" -> this.setState(EXIT);
            default -> throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    private void doAction(String input) {
        switch (state) {
            case CHOOSE_ACTION -> this.chooseAction(input);
            case BUY -> {
                if (!input.equals("back")) {
                    this.buyCoffee(Integer.parseInt(input));
                }
                this.nextState();
            }
            case FILL_WATER, FILL_MILK, FILL_COFFEE_BEANS, ADD_CUPS -> {
                this.fillMachine(Integer.parseInt(input));
                this.nextState();
            }
        }
    }

    private void fillMachine(int amount) {
        switch (this.getState()) {
            case FILL_WATER -> this.setWaterCapacity(this.getWaterCapacity() + amount);
            case FILL_MILK -> this.setMilkCapacity(this.getMilkCapacity() + amount);
            case FILL_COFFEE_BEANS -> this.setCoffeeBeansCapacity(this.getCoffeeBeansCapacity() + amount);
            case ADD_CUPS -> this.setDisposableCups(this.getDisposableCups() + amount);
        }
    }

    private void buyCoffee(int chooseFlavour) {
        Coffee coffee = switch (chooseFlavour) {
            case 1 -> Coffee.ESPRESSO;
            case 2 -> Coffee.LATTE;
            case 3 -> Coffee.CAPPUCCINO;
            default -> null;
        };

        if (coffee != null) {
            boolean hasEnoughResources = validateResource(coffee);
            if (hasEnoughResources) {
                this.setWaterCapacity(this.getWaterCapacity() - coffee.getWater());
                this.setMilkCapacity(this.getMilkCapacity() - coffee.getMilk());
                this.setCoffeeBeansCapacity(this.getCoffeeBeansCapacity() - coffee.getCoffeeBeans());
                this.setDisposableCups(this.getDisposableCups() - 1);
                this.setMoney(this.getMoney() + coffee.getPrice());
                System.out.println("I have enough resources, making you a coffee!");
            }
        }
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d%n", this.getMoney());
        this.setMoney(0);
    }

    private boolean validateResource(Coffee coffee) {
        boolean enoughWater = this.getWaterCapacity() > coffee.getWater();
        boolean enoughMilk = this.getMilkCapacity() > coffee.getMilk();
        boolean enoughCoffeeBeans = this.getCoffeeBeansCapacity() > coffee.getCoffeeBeans();
        boolean enoughDisposableCups = this.getDisposableCups() > 0;

        if (!enoughWater) {
            System.out.println("Sorry, not enough water!");
        }
        if (!enoughMilk) {
            System.out.println("Sorry, not enough milk!");
        }
        if (!enoughCoffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
        }
        if (!enoughDisposableCups) {
            System.out.println("Sorry, not enough disposable cups!");
        }

        return enoughWater && enoughMilk && enoughCoffeeBeans && enoughDisposableCups;
    }
}
