package christmas.mock;

import christmas.domain.OrderedMenuItem;
import christmas.domain.enumeration.menu.ChristMasMenu;
import christmas.domain.enumeration.menu.Drink;
import christmas.domain.enumeration.menu.Menu;
import christmas.view.InputView;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 민경수
 * @description mock event planner
 * @since 2023.11.14
 **********************************************************************************************************************/
public class MockEventPlanner {

    public static final String INPUT_DATE_EXCEPTION_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    public static final String INPUT_MENU_EXCEPTION_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String DELIMITER = ",";
    private static final String MENU_REGEX = "^[가-힣]+-\\d+$";
    private static final Integer EVENT_MONTH = 12;
    private static final Integer EVENT_YEAR = 2023;
    private static final Integer MAX_ORDER_AMOUNT = 20;

    private final InputView inputView;

    public MockEventPlanner(InputView inputView) {
        this.inputView = inputView;
    }

    public String takeWrongOrders() {
        return MockInputValidator.getTest(() -> getOrderedMenus());
    }

    public String takeWrongDate() {
        return MockInputValidator.getTest(() -> getVisitDate(EVENT_MONTH));

    }

    private LocalDate getVisitDate(int date) {
        return validateUserInputDate(inputView.getVisitDate(date));
    }

    private List<OrderedMenuItem> getOrderedMenus() {
        List<String> menu = toMenuList(inputView.getOrderingMenus());
        validateMenu(menu);

        Map<String, Integer> result = toMap(menu);
        validateMenu(result);

        List<OrderedMenuItem> orderedMenuItems = separateMenus(result);
        validateOnlyDrinkOrdered(orderedMenuItems);

        return orderedMenuItems;
    }

    private LocalDate validateUserInputDate(String date) {
        return validateDate(validateNumberInput(date));
    }

    private LocalDate validateDate(int day) {
        try {
            return LocalDate.of(EVENT_YEAR, EVENT_MONTH, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INPUT_DATE_EXCEPTION_MESSAGE);
        }
    }

    private int validateNumberInput(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INPUT_DATE_EXCEPTION_MESSAGE);
        }
    }

    private List<String> toMenuList(String menus) {
        return Arrays.stream(menus.split(DELIMITER)).map(String::trim).toList();
    }

    private List<OrderedMenuItem> separateMenus(Map<String, Integer> menus) {
        List<OrderedMenuItem> result = new ArrayList<>();

        for (String menu : menus.keySet()) {
            Menu christmasMenu = ChristMasMenu.findMenuByName(menu)
                    .orElseThrow(() -> new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE));
            result.add(OrderedMenuItem.of(christmasMenu, menus.get(menu)));
        }

        return result;
    }

    private void validateMenu(List<String> menus) {
        validateMenuInput(menus);
    }

    private void validateMenu(Map<String, Integer> menus) {
        validateOrderAmount(menus);
    }

    private void validateOnlyDrinkOrdered(List<OrderedMenuItem> result) {
        long nonDrinkCount = result.stream()
                .filter(e -> !e.isOfType(Drink.class))
                .count();

        if (nonDrinkCount == 0) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
    }

    private void validateMenuInput(List<String> menus) {
        menus.stream()
                .filter(e -> !e.matches(MENU_REGEX))
                .findAny()
                .ifPresent(invalidMenu -> {
                    throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
                });
    }

    private Map<String, Integer> toMap(List<String> strings) {
        try {
            return strings.stream()
                    .map(element -> element.split("-"))
                    .collect(Collectors.toMap(parts -> parts[0], parts -> Integer.parseInt(parts[1])));
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
    }

    private void validateOrderAmount(Map<String, Integer> menus) {
        if (menus.values().stream().reduce(0, Integer::sum) > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
    }

}