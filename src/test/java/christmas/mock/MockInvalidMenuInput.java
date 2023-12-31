package christmas.mock;

import christmas.view.InputView;

/**
 * @author 민경수
 * @description mock invalid menu input
 * @since 2023.11.15
 **********************************************************************************************************************/
public class MockInvalidMenuInput  extends InputView {

    @Override
    public String getOrderingMenus() {
        return "비빔밥-1,레드와인-2";
    }

    @Override
    public String getVisitDate(int month) {
        return "12";
    }

}