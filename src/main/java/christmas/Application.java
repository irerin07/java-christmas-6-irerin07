package christmas;

import christmas.domain.Order;
import christmas.domain.SaleBenefitGenerator;
import christmas.domain.SaleProfit;
import christmas.printer.EventPlannerPrinter;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        EventPlanner eventPlanner = new EventPlanner(new InputView());

        SaleBenefitGenerator saleBenefitGenerator = new SaleBenefitGenerator();
        Order order = eventPlanner.takeOrders();
        SaleProfit saleProfit = saleBenefitGenerator.getBenefits(order);

        EventPlannerPrinter eventPlannerPrinter = new EventPlannerPrinter(new OutputView());
        eventPlannerPrinter.printHeader();
        eventPlannerPrinter.print(order, saleProfit);
    }

}
