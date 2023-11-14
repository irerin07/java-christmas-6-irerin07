package christmas.domain;

import christmas.domain.enumeration.menu.GiftMenu;

/**
 * @author 민경수
 * @description sale benefit calculator
 * @since 2023.11.11
 **********************************************************************************************************************/
public class SaleBenefitGenerator {

    public SaleProfit getBenefits(Order order) {
        return SaleProfit.ofVisitDate(
                giftMenu(order),
                weekDaySale(order),
                weekEndSale(order),
                christmasEventSale(order),
                specialDaySale(order)
        );
    }

    private GiftMenu giftMenu(Order order) {
        return GiftMenu.findByOrderPrice(order.totalPrice().intValue());
    }

    private int weekDaySale(Order order) {
        if (order.isWeekDay()) {
            return order.calculateWeekDayBenefit();
        }

        return 0;
    }

    private int weekEndSale(Order order) {
        if (order.isWeekend()) {
            return order.calculateWeekEndBenefit();
        }

        return 0;
    }

    private int christmasEventSale(Order order) {
        if (order.isChristmasSalePeriod()) {
            return order.calculateChristmasEventBenefit();
        }

        return 0;
    }

    private int specialDaySale(Order order) {
        if (order.isSpecialSaleDay()) {
            return order.calculateSpecialDayBenefit();
        }

        return 0;
    }

}