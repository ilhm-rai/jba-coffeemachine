package machine;

public enum CoffeeMachineState {
    CHOOSE_ACTION, BUY, FILL_WATER, FILL_MILK, FILl_COFFEE_BEANS, FILL_CUPS, EXIT;

    public CoffeeMachineState nextState() {
        return switch (this) {
            case FILL_WATER -> FILL_MILK;
            case FILL_MILK -> FILl_COFFEE_BEANS;
            case FILl_COFFEE_BEANS -> FILL_CUPS;
            default -> CHOOSE_ACTION;
        };
    }
}
