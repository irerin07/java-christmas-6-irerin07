package christmas.domain.enumeration.menu;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum ChristMasMenu {

    NONE(Collections.EMPTY_LIST),
    DESSERT(Arrays.asList(
            Dessert.CHOCOLATE_CAKE,
            Dessert.ICE_CREAM)
    ),
    DRINK(Arrays.asList(
            Drink.ZERO_COKE,
            Drink.RED_WINE,
            Drink.CHAMPAGNE)
    ),
    MAIN_MENU(Arrays.asList(
            MainMenu.T_BONE_STEAK,
            MainMenu.BARBECUE_RIB,
            MainMenu.SEAFOOD_PASTA,
            MainMenu.CHRISTMAS_PASTA)
    ),
    APPETIZER(Arrays.asList(
            Appetizer.BUTTON_MUSHROOM_SOUP,
            Appetizer.TAPAS,
            Appetizer.CAESAR_SALAD)
    );

    private static final Map<List<? extends Menu>, ChristMasMenu> CHRISTMAS_MENU_BY_CATEGORY;

    static {
        CHRISTMAS_MENU_BY_CATEGORY = new HashMap<>();
        for (ChristMasMenu category : ChristMasMenu.values()) {
            CHRISTMAS_MENU_BY_CATEGORY.put(category.items, category);
        }
    }

    private final List<? extends Menu> items;

    ChristMasMenu(List<? extends Menu> items) {
        this.items = items;
    }

    public static Optional<? extends Menu> findMenuByName(String userInput) {
        return CHRISTMAS_MENU_BY_CATEGORY.values().stream()
                .flatMap(christMasMenu -> christMasMenu.items.stream())
                .map(menuItem -> menuItem.findByName(userInput))
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());
    }

}
