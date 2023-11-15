package christmas.mock;

import christmas.view.InputView;

/**
 * @author 민경수
 * @description mock wrong date input
 * @since 2023.11.15
 **********************************************************************************************************************/
public class MockWrongDateInput extends InputView {

    @Override
    public String getOrderingMenus() {
        return "해산물파스타-1,레드와인-2";
    }

    @Override
    public String getVisitDate(int month) {
        return "64";
    }
}