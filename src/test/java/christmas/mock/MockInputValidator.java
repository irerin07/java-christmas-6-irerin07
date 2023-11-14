package christmas.mock;

import java.util.function.Supplier;

/**
 * @author 민경수
 * @description mock input validator
 * @since 2023.11.14
 **********************************************************************************************************************/
public class MockInputValidator {
    static <T> String getTest(Supplier<T> supplier) {
        try {
            supplier.get();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return (e.getMessage());
        }

        return "이상없음";
    }
}