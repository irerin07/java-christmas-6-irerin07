package christmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import christmas.domain.Order;
import christmas.mock.MockEventPlanner;
import christmas.mock.MockInvalidMenuInput;

import christmas.view.InputView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventPlannerTest {

    @DisplayName("정상적인 주문이 들어온 경우")
    @Test
    void takeOrders_ValidInput_ReturnsOrder() {
        // Arrange
        InputView inputView = Mockito.mock(InputView.class);
        when(inputView.getVisitDate(Mockito.anyInt())).thenReturn("15");
        when(inputView.getOrderingMenus()).thenReturn("티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");

        EventPlanner eventPlanner = new EventPlanner(inputView);

        // Act
        Order order = eventPlanner.takeOrders();

        // Assert
        assertNotNull(order);
        assertEquals("12월15일", order.getVisitDate());
        assertAll(
                () -> assertTrue(order.isBenefitReceivable()), // 혜택을 받을 수 있는지 확인
                () -> assertTrue(order.isChristmasSalePeriod()), // 방문일(12월 15일)이 크리스마스 디데이 세일 기간인지 확인
                () -> assertTrue(order.isGiftMenu()), // 총주문 금액이 12만원이 초과하여 증정 메뉴를 받을 수 있는지 확인
                () -> assertTrue(order.isWeekend()), //방문일(12월 15일)이 주말인지 확인
                () -> assertFalse(order.isWeekDay()), // 방문일(12월 15일)이 평일인지 확인
                () -> assertFalse(order.isSpecialSaleDay()) // 방문일(12월 15일)이 달력에 별표시가 되어있는 날인지 확인
        );
    }

    @DisplayName("음료만으로 구성된 메뉴 주문시 예외 처리")
    @Test
    void takeOrders_Drinks_Only() {
        // Arrange
        InputView inputView = new MockInvalidMenuInput();
        MockEventPlanner eventPlanner = new MockEventPlanner(inputView);

        // Act
        String s = eventPlanner.takeWrongOrders();

        // Assert
        assertThat(s).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("잘못된 날짜")
    @Test
    void takeOrders_Wrong_Date() {
        // Arrange
        InputView inputView = new MockInvalidMenuInput();
        MockEventPlanner eventPlanner = new MockEventPlanner(inputView);

        // Act
        String s = eventPlanner.takeWrongDate();

        // Assert
        assertThat(s).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

}