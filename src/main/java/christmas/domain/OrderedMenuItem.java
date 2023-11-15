package christmas.domain;

import christmas.domain.enumeration.menu.Menu;

/**
 * @author 민경수
 * @description ordered menu
 * @since 2023.11.11
 **********************************************************************************************************************/
public class OrderedMenuItem {

    private final Menu menu;
    private final Integer amount;

    private OrderedMenuItem(Menu menu, Integer amount) {
        this.menu = menu;
        this.amount = amount;
    }

    public static OrderedMenuItem of(Menu menu, int amount) {
        return new OrderedMenuItem(menu, amount);
    }

    public String getMenuAndAmount() {
        return menu.getMenuNameAndAmount(amount);
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

    public boolean containsMenu(Class<? extends Menu> menuType) {
        return menuType.isInstance(menu);
    }

}