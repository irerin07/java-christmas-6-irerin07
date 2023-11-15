package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import christmas.domain.enumeration.menu.Appetizer;
import christmas.domain.enumeration.menu.Dessert;
import christmas.domain.enumeration.menu.Drink;
import christmas.domain.enumeration.menu.MainMenu;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SaleBenefitGeneratorTest {

    private Order order;

    @BeforeEach
    void setUp() {
        Map<String, Integer> map = new HashMap<>();
        map.put("아이스크림", 2);
        map.put("티본스테이크", 1);
        map.put("바비큐립", 1);
        map.put("시저샐러드", 1);
        map.put("레드와인", 1);

        OrderedMenus orderedMenusInstance = OrderedMenus.of(map);
        order = Order.ofVisitDate(LocalDate.of(2023, 12, 4), orderedMenusInstance);
    }

    @DisplayName("해당하는 혜택 내역들을 산출한다.")
    @Test
    void getBenefits() {
        SaleBenefitGenerator saleBenefitGenerator = new SaleBenefitGenerator();
        SaleProfit benefits = saleBenefitGenerator.getBenefits(order);

        assertNotNull(saleBenefitGenerator.getBenefits(order));

        assertThat(benefits.getGiftMenu()).contains(
                "샴페인 1개"
        );

        assertThat(benefits.getEventBadge()).isEqualTo(
                "산타"
        );

        assertThat(benefits.appendBenefits(order)).contains(
                "크리스마스 디데이 할인: -1300"
                , "평일 할인: -4046"
                , "증정 이벤트: -25000"
        );

        assertThat(benefits.printTotalBenefitAmount(order)).contains(
                "-30,346원"
        );

        assertThat(benefits.totalProfit()).isEqualTo(
                BigDecimal.valueOf(30346)
        );

        assertThat(benefits.printEstimatedCheckoutPrice(order)).contains(
                "181,654원"
        );

    }

}