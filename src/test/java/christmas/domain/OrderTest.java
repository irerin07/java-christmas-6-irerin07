package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import christmas.domain.enumeration.menu.Appetizer;
import christmas.domain.enumeration.menu.Dessert;
import christmas.domain.enumeration.menu.Drink;
import christmas.domain.enumeration.menu.MainMenu;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void printOrderedMenus() {
        List<OrderedMenu> orderedMenus = new ArrayList<>();
        orderedMenus.add(OrderedMenu.of(Dessert.ICE_CREAM, 2));
        orderedMenus.add(OrderedMenu.of(MainMenu.T_BONE_STEAK, 1));
        orderedMenus.add(OrderedMenu.of(MainMenu.BARBECUE_RIB, 1));
        orderedMenus.add(OrderedMenu.of(Appetizer.CAESAR_SALAD, 1));
        orderedMenus.add(OrderedMenu.of(Drink.RED_WINE, 1));

//        Order order = Order.ofVisitDate(LocalDate.now(), orderedMenus);
//
//        String s = order.printOrderedMenus();
//
//        assertThat(s).contains(
//                "아이스크림 2개",
//                "티본스테이크 1개",
//                "바비큐립 1개",
//                "시저샐러드 1개",
//                "레드와인 1개"
//        );
    }

    @Test
    void totalPrice() {
        List<OrderedMenu> orderedMenus = new ArrayList<>();
        orderedMenus.add(OrderedMenu.of(Dessert.ICE_CREAM, 2));
        orderedMenus.add(OrderedMenu.of(MainMenu.T_BONE_STEAK, 1));
        orderedMenus.add(OrderedMenu.of(MainMenu.BARBECUE_RIB, 1));
        orderedMenus.add(OrderedMenu.of(Appetizer.CAESAR_SALAD, 1));
        orderedMenus.add(OrderedMenu.of(Drink.RED_WINE, 1));

//        Order order = Order.ofVisitDate(LocalDate.now(), orderedMenus);
//
//        BigDecimal bigDecimal = order.totalPrice();
//
//        Integer reduce = orderedMenus.stream().map(e -> e.calculatePrice()).reduce(0, Integer::sum);
//
//        assertEquals(BigDecimal.valueOf(reduce), bigDecimal);
    }

    @Test
    void isBenefitReceivable() {
        List<OrderedMenu> orderedMenus = new ArrayList<>();
        orderedMenus.add(OrderedMenu.of(Dessert.ICE_CREAM, 2));
        orderedMenus.add(OrderedMenu.of(MainMenu.T_BONE_STEAK, 1));
        orderedMenus.add(OrderedMenu.of(MainMenu.BARBECUE_RIB, 1));
        orderedMenus.add(OrderedMenu.of(Appetizer.CAESAR_SALAD, 1));
        orderedMenus.add(OrderedMenu.of(Drink.RED_WINE, 1));
//
//        Order order = Order.ofVisitDate(LocalDate.now(), orderedMenus);
//
//        assertTrue(order.isBenefitReceivable());
    }

}