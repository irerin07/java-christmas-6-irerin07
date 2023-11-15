package christmas.view;

import christmas.domain.Order;
import christmas.domain.SaleProfit;

/**
 * @author 민경수
 * @description result view
 * @since 2023.11.10
 **********************************************************************************************************************/
public class OutputView {

    public void printEventStatistics(Order order, SaleProfit saleProfit) {
        StringBuilder stringBuilder = new StringBuilder();

        printOrderRelatedInformation(stringBuilder, order);
        printBenefitsRelatedInformation(stringBuilder, order, saleProfit);

        System.out.println(stringBuilder);
    }

    private void appendSection(StringBuilder stringBuilder, String title, String content) {
        stringBuilder.append(printTitle(title)).append(System.lineSeparator());
        stringBuilder.append(content).append(System.lineSeparator());
    }

    private String printTitle(String title) {
        return "<" + title + ">";
    }

    private void printOrderRelatedInformation(StringBuilder stringBuilder, Order order) {
        appendSection(stringBuilder, "주문 메뉴", order.printOrderedMenus());
        appendSection(stringBuilder, "할인 전 총주문 금액", order.printTotalOrderedPrice());
    }

    private void printBenefitsRelatedInformation(StringBuilder stringBuilder, Order order, SaleProfit saleProfit) {
        appendSection(stringBuilder, "증정 메뉴", saleProfit.getGiftMenu());
        appendSection(stringBuilder, "혜택 내역", saleProfit.appendBenefits(order));
        appendSection(stringBuilder, "총혜택 금액", saleProfit.printTotalBenefitAmount(order));
        appendSection(stringBuilder, "할인 후 예상 결제 금액", saleProfit.printEstimatedCheckoutPrice(order));
        appendSection(stringBuilder, "12월 이벤트 배지", saleProfit.getEventBadge());
    }

}