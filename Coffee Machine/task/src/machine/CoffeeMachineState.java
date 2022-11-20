package machine;

public enum CoffeeMachineState {
    CHOOSE_ACTION, BUY, FILL_WATER, FILL_MILK, FILL_COFFEE_BEANS, ADD_CUPS, EXIT;

    public CoffeeMachineState nextState() {
        return switch (this) {
            case FILL_WATER -> FILL_MILK;
            case FILL_MILK -> FILL_COFFEE_BEANS;
            case FILL_COFFEE_BEANS -> ADD_CUPS;
            default -> CHOOSE_ACTION;
        };
    }
}
