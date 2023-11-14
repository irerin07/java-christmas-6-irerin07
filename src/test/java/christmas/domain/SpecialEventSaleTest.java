package christmas.domain;

import static org.junit.jupiter.api.Assertions.*;

import christmas.domain.enumeration.SpecialEventSale;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpecialEventSaleTest {

    @DisplayName("각 날짜가 STARRED_DATE에 설정된 날짜인지 확인")
    @Test
    void testIsSpecialEventDate() {
        assertTrue(SpecialEventSale.isSpecialEventDate(3));
        assertTrue(SpecialEventSale.isSpecialEventDate(25));
        assertFalse(SpecialEventSale.isSpecialEventDate(8));
    }

    @DisplayName("각 날짜별 할인 금액 확인")
    @Test
    void testGetSaleAmount() {
        assertEquals(0, SpecialEventSale.getSaleAmount(8));
        assertEquals(1000, SpecialEventSale.getSaleAmount(3));
        assertEquals(1000, SpecialEventSale.getSaleAmount(31));
    }

}