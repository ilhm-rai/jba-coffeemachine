package machine;

import java.util.Scanner;

public class CoffeeMachine {

    // one cup of coffee made on this coffee machine contains 200 ml of water, 50 ml of milk, and 15 g of coffee beans.
    static final int water = 200; // 200 ml
    static final int milk = 50; // 50 ml
    static final int coffeeBeans = 15; // 15g

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cups = scanner.nextInt();
        calculateIngredients(cups);
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

    static void calculateIngredients(int cups) {
        System.out.println("Write how many cups of coffee you will need:");
        System.out.printf("For %d cups of coffee you will need:%n", cups);
        System.out.printf("%d ml of water%n", water * cups);
        System.out.printf("%d ml of milk%n", milk * cups);
        System.out.printf("%d g of coffee beans%n", coffeeBeans * cups);
    }
}
