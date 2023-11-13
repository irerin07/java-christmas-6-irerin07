package christmas;

import christmas.domain.InputValidationHelper;
import christmas.domain.Order;
import christmas.domain.OrderedMenu;
import christmas.domain.menu.ChristMasMenu;
import christmas.domain.menu.Drink;
import christmas.domain.menu.Menu;
import christmas.view.InputVIew;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 민경수
 * @description event planner
 * @since 2023.11.11
 **********************************************************************************************************************/
public class EventPlanner {

    private static final String DELIMITER = ",";
    private static final String MENU_REGEX = "^[가-힣]+-\\d+$";
    private static final Integer EVENT_MONTH = 12;
    public static final Integer EVENT_YEAR = 2023;

    private final InputVIew inputView;

    public EventPlanner(InputVIew inputView) {
        this.inputView = inputView;
    }

    public Order takeOrders() {
        LocalDate localDate = InputValidationHelper.get(() -> getVisitDate(EVENT_MONTH));

        List<OrderedMenu> orderedMenus = InputValidationHelper.get(() -> getOrderedMenus());

        return Order.ofVisitDate(LocalDateTime.now(), localDate, orderedMenus);
    }

    private LocalDate getVisitDate(int date) {
        return validateUserInputDate(inputView.getVisitDate(date));
    }

    private List<OrderedMenu> getOrderedMenus() {
        List<String> menu = toMenuList(inputView.getOrderingMenus());
        validateMenu(menu);

        Map<String, Integer> result = toMap(menu);
        validateMenu(result);

        List<OrderedMenu> orderedMenus = separateMenus(result);
        validateOnlyDrinkOrdered(orderedMenus);

        return orderedMenus;
    }

    private LocalDate validateUserInputDate(String date) {
        return validateDate(validateNumberInput(date));
    }

    private LocalDate validateDate(int date) {
        try {
            return LocalDate.of(EVENT_YEAR, EVENT_MONTH, date);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    private int validateNumberInput(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    private List<String> toMenuList(String menus) {
        return Arrays.stream(menus.split(DELIMITER)).map(String::trim).toList();
    }

    private List<OrderedMenu> separateMenus(Map<String, Integer> menus) {
        List<OrderedMenu> result = new ArrayList<>();

        for (String menu : menus.keySet()) {
            Menu christmasMenu = ChristMasMenu.findMenu(menu);
            result.add(OrderedMenu.of(christmasMenu, menus.get(menu)));
        }

        return result;
    }

    private void validateMenu(List<String> menus) {
        validateMenuInput(menus);
    }

    private void validateMenu(Map<String, Integer> menus) {
        validateOrderAmount(menus);
    }

    private void validateOnlyDrinkOrdered(List<OrderedMenu> result) {
        long nonDrinkCount = result.stream()
                .filter(e -> !e.isOfType(Drink.class))
                .count();

        if (nonDrinkCount == 0) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private void validateMenuInput(List<String> menus) {
        menus.stream()
                .filter(e -> !e.matches(MENU_REGEX))
                .findAny()
                .ifPresent(invalidMenu -> {
                    throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
                });
    }

    private Map<String, Integer> toMap(List<String> strings) {
        try {
            return strings.stream()
                    .map(element -> element.split("-"))
                    .collect(Collectors.toMap(parts -> parts[0], parts -> Integer.parseInt(parts[1])));
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    private void validateOrderAmount(Map<String, Integer> menus) {
        if (menus.values().stream().reduce(0, Integer::sum) > 20) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }


}