package christmas.view;

import christmas.domain.Order;
import christmas.domain.SaleProfit;
import java.math.BigDecimal;

/**
 * @author 민경수
 * @description result view
 * @since 2023.11.10
 **********************************************************************************************************************/
public class OutputView {

    public void printEvent(Order order, SaleProfit saleProfit) {
        printOrderedMenus(order);
        printTotalOrderedPrice(order);

        saleProfit.appendBenefits(order);
    }

    private void printOrderedMenus(Order order) {
        printTitle("<주문 메뉴>");
        System.out.println(order.printOrderedMenus());
    }

    private void printTotalOrderedPrice(Order order) {
        printTitle("<할인 전 총주문 금액>");
        String format = String.format("%,.0f원", order.totalPrice());
        System.out.println(format);
        System.out.println();
    }

    private void printTitle(String title) {
        System.out.println(title);
    }

}