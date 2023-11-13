package christmas.domain;

import christmas.domain.menu.Menu;

/**
 * @author 민경수
 * @description ordered menu
 * @since 2023.11.11
 **********************************************************************************************************************/
public class OrderedMenu {

    private final Menu menu;
    private final Integer amount;

    private OrderedMenu(Menu menu, Integer amount) {
        this.menu = menu;
        this.amount = amount;
    }

    public static OrderedMenu of(Menu menu, int amount) {
        return new OrderedMenu(menu, amount);
    }

    public String getMenuAndAmount() {
        return menu.getMenuName(amount);
    }

    public int calculatePrice() {
        return menu.calculateTotalPrice(amount);
    }

    public boolean isOfType(Class<? extends Menu> menuType) {
        return menuType.isInstance(menu);
    }

    public int calculateBenefit() {
        return 2023 * amount;
    }
}