package christmas.domain.enumeration.menu;

import java.util.Arrays;
import java.util.Optional;

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
    public Optional<Drink> findByName(String userInput) {
        return Arrays.stream(Drink.values())
                .filter(drink -> drink.name.equals(userInput))
                .findFirst();
    }

    @Override
    public int calculateTotalPrice(int amount) {
        return price * amount;
    }

    @Override
    public String getMenuNameAndAmount(int amount) {
        return name + " " + amount + "개";
    }

}
