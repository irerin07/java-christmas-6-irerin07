package christmas.domain.menu;

public interface Menu {

    Menu findByName(String userInput);

    int calculateTotalPrice(Integer amount);

    String getMenuName(int amount);

}
