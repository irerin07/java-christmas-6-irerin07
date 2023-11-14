package christmas.domain.enumeration.menu;

import java.util.Arrays;

public enum GiftMenu {

    NONE("없음", 0, 0, 119999),
    CHAMPAGNE("샴페인", 25000, 120000, Integer.MAX_VALUE);

    private final String name;
    private final Integer price;
    private final Integer minimumOrderPrice;
    private final Integer maximumOrderPrice;

    GiftMenu(String name, Integer price, Integer minimumOrderPrice, Integer maximumOrderPrice) {
        this.name = name;
        this.price = price;
        this.minimumOrderPrice = minimumOrderPrice;
        this.maximumOrderPrice = maximumOrderPrice;
    }

    public static GiftMenu findByOrderPrice(int price) {
        return Arrays.stream(GiftMenu.values())
                .filter(e -> e.minimumOrderPrice <= price && e.maximumOrderPrice >= price)
                .findFirst()
                .orElse(NONE);
    }

    public int calculateTotalBenefitProfit(int benefitAmountWithoutGiftMenu) {
        return price + benefitAmountWithoutGiftMenu;
    }

    public int price() {
        return price;
    }

    public String getGiftMenu() {
        return String.format("%s %d개%s", name, 1, System.lineSeparator());
    }

    public String getName() {
        return String.format("%s%s", name, System.lineSeparator());
    }
}