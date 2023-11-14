package christmas.domain;

import java.util.function.Supplier;

/**
 * @author 민경수
 * @description input validation helper
 * @since 2023.11.11
 **********************************************************************************************************************/
public class InputValidationHelper {

    private InputValidationHelper() {
    }

    public static <T> T get(Supplier<T> supplier) {
        do {
            try {
                return supplier.get();
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

}