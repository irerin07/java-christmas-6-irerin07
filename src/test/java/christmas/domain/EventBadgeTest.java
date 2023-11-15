package christmas.domain;

import static org.junit.jupiter.api.Assertions.*;

import christmas.domain.enumeration.EventBadge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventBadgeTest {

    @DisplayName("각 혜택금액 별 올바른 배지를 찾는다.")
    @Test
    void findByBenefitByPrice() {
        assertAll(
                () -> assertEquals(EventBadge.STAR, EventBadge.findByBenefitByPrice(5000)),
                () -> assertEquals(EventBadge.TREE, EventBadge.findByBenefitByPrice(10000)),
                () -> assertEquals(EventBadge.SANTA, EventBadge.findByBenefitByPrice(20000)),
                () -> assertEquals(EventBadge.NONE, EventBadge.findByBenefitByPrice(0))
        );

    }

}