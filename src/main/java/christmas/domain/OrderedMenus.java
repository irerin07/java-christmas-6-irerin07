package christmas.domain;

import static christmas.EventPlanner.INPUT_MENU_EXCEPTION_MESSAGE;

import christmas.EventPlanner;
import christmas.domain.enumeration.menu.ChristMasMenu;
import christmas.domain.enumeration.menu.Dessert;
import christmas.domain.enumeration.menu.Drink;
import christmas.domain.enumeration.menu.Menu;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 민경수
 * @description ordered menus
 * @since 2023.11.15
 **********************************************************************************************************************/
public class OrderedMenus {

    private static final Integer MAX_ORDER_AMOUNT = 20;

    private final List<OrderedMenu> orderedMenus;

    private OrderedMenus(Map<String, Integer> orderedMenus) {
        validateMenu(orderedMenus);
        List<OrderedMenu> orderedMenus1 = buildOrderedMenus(orderedMenus);

        this.orderedMenus = orderedMenus1;
    }

    public static OrderedMenus of(Map<String, Integer> orderedMenus) {
        return new OrderedMenus(orderedMenus);
    }

    private List<OrderedMenu> buildOrderedMenus(Map<String, Integer> result) {
        List<OrderedMenu> orderedMenus = separateMenus(result);
        validateOnlyDrinkOrdered(orderedMenus);

        return orderedMenus;
    }

    private List<OrderedMenu> separateMenus(Map<String, Integer> menus) {
        List<OrderedMenu> result = new ArrayList<>();

        for (String menu : menus.keySet()) {
            Menu orderedMenu = findMenu(menu);
            result.add(OrderedMenu.of(orderedMenu, menus.get(menu)));
        }

        return result;
    }

    private Menu findMenu(String menu) {
        return ChristMasMenu.findMenuByName(menu)
                .orElseThrow(() -> new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE));
    }

    private void validateOnlyDrinkOrdered(List<OrderedMenu> result) {
        if (countNonDrinkMenu(result) == 0) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
    }

    private long countNonDrinkMenu(List<OrderedMenu> result) {
        return result.stream()
                .filter(e -> !e.isOfType(Drink.class))
                .count();
    }

    private void validateMenu(Map<String, Integer> menus) {
        if (menus.values().stream().reduce(0, Integer::sum) > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
    }

    public String printMenus() {
        StringBuilder stringBuilder = new StringBuilder();
        for (OrderedMenu orderedMenu : orderedMenus) {
            stringBuilder.append(orderedMenu.getMenuAndAmount());
        }

        return stringBuilder.toString();
    }

    public int matchMenuType(Class<? extends Menu> menuType) {
        int result = 0;

        for (OrderedMenu orderedMenu : orderedMenus) {
            if (orderedMenu.isOfType(menuType)) {
                result += orderedMenu.calculateBenefit();
            }
        }

        return result;
    }


    public BigDecimal totalPrice() {
        BigDecimal result = BigDecimal.ZERO;

        for (OrderedMenu orderedMenu : orderedMenus) {
            result = result.add(BigDecimal.valueOf(orderedMenu.calculatePrice()));
        }

        return result;
    }

    public boolean containsMenuType(Class<? extends Menu> menuType) {
        return orderedMenus.stream().anyMatch(e -> e.containsMenu(menuType));
    }
}