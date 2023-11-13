package christmas.domain.menu;

import java.util.Arrays;

public enum MainMenu implements Menu {

    T_BONE_STEAK("티본스테이크", 55000),
    BARBECUE_RIB("바비큐립", 54000),
    SEAFOOD_PASTA("해산물파스타", 35000),
    CHRISTMAS_PASTA("크리스마스파스타", 25000);

    private String name;
    private Integer price;

    MainMenu(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public MainMenu findByName(String userInput) {
        return Arrays.stream(MainMenu.values())
                .filter(mainMenu -> mainMenu.name.equals(userInput))
                .findFirst()
                .orElse(null);
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