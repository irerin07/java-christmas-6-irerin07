package christmas;

import christmas.domain.Order;
import christmas.domain.SaleBenefitCalculator;
import christmas.domain.SaleProfit;
import christmas.printer.EventPlannerPrinter;
import christmas.view.InputVIew;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO 주문 내역에 샴페인이 있는 경우는 어떻게 처리해야 하나
        // TODO sysout 대신 stringbuffer 사용하도록 변경
        EventPlanner eventPlanner = new EventPlanner(new InputVIew());

        SaleBenefitCalculator saleBenefitCalculator = new SaleBenefitCalculator();
        Order order = eventPlanner.takeOrders();
        SaleProfit saleProfit = saleBenefitCalculator.getBenefits(order);

        EventPlannerPrinter eventPlannerPrinter = new EventPlannerPrinter(new OutputView());
        eventPlannerPrinter.printHeader();
        eventPlannerPrinter.print(order, saleProfit);
    }
}
