package machine;

import java.util.Scanner;

import static machine.CoffeeMachineState.*;

public class CoffeeMachine {

    // one cup of coffee made on this coffee machine contains 200 ml of water, 50 ml of milk, and 15 g of coffee beans.
    private int money;
    private int waterCapacity;
    private int milkCapacity;
    private int coffeeBeansCapacity;
    private int disposableCups;
    private CoffeeMachineState state;

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        machine.start();
    }

    public CoffeeMachine(int waterCapacity, int milkCapacity, int coffeeBeansCapacity, int disposableCups, int money) {
        this.waterCapacity = waterCapacity;
        this.milkCapacity = milkCapacity;
        this.coffeeBeansCapacity = coffeeBeansCapacity;
        this.disposableCups = disposableCups;
        this.money = money;
        this.state = CoffeeMachineState.CHOOSE_ACTION;
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

    public void printDetail() {
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
            if (getState().equals(CHOOSE_ACTION)) {
                showActionMenu();
            }
            String action = scanner.next();
            doAction(action);
        }
    }

    public void showActionMenu() {
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
    }

    public void chooseAction(String action) {
        switch (action) {
            case "buy" -> {
                setState(BUY);
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
            }
            case "fill" -> {
                setState(FILL_WATER);
                System.out.println("Write how many ml of water you want to add:");
            }
            case "take" -> takeMoney();
            case "remaining" -> printDetail();
            case "exit" -> setState(EXIT);
            default -> throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    void doAction(String input) {
        switch (state) {
            case CHOOSE_ACTION -> chooseAction(input);
            case BUY -> {
                if (!input.equals("back")) {
                    buyCoffee(Integer.parseInt(input));
                }
                nextState();
            }
            case FILL_WATER, FILL_MILK, FILl_COFFEE_BEANS, FILL_CUPS -> {
                fillMachine(Integer.parseInt(input));
                nextState();
            }
        }
    }

    public void fillMachine(int amount) {
        switch (getState()) {
            case FILL_WATER -> {
                this.setWaterCapacity(this.getWaterCapacity() + amount);
                System.out.println("Write how many ml of milk you want to add:");
            }
            case FILL_MILK -> {
                this.setMilkCapacity(this.getMilkCapacity() + amount);
                System.out.println("Write how many grams of coffee beans you want to add:");
            }
            case FILl_COFFEE_BEANS -> {
                this.setCoffeeBeansCapacity(this.getCoffeeBeansCapacity() + amount);
                System.out.println("Write how many disposable cups you want to add: ");
            }
            case FILL_CUPS -> {
                this.setDisposableCups(this.getDisposableCups() + amount);
            }
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

    boolean validateResource(Coffee coffee) {
        boolean enoughWater = this.getWaterCapacity() > coffee.getWater();
        boolean enoughMilk  = this.getMilkCapacity() > coffee.getMilk();
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
