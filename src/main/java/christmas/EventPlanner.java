package christmas;

import christmas.domain.InputValidationHelper;
import christmas.domain.Order;
import christmas.domain.OrderedMenu;
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
 * @description event planner
 * @since 2023.11.11
 **********************************************************************************************************************/
public class EventPlanner {

    private static final String INPUT_DATE_EXCEPTION_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final String INPUT_MENU_EXCEPTION_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String DELIMITER = ",";
    private static final String MENU_REGEX = "^[가-힣]+-\\d+$";
    private static final Integer EVENT_MONTH = 12;
    private static final Integer EVENT_YEAR = 2023;
    private static final Integer MAX_ORDER_AMOUNT = 20;

    private final InputView inputView;

    public EventPlanner(InputView inputView) {
        this.inputView = inputView;
    }

    public Order takeOrders() {
        LocalDate localDate = InputValidationHelper.get(this::getVisitLocalDate);

        List<OrderedMenu> orderedMenus = InputValidationHelper.get(this::getOrderedMenus);

        return Order.ofVisitDate(localDate, orderedMenus);
    }

    private LocalDate getVisitLocalDate() {
        String visitDate = inputView.getVisitDate(EventPlanner.EVENT_MONTH);

        return generateVisitDateFromInput(visitDate);
    }

    private List<OrderedMenu> getOrderedMenus() {
        List<String> menu = extractMenusFromInput();

        Map<String, Integer> result  = generateMenuQuantities(menu);

        return buildOrderedMenus(result);
    }

    private List<String> extractMenusFromInput() {
        List<String> result = toMenuList(inputView.getOrderingMenus());
        validateMenu(result);

        return result;
    }

    private Map<String, Integer> generateMenuQuantities(List<String> menu) {
        Map<String, Integer> result = toMap(menu);
        validateMenu(result);

        return result;
    }

    private List<OrderedMenu> buildOrderedMenus(Map<String, Integer> result) {
        List<OrderedMenu> orderedMenus = separateMenus(result);
        validateOnlyDrinkOrdered(orderedMenus);

        return orderedMenus;
    }

    private LocalDate generateVisitDateFromInput(String date) {
        int result = validateNumberFormat(date);

        return validateDate(result);
    }

    private LocalDate validateDate(int day) {
        try {
            return getVisitLocalDate(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INPUT_DATE_EXCEPTION_MESSAGE);
        }
    }

    private LocalDate getVisitLocalDate(int day) {
        return LocalDate.of(EVENT_YEAR, EVENT_MONTH, day);
    }

    private int validateNumberFormat(String number) {
        try {
            return convertStringToInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INPUT_DATE_EXCEPTION_MESSAGE);
        }
    }

    private int convertStringToInt(String number) {
        return Integer.parseInt(number);
    }

    private List<String> toMenuList(String menus) {
        return Arrays.stream(menus.split(DELIMITER))
                .map(String::trim)
                .toList();
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

    private void validateMenu(List<String> menus) {
        menus.stream()
                .filter(e -> !e.matches(MENU_REGEX))
                .findAny()
                .ifPresent(invalidMenu -> {
                    throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
                });
    }

    private void validateMenu(Map<String, Integer> menus) {
        if (menus.values().stream().reduce(0, Integer::sum) > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
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

    private Map<String, Integer> toMap(List<String> strings) {
        try {
            return strings.stream()
                    .map(element -> element.split("-"))
                    .collect(Collectors.toMap(parts -> parts[0], parts -> Integer.parseInt(parts[1])));
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(INPUT_MENU_EXCEPTION_MESSAGE);
        }
    }

}