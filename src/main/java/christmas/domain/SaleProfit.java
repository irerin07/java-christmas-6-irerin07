package christmas.domain;

import christmas.domain.menu.GiftMenu;
import java.math.BigDecimal;

/**
 * @author 민경수
 * @description sale profit
 * @since 2023.11.11
 **********************************************************************************************************************/
public class SaleProfit {

    private final GiftMenu giftMenu;
    private final Integer weekdaySaleAmount;
    private final Integer weekendSaleAmount;
    private final Integer christmasDDayEventSaleAmount;
    private final Integer specialDaySaleAmount;
    private final EventBadge eventBadge;


    private SaleProfit(GiftMenu giftMenu, int weekDay, int weekEnd, int christmasDDayEvent, int specialDaySaleAmount) {
        this.giftMenu = giftMenu;
        this.weekdaySaleAmount = weekDay;
        this.weekendSaleAmount = weekEnd;
        this.christmasDDayEventSaleAmount = christmasDDayEvent;
        this.specialDaySaleAmount = specialDaySaleAmount;
        this.eventBadge = EventBadge.findByBenefitByPrice(totalProfit().intValue());
    }

    public static SaleProfit ofVisitDate(GiftMenu giftMenu, int weekDay, int weekEnd, int christmasDDayEvent, int specialDay) {
        return new SaleProfit(giftMenu, weekDay, weekEnd, christmasDDayEvent, specialDay);
    }

    public BigDecimal totalProfit() {
        int benefitAmountWithoutGiftMenu = weekdaySaleAmount + weekendSaleAmount + christmasDDayEventSaleAmount + specialDaySaleAmount;

        return BigDecimal.valueOf(giftMenu.calculateTotalBenefitProfit(benefitAmountWithoutGiftMenu));
    }

    public void appendBenefits(Order order) {
        printGiftMenu(order);
        printBenefits(order);
        printTotalBenefitAmount();
        printEstimatedCheckoutPrice(order);
        printEventBadge();
    }

    private void printGiftMenu(Order order) {
        System.out.println("<증정 메뉴>");
        System.out.println(getGiftMenu(order));

        System.out.println();
    }

    private void printBenefits(Order order) {
        System.out.println("<혜택 내역>");
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(appendBenefits(order, stringBuilder));

    }

    private StringBuilder appendBenefits(Order order, StringBuilder stringBuilder) {
        if (order.isBenefitReceivable()) {
            append(stringBuilder, getChristmasSaleProfit(order));
            append(stringBuilder, getWeekDaySaleProfit(order));
            append(stringBuilder, getWeekEndSaleProfit(order));
            append(stringBuilder, getSpecialSaleDayProfit(order));
            append(stringBuilder, getGiftMenuProfit(order));

            return stringBuilder;
        }

        return stringBuilder.append("없음").append(System.lineSeparator());
    }

    private void append(StringBuilder stringBuilder, String benefit) {
        if (benefit != null && !benefit.isEmpty()) {
            stringBuilder.append(benefit).append(System.lineSeparator());
        }
    }

    private String getChristmasSaleProfit(Order order) {
        if (order.isChristmasSalePeriod() && christmasPeriodSaleApplied()) {
            return getChristmasEventBenefit();
        }

        return "";
    }

    private String getWeekDaySaleProfit(Order order) {
        if (order.isWeekDay() && weekdaySaleApplied()) {
            return getWeekDayBenefit();
        }

        return "";
    }

    private String getWeekEndSaleProfit(Order order) {
        if (order.isWeekend() && weekendSaleApplied()) {
            return getWeekEndBenefit();
        }

        return "";
    }

    private String getSpecialSaleDayProfit(Order order) {
        if (order.isSpecialSaleDay() && specialDaySaleApplied()) {
            return getSpecialSaleDayBenefit();
        }

        return "";
    }

    private String getGiftMenuProfit(Order order) {
        if (order.isGiftMenu()) {
            return getGiftMenuBenefit();
        }

        return "";
    }

    private void printTotalBenefitAmount() {
        System.out.println("<총혜택 금액>");
        String format = String.format("-%,.0f원", totalProfit());
        System.out.println(format);
        System.out.println();
    }

    private void printEstimatedCheckoutPrice(Order order) {
        System.out.println("<할인 후 예상 결제 금액>");
        BigDecimal totalPrice = order.totalPrice();

        if (order.isGiftMenu()) {
            totalPrice = getGiftMenuIncludedPrice(order.totalPrice());
        }
        String format = String.format("%,.0f원", totalPrice.subtract(totalProfit()));
        System.out.println(format);
        System.out.println();
    }

    private void printEventBadge() {
        System.out.println("<12월 이벤트 배지>");
        String eventBadge = getEventBadge();
        System.out.println(eventBadge);
    }

    private boolean christmasPeriodSaleApplied() {
        return christmasDDayEventSaleAmount != 0;
    }

    private String getChristmasEventBenefit() {
        return "크리스마스 디데이 할인: -" + christmasDDayEventSaleAmount;
    }

    private boolean weekdaySaleApplied() {
        return weekdaySaleAmount != 0;
    }

    private String getWeekDayBenefit() {
        return "평일 할인: -" + weekdaySaleAmount;
    }

    private boolean weekendSaleApplied() {
        return weekendSaleAmount != 0;
    }

    private String getWeekEndBenefit() {
        return "주말 할인: -" + weekendSaleAmount;
    }

    private boolean specialDaySaleApplied() {
        return specialDaySaleAmount != 0;
    }

    private String getSpecialSaleDayBenefit() {
        return "특별 할인: -" + specialDaySaleAmount;
    }

    private String getGiftMenu(Order order) {
        if (giftMenu == GiftMenu.NONE) {
            return giftMenu.getName();
        }

        return giftMenu.getGiftMenu();
    }

    private String getGiftMenuBenefit() {
        return "증정 이벤트: -" + giftMenu.price();
    }

    private BigDecimal getGiftMenuIncludedPrice(BigDecimal totalPrice) {
        return totalPrice.add(BigDecimal.valueOf(giftMenu.price()));
    }

    private String getEventBadge() {
        return eventBadge.getName();
    }

}