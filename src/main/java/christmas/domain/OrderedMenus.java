package christmas.domain;

import static christmas.EventPlanner.INPUT_MENU_EXCEPTION_MESSAGE;

import christmas.domain.enumeration.menu.ChristMasMenu;
import christmas.domain.enumeration.menu.Drink;
import christmas.domain.enumeration.menu.Menu;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 민경수
 * @description ordered menus
 * @since 2023.11.15
 **********************************************************************************************************************/
public class OrderedMenus {

    private static final Integer MAX_ORDER_AMOUNT = 20;

    private final List<OrderedMenuItem> orderedMenuItems;

    private OrderedMenus(Map<String, Integer> orderedMenus) {
        validateTotalMenuOrdered(orderedMenus);

        this.orderedMenuItems = buildOrderedMenuItems(orderedMenus);
    }

    public static OrderedMenus of(Map<String, Integer> orderedMenus) {
        return new OrderedMenus(orderedMenus);
    }

    public String printMenus() {
        StringBuilder stringBuilder = new StringBuilder();
        orderedMenuItems.forEach(orderedMenuItem ->
                stringBuilder.append(orderedMenuItem.getMenuAndAmount())
        );

        return stringBuilder.toString();
    }

    public int matchMenuType(Class<? extends Menu> menuType) {
        return orderedMenuItems.stream()
                .filter(orderedMenuItem -> orderedMenuItem.isOfType(menuType))
                .mapToInt(OrderedMenuItem::calculateBenefit)
                .sum();
    }


    public BigDecimal totalPrice() {
        return orderedMenuItems.stream()
                .map(orderedMenuItem -> BigDecimal.valueOf(orderedMenuItem.calculatePrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean containsMenuType(Class<? extends Menu> menuType) {
        return orderedMenuItems.stream()
                .anyMatch(e -> e.containsMenu(menuType));
    }

    private List<OrderedMenuItem> buildOrderedMenuItems(Map<String, Integer> result) {
        List<OrderedMenuItem> orderedMenuItems = separateMenus(result);
        validateOnlyDrinkOrdered(orderedMenuItems);

        return orderedMenuItems;
    }

    private List<OrderedMenuItem> separateMenus(Map<String, Integer> menus) {
        return menus.entrySet().stream()
                .map(entry -> OrderedMenuItem.of(findMenu(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Menu findMenu(String menu) {
        return ChristMasMenu.findMenuByName(menu)
                .orElseThrow(() -> new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE));
    }

    private void validateOnlyDrinkOrdered(List<OrderedMenuItem> result) {
        if (countNonDrinkMenu(result) == 0) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
    }

    private long countNonDrinkMenu(List<OrderedMenuItem> result) {
        return result.stream()
                .filter(e -> !e.isOfType(Drink.class))
                .count();
    }

    private void validateTotalMenuOrdered(Map<String, Integer> menus) {
        if (countAllMenuAmount(menus) > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
    }

    private int countAllMenuAmount(Map<String, Integer> menus) {
        return menus.values().stream().reduce(0, Integer::sum);
    }

}