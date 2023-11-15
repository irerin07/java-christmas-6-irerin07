package christmas;

import christmas.domain.InputValidationHelper;
import christmas.domain.Order;
import christmas.domain.OrderedMenus;
import christmas.view.InputView;
import java.time.DateTimeException;
import java.time.LocalDate;
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

    public static final String INPUT_DATE_EXCEPTION_MESSAGE = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    public static final String INPUT_MENU_EXCEPTION_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    public static final LocalDate CHRISTMAS_EVENT_START = LocalDate.of(2023, 12, 1);
    public static final LocalDate CHRISTMAS_EVENT_END = LocalDate.of(2023, 12, 25);
    public static final Integer BASE_CHRISTMAS_SALE_AMOUNT = 1000;
    public static final Integer CHRISTMAS_SALE_UNIT = 100;
    private static final String DELIMITER = ",";
    private static final String MENU_REGEX = "^[가-힣]+-\\d+$";
    private static final Integer EVENT_MONTH = 12;
    private static final Integer EVENT_YEAR = 2023;

    private final InputView inputView;

    public EventPlanner(InputView inputView) {
        this.inputView = inputView;
    }

    public Order takeOrders() {
        LocalDate localDate = InputValidationHelper.get(this::getVisitLocalDate);

        Map<String, Integer> orderedMenus = InputValidationHelper.get(this::getOrderedMenus);
        OrderedMenus orderedMenus1 = OrderedMenus.of(orderedMenus);

        return Order.ofVisitDate(localDate, orderedMenus1);
    }

    private LocalDate getVisitLocalDate() {
        String visitDate = inputView.getVisitDate(EventPlanner.EVENT_MONTH);

        return generateVisitDateFromInput(visitDate);
    }

    private Map<String, Integer> getOrderedMenus() {
        List<String> menu = extractMenusFromInput();

        return generateMenuQuantities(menu);
    }

    private List<String> extractMenusFromInput() {
        List<String> result = toMenuList(inputView.getOrderingMenus());
        validateMenuRegex(result);

        return result;
    }

    private Map<String, Integer> generateMenuQuantities(List<String> menu) {

        return toMap(menu);
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

    private void validateMenuRegex(List<String> menus) {
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

}