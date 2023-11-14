package christmas.domain.enumeration.menu;

import java.util.Arrays;
import java.util.Optional;

public enum MainMenu implements Menu {

    T_BONE_STEAK("티본스테이크", 55000),
    BARBECUE_RIB("바비큐립", 54000),
    SEAFOOD_PASTA("해산물파스타", 35000),
    CHRISTMAS_PASTA("크리스마스파스타", 25000);

    private final String name;
    private final Integer price;

    MainMenu(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public Optional<MainMenu> findByName(String userInput) {
        return Arrays.stream(MainMenu.values())
                .filter(mainMenu -> mainMenu.name.equals(userInput))
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