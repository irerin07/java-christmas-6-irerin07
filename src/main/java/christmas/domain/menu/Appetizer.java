package christmas.domain.menu;

import java.util.Arrays;

public enum Appetizer implements Menu {

    BUTTON_MUSHROOM_SOUP("양송이 스프", 6000),
    TAPAS("타파스", 5500),
    CAESAR_SALAD("시저샐러드", 8000);

    private String name;
    private Integer price;

    Appetizer(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public Appetizer findByName(String userInput) {
        return Arrays.stream(Appetizer.values())
                .filter(appetizer -> appetizer.name.equals(userInput))
                .findFirst()
                .orElse(null);
    }

    @Override
    public int calculateTotalPrice(Integer amount) {
        return price * amount;
    }

    @Override
    public String getMenuName(int amount) {
        return name + " " + amount + "개";
    }

}
