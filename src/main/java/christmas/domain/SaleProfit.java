package christmas.domain;

import christmas.domain.enumeration.EventBadge;
import christmas.domain.enumeration.menu.GiftMenu;
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

    public static SaleProfit ofVisitDate(GiftMenu giftMenu, int weekDay, int weekEnd, int christmasDDayEvent,
                                         int specialDay) {
        return new SaleProfit(giftMenu, weekDay, weekEnd, christmasDDayEvent, specialDay);
    }

    public BigDecimal totalProfit() {
        int benefitAmountWithoutGiftMenu =
                weekdaySaleAmount + weekendSaleAmount + christmasDDayEventSaleAmount + specialDaySaleAmount;

        return BigDecimal.valueOf(giftMenu.calculateTotalBenefitProfit(benefitAmountWithoutGiftMenu));
    }

    public String appendBenefits(Order order) {
        if (order.isBenefitReceivable()) {
            return String.format("%s%s%s%s%s",
                    getChristmasSaleProfit(order),
                    getWeekDaySaleProfit(order),
                    getWeekEndSaleProfit(order),
                    getSpecialSaleDayProfit(order),
                    getGiftMenuProfit(order)
            );
        }

        return String.format("%s%s", "없음", System.lineSeparator());
    }

    public String printTotalBenefitAmount() {
        return String.format("%,.0f원%s", totalProfit().negate(), System.lineSeparator());
    }

    public String printEstimatedCheckoutPrice(Order order) {
        BigDecimal totalPrice = order.totalPrice();

        if (order.isGiftMenu()) {
            totalPrice = getGiftMenuIncludedPrice(order.totalPrice());
        }

        return String.format("%,.0f원%s", totalPrice.subtract(totalProfit()), System.lineSeparator());
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

    private boolean christmasPeriodSaleApplied() {
        return christmasDDayEventSaleAmount != 0;
    }

    private String getChristmasEventBenefit() {
        return String.format("크리스마스 디데이 할인: %d%s", (-christmasDDayEventSaleAmount), System.lineSeparator());
    }

    private boolean weekdaySaleApplied() {
        return weekdaySaleAmount != 0;
    }

    private String getWeekDayBenefit() {
        return String.format("평일 할인: %d%s", (-weekdaySaleAmount), System.lineSeparator());
    }

    private boolean weekendSaleApplied() {
        return weekendSaleAmount != 0;
    }

    private String getWeekEndBenefit() {
        return String.format("주말 할인: %d%s", (-weekendSaleAmount), System.lineSeparator());
    }

    private boolean specialDaySaleApplied() {
        return specialDaySaleAmount != 0;
    }

    private String getSpecialSaleDayBenefit() {
        return String.format("특별 할인: %d%s", (-specialDaySaleAmount), System.lineSeparator());
    }

    public String getGiftMenu() {
        if (giftMenu == GiftMenu.NONE) {
            return giftMenu.getName();
        }

        return giftMenu.getGiftMenu();
    }

    private String getGiftMenuBenefit() {
        return String.format("증정 이벤트: %d%s", (-giftMenu.price()), System.lineSeparator());
    }

    private BigDecimal getGiftMenuIncludedPrice(BigDecimal totalPrice) {
        return totalPrice.add(BigDecimal.valueOf(giftMenu.price()));
    }

    public String getEventBadge() {
        return String.format("%s", eventBadge.getName());
    }

}