package christmas.domain.enumeration.menu;

import java.util.Arrays;
import java.util.Optional;

public enum Dessert implements Menu {

    CHOCOLATE_CAKE("초코케이크", 15000),
    ICE_CREAM("아이스크림", 5000);

    private final String name;
    private final Integer price;

    Dessert(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public Optional<Dessert> findByName(String userInput) {
        return Arrays.stream(Dessert.values())
                .filter(desert -> desert.name.equals(userInput))
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
