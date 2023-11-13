package christmas.domain.menu;

import java.util.Arrays;

public enum Drink implements Menu {

    ZERO_COKE("제로콜라", 3000),
    RED_WINE("레드와인", 60000),
    CHAMPAGNE("샴페인", 25000);

    private String name;
    private Integer price;

    Drink(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public Drink findByName(String userInput) {
        return Arrays.stream(Drink.values()).filter(drink -> drink.name.equals(userInput)).findFirst().orElse(null);
    }

    @Override
    public String getMenuName(int amount) {
        return name + " " + amount + "개";
    }

    @Override
    public int calculateTotalPrice(Integer amount) {
        return price * amount;
    }

}
