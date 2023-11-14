package christmas.mock;

import camp.nextstep.edu.missionutils.Console;
import christmas.view.InputView;

/**
 * @author 민경수
 * @description mock invalid input
 * @since 2023.11.14
 **********************************************************************************************************************/
public class MockInvalidMenuInput extends InputView {

    @Override
    public String getOrderingMenus() {
        return "제로콜라-1,레드와인-2";
    }

    @Override
    public String getVisitDate(int month) {
        return "64";
    }
}