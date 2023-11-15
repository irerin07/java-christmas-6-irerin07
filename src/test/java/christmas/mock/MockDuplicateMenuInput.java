package christmas.mock;

import christmas.view.InputView;

/**
 * @author 민경수
 * @description mock duplicate menu input
 * @since 2023.11.15
 **********************************************************************************************************************/
public class MockDuplicateMenuInput extends InputView {

    @Override
    public String getOrderingMenus() {
        return "해산물파스타-1,해산물파스타-2,레드와인-2";
    }

    @Override
    public String getVisitDate(int month) {
        return "64";
    }
}