package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class OrderTest {

    private List<OrderedMenuItem> orderedMenuItems;
    private Order order;

    @BeforeEach
    void setUp() {
        orderedMenuItems = new ArrayList<>();
        orderedMenuItems.add(OrderedMenuItem.of(Dessert.ICE_CREAM, 2));
        orderedMenuItems.add(OrderedMenuItem.of(MainMenu.T_BONE_STEAK, 1));
        orderedMenuItems.add(OrderedMenuItem.of(MainMenu.BARBECUE_RIB, 1));
        orderedMenuItems.add(OrderedMenuItem.of(Appetizer.CAESAR_SALAD, 1));
        orderedMenuItems.add(OrderedMenuItem.of(Drink.RED_WINE, 1));

        Map<String, Integer> map = new HashMap<>();
        map.put("아이스크림", 2);
        map.put("티본스테이크", 1);
        map.put("바비큐립", 1);
        map.put("시저샐러드", 1);
        map.put("레드와인", 1);

        OrderedMenus orderedMenusInstance = OrderedMenus.of(map);
        order = Order.ofVisitDate(LocalDate.of(2023, 12, 4), orderedMenusInstance);
    }

    @DisplayName("주문한 메뉴들과 그 수량을 출력한다.")
    @Test
    void printOrderedMenus() {
        assertThat(order.printOrderedMenus()).contains(
                "아이스크림 2개",
                "티본스테이크 1개",
                "바비큐립 1개",
                "시저샐러드 1개",
                "레드와인 1개"
        );
    }

    @DisplayName("총주문 금액을 계산한다.")
    @Test
    void totalPrice() {
        BigDecimal bigDecimal = order.totalPrice();
        Integer reduce = orderedMenuItems.stream().map(OrderedMenuItem::calculatePrice).reduce(0, Integer::sum);
        assertEquals(BigDecimal.valueOf(reduce), bigDecimal);
    }

    @Test
    void isBenefitReceivable() {
        assertTrue(order.isBenefitReceivable());
    }

    @DisplayName("최소 주문 금액을 맞추지 못한 경우, 혜택을 받을 수 없다.")
    @Test
    void minimum_order_price_not_met() {
        orderedMenuItems = new ArrayList<>();
        orderedMenuItems.add(OrderedMenuItem.of(Appetizer.TAPAS, 1));

        Map<String, Integer> map = new HashMap<>();
        map.put("타파스", 1);

        OrderedMenus orderedMenusInstance = OrderedMenus.of(map);
        order = Order.ofVisitDate(LocalDate.of(2023, 12, 4), orderedMenusInstance);

        assertFalse(order.isBenefitReceivable());
    }

}