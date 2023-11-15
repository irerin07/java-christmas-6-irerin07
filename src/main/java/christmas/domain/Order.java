package christmas.domain;

import christmas.domain.enumeration.SpecialEventSale;
import christmas.domain.enumeration.menu.Dessert;
import christmas.domain.enumeration.menu.MainMenu;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author 민경수
 * @description order
 * @since 2023.11.10
 **********************************************************************************************************************/
public class Order {

    private final LocalDate visitDate;
    private final OrderedMenus orderedMenus;

    private Order(LocalDate visitDate, OrderedMenus orderedMenus) {
        validateVisitDate(visitDate);
        this.visitDate = visitDate;

        this.orderedMenus = orderedMenus;
    }


    public static Order ofVisitDate(LocalDate visitDate, OrderedMenus orderedMenus) {
        return new Order(visitDate, orderedMenus);
    }

    public String printOrderedMenus() {
        StringBuffer sb = new StringBuffer();
        sb.append(orderedMenus.printMenus());

        return sb.toString();
    }

    public BigDecimal totalPrice() {
        BigDecimal result = BigDecimal.ZERO;

        result = result.add(orderedMenus.totalPrice());

        return result;
    }

    public boolean isBenefitReceivable() {
        return isWeekDayBenefitReceivable() || isWeekEndBenefitReceivable() || isSpecialSaleDay() || isGiftMenu()
                || isChristmasSalePeriod();
    }

    public String getVisitDate() {
        return visitDate.getMonthValue() + "월" + visitDate.getDayOfMonth() + "일";
    }

    public boolean isWeekDay() {
        return !(visitDate.getDayOfWeek() == DayOfWeek.FRIDAY || visitDate.getDayOfWeek() == DayOfWeek.SATURDAY);
    }

    public boolean isSpecialSaleDay() {
        return SpecialEventSale.isSpecialEventDate(visitDate.getDayOfMonth());
    }

    public boolean isGiftMenu() {
        return totalPrice().compareTo(BigDecimal.valueOf(120000)) != -1;
    }

    public boolean isChristmasSalePeriod() {
        return !visitDate.isBefore(LocalDate.of(2023, 12, 1)) && !visitDate.isAfter(LocalDate.of(2023, 12, 25));
    }

    public int calculateChristmasEventBenefit() {
        if (isChristmasSalePeriod()) {
            long between = ChronoUnit.DAYS.between(LocalDate.of(2023, 12, 1), visitDate);
            return (int) (1000 + (between * 100));
        }

        return 0;
    }

    public boolean isWeekend() {
        return (visitDate.getDayOfWeek() == DayOfWeek.FRIDAY || visitDate.getDayOfWeek() == DayOfWeek.SATURDAY);
    }

    public int calculateWeekDayBenefit() {
        int weekDayBenefit = 0;

        weekDayBenefit += orderedMenus.matchMenuType(Dessert.class);

        return weekDayBenefit;
    }

    public int calculateWeekEndBenefit() {
        int weekEndBenefit = 0;

        weekEndBenefit += orderedMenus.matchMenuType(MainMenu.class);

        return weekEndBenefit;
    }

    public int calculateSpecialDayBenefit() {
        return SpecialEventSale.getSaleAmount(visitDate.getDayOfMonth());
    }

    public String printTotalOrderedPrice() {
        return String.format("%,.0f원%s", totalPrice(), System.lineSeparator());
    }

    private void validateVisitDate(LocalDate visitDate) {
        if (LocalDate.now().isAfter(visitDate)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    private boolean isWeekDayBenefitReceivable() {
        return isWeekDay() && orderedMenus.containsMenuType(Dessert.class);
    }

    private boolean isWeekEndBenefitReceivable() {
        return isWeekDay() && orderedMenus.containsMenuType(MainMenu.class);
    }

}