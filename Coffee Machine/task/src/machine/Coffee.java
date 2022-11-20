package machine;

public enum Coffee {

    ESPRESSO(250, 0,16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    private final int water; // in ml
    private final int milk; // in ml
    private final int coffeeBeans; // in g
    private final int price; // in $

    Coffee(int water, int milk, int coffeeBeans, int price) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.price = price;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getPrice() {
        return price;
    }
}
