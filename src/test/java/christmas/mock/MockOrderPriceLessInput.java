package christmas.mock;

import christmas.view.InputView;

/**
 * @author 민경수
 * @description mock order price less input
 * @since 2023.11.16
 **********************************************************************************************************************/
public class MockOrderPriceLessInput extends InputView {

    @Override
    public String getOrderingMenus() {
        return "타파스-1";
    }

    @Override
    public String getVisitDate(int month) {
        return "12";
    }

}