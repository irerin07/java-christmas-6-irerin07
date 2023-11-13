package christmas.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public enum SpecialEventSale {

    NONE(Collections.EMPTY_LIST, 0),
    STARRED_DATE(Arrays.asList(3, 10, 17, 24, 25, 31), 1000);

    private final List<Integer> dates;
    private final Integer saleAmount;

    SpecialEventSale(List<Integer> dates, Integer saleAmount) {
        this.dates = dates;
        this.saleAmount = saleAmount;
    }

    public static boolean isSpecialEventDate(int date) {
        return Arrays.stream(SpecialEventSale.values())
                .anyMatch(e -> e.dates.contains(date));
    }

    public static int getSaleAmount(int date) {
        return Arrays.stream(SpecialEventSale.values())
                .filter(e -> e.dates.contains(date))
                .findFirst()
                .map(e -> e.saleAmount)
                .orElse(NONE.saleAmount);
    }


}
