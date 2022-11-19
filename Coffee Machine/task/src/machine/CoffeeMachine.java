package machine;

import java.util.Scanner;

public class CoffeeMachine {

    // one cup of coffee made on this coffee machine contains 200 ml of water, 50 ml of milk, and 15 g of coffee beans.
    static final int WATER = 200;
    static final int MILK = 50;
    static final int COFFEE_BEANS = 15;
    static int MAX_COFFEE_CUPS;
    private int money;
    private int waterCapacity;
    private int milkCapacity;
    private int coffeeBeansCapacity;
    private int disposableCups;

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();
        machine.start();
    }

    public CoffeeMachine() {
        waterCapacity = 400;
        milkCapacity = 540;
        coffeeBeansCapacity = 120;
        disposableCups = 9;
        money = 550;
    }

    public CoffeeMachine(int waterCapacity, int milkCapacity, int coffeeBeansCapacity) {
        this.waterCapacity = waterCapacity;
        this.milkCapacity = milkCapacity;
        this.coffeeBeansCapacity = coffeeBeansCapacity;
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

    private void setMaxCoffeeCups(int waterNeeded, int milkNeeded, int coffeeBeansNeeded) {
        int waterRemain = waterCapacity / waterNeeded;
        int milkRemain = milkCapacity / milkNeeded;
        int coffeeBeansRemain = coffeeBeansCapacity / coffeeBeansNeeded;
        MAX_COFFEE_CUPS = Math.min(Math.min(waterRemain, milkRemain), coffeeBeansRemain);
    }

    static void printSteps() {
        String[] outputs = new String[] {
                "Starting to make a coffee",
                "Grinding coffee beans",
                "Boiling water",
                "Mixing boiled water with crushed coffee beans",
                "Pouring coffee into the cup",
                "Pouring some milk into the cup",
                "Coffee is ready!"
        };

        for (var output: outputs) {
            System.out.println(output);
        }
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
        showMainMenu();
    }

    public void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            String menu = scanner.next();
            switch (menu) {
                case "buy" -> buyCoffee();
                case "fill" -> fillMachine();
                case "take" -> takeMoney();
                case "remaining" -> printDetail();
                case "exit" -> exit = true;
            }
        }
    }

    public void fillMachine() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add:");
        int water = scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        int milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int coffeeBeans = scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add: ");
        int disposableCups = scanner.nextInt();
        this.setWaterCapacity(this.getWaterCapacity() + water);
        this.setMilkCapacity(this.getMilkCapacity() + milk);
        this.setCoffeeBeansCapacity(this.getCoffeeBeansCapacity() + coffeeBeans);
        this.setDisposableCups(this.getDisposableCups() + disposableCups);
    }

    private void buyCoffee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String flavour = scanner.next();

        Coffee coffee = switch (flavour) {
            case "1" -> Coffee.ESPRESSO;
            case "2" -> Coffee.LATTE;
            case "3" -> Coffee.CAPPUCCINO;
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

    void calculateIngredients(int cups) {
        int waterNeeded = cups * WATER;
        int milkNeeded = cups * MILK;
        int coffeeBeansNeeded = cups * COFFEE_BEANS;

        this.setWaterCapacity(this.getWaterCapacity() - waterNeeded);
        this.setMilkCapacity(this.getMilkCapacity() - milkNeeded);
        this.setCoffeeBeansCapacity(this.getCoffeeBeansCapacity() - coffeeBeansNeeded);

        this.setMaxCoffeeCups(waterNeeded, milkNeeded, coffeeBeansNeeded);
    }

    void estimateIngredients(int cups) {
        if (cups > MAX_COFFEE_CUPS) {
            System.out.printf("No, I can make only %d cup(s) of coffee%n", MAX_COFFEE_CUPS);
        } else {
            calculateIngredients(cups);
            if (MAX_COFFEE_CUPS > 0) {
                System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)", MAX_COFFEE_CUPS);
            } else {
                System.out.println("Yes, I can make that amount of coffee");
            }
        }
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
