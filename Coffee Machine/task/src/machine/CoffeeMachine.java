package machine;

import java.util.Scanner;

public class CoffeeMachine {

    // one cup of coffee made on this coffee machine contains 200 ml of water, 50 ml of milk, and 15 g of coffee beans.
    static final int WATER = 200;
    static final int MILK = 50;
    static final int COFFEE_BEANS = 15;
    private int waterCapacity;
    private int milkCapacity;
    private int coffeeBeansCapacity;
    static int MAX_COFFEE_CUPS;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water the coffee machine has:");
        int water = scanner.nextInt();
        System.out.println("Write how many ml of milk the coffee machine has:");
        int milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        int coffeeBeans = scanner.nextInt();
        CoffeeMachine machine = new CoffeeMachine(water, milk, coffeeBeans);
        System.out.println("Write how many cups of coffee you will need:");
        int cups = scanner.nextInt();
        machine.estimateIngredients(cups);
    }

    public CoffeeMachine(int waterCapacity, int milkCapacity, int coffeeBeansCapacity) {
        this.waterCapacity = waterCapacity;
        this.milkCapacity = milkCapacity;
        this.coffeeBeansCapacity = coffeeBeansCapacity;
        this.setMaxCoffeeCups(WATER, MILK, COFFEE_BEANS);
    }

    private void setMaxCoffeeCups(int waterNeeded, int milkNeeded, int coffeeBeansNeeded) {
        int waterRemain = this.getWaterCapacity() / waterNeeded;
        int milkRemain = this.getMilkCapacity() / milkNeeded;
        int coffeeBeansRemain = this.getCoffeeBeansCapacity() / coffeeBeansNeeded;
        MAX_COFFEE_CUPS = Math.min(Math.min(waterRemain, milkRemain), coffeeBeansRemain);
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
}
